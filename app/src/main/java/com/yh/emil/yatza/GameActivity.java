package com.yh.emil.yatza;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yh.emil.yatza.models.PlayerScore;
import com.yh.emil.yatza.models.scores.ScoreBoardAdapter;
import com.yh.emil.yatza.models.Dice;
import com.yh.emil.yatza.models.Player;
import com.yh.emil.yatza.models.toplist.TopList;
import com.yh.emil.yatza.models.toplist.TopListManager;
import com.yh.emil.yatza.models.ValueComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Emil on 2016-03-31.
 */
public class GameActivity extends Activity {

    private TextView mCurrentPlayerView;
    private TextView mMessageView;
    private TextView mPlayer1Score;
    private TextView mPlayer2Score;
    private ImageView[] mDice;
    private ScoreBoardAdapter mScoreBoardAdapter;
    private AlertDialog mExitDialog;

    private String mPlayerOneName = "Player1";
    private String mPlayerTwoName = "Player2";
    private Integer mNumberOfPlayers = 2;
    private List<Player> mPlayers;
    private List<PlayerScore> mInitialScores;
    private Integer mSelectedPlayerIndex = 0;

    private Handler handler;
    private TurnChangeValidator turnChangeValidator;

    private enum State { DECIDING_PLAYER, WAITING, PLAYING_GAME, ENDING_GAME};
    private State state;

    private int mRollsLeft;
    private final int MAX_ROLLS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            mPlayerOneName = extras.getString("playerOneName");
            mPlayerTwoName = extras.getString("playerTwoName");
            mNumberOfPlayers = extras.getInt("numberOfPlayers");
        }

        //hardcoded, later make dynamic
        mPlayers = new ArrayList<Player>();
        mPlayers.add(new Player(mPlayerOneName));
        if (mNumberOfPlayers == 2) {
            mPlayers.add(new Player(mPlayerTwoName));
        }

        state = State.DECIDING_PLAYER;

        mInitialScores = new ArrayList<PlayerScore>();
        handler = new Handler();
        turnChangeValidator = new TurnChangeValidator();

        mScoreBoardAdapter = new ScoreBoardAdapter(getApplicationContext(), mPlayers.get(mSelectedPlayerIndex).getScoreboard());
        mScoreBoardAdapter.setScoreClickListener(new ScoreClickListener());

        ListView listView = (ListView) findViewById(R.id.gameScoreBoard);
        listView.setAdapter(mScoreBoardAdapter);

        mDice = new ImageView[5];
        mDice[0] = (ImageView)findViewById(R.id.gameDice1);
        mDice[1] = (ImageView)findViewById(R.id.gameDice2);
        mDice[2] = (ImageView)findViewById(R.id.gameDice3);
        mDice[3] = (ImageView)findViewById(R.id.gameDice4);
        mDice[4] = (ImageView)findViewById(R.id.gameDice5);

        for (ImageView diceImage : mDice) {
            diceImage.setTag(new Dice());
            diceImage.setOnClickListener(new DiceImageListener());
        }

        mCurrentPlayerView = (TextView)findViewById(R.id.gameCurrentPlayerView);
        mMessageView = (TextView)findViewById(R.id.gameMessageView);

        mPlayer1Score = (TextView)findViewById(R.id.gamePlayer1Score);
        mPlayer2Score = (TextView)findViewById(R.id.gamePlayer2Score);

        mPlayer1Score.setText(mPlayerOneName + ": 0 points");
        if (mNumberOfPlayers == 2)
            mPlayer2Score.setText(mPlayerTwoName + ": 0 points");

        Button rollButton = (Button) findViewById(R.id.gameRollButton);
        rollButton.setOnClickListener(new RollButtonListener());

        mExitDialog = createExitDialog();

        Button exitButton = (Button) findViewById(R.id.gameExitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExitDialog.show();
            }
        });

        mMessageView.setText("Roll the dice, highest points will start.");
    }

    private class ScoreClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (isWaitingForScore()) {
                if (mScoreBoardAdapter.processClickOnScoreView(view)) {
                    playerFinishedTurn();
                }
            }
        }

        public void playerFinishedTurn() {
            mRollsLeft = 0;
            handler.postDelayed(turnChangeValidator, 500);
        }

        public boolean isWaitingForScore() {
            if (state == State.PLAYING_GAME && mRollsLeft < MAX_ROLLS)
                return true;
            else
                return false;
        }
    }

    class TurnChangeValidator implements Runnable {
        public void run() {
            if (state == State.PLAYING_GAME) {
                mSelectedPlayerIndex = mSelectedPlayerIndex + 1 < mPlayers.size() ? mSelectedPlayerIndex + 1 : 0;
                unselectAllDice();
                mPlayer1Score.setText(mPlayers.get(0).getName() + ": " + mPlayers.get(0).getTotalScore() + " points");
                if (mNumberOfPlayers == 2) {
                    mPlayer2Score.setText(mPlayers.get(1).getName() + ": " + mPlayers.get(1).getTotalScore() + " points");
                }

                if (mPlayers.get(mSelectedPlayerIndex).isFinished()) {
                    state = GameActivity.State.ENDING_GAME;
                    mMessageView.setText("Game has ended.");
                    ArrayList<PlayerScore> endScores = new ArrayList<PlayerScore>();
                    for (Player player : mPlayers) {
                        endScores.add(new PlayerScore(player));
                    }

                    boolean isTie = false;
                    Collections.sort(endScores);
                    String endMessage = "Winner is: " + endScores.get(0).getName();

                    if (endScores.size() >= 2) {
                        if (endScores.get(0).getScore() == endScores.get(1).getScore()) {
                            endMessage = "No winner, it's a tie!";
                        }
                    }

                    TopListManager topListManager = new TopListManager();
                    TopList topList = topListManager.loadTopList(getApplicationContext());

                    for (PlayerScore playerScore : endScores) {
                        boolean isScoreSaved = topList.tryToAddEntry(playerScore.getName(), playerScore.getScore());
                        if (isScoreSaved) {
                            Toast.makeText(getApplicationContext(), "Congratulations " + playerScore.getName() + "! Your score of " +
                                    playerScore.getScore() +
                                    " will be saved in the TopList!", Toast.LENGTH_LONG).show();
                        }
                    }

                    topListManager.saveTopList(topList, getApplicationContext());

                    mMessageView.setText(endMessage);
                } else {

                    mScoreBoardAdapter.setScoreBoard(mPlayers.get(mSelectedPlayerIndex).getScoreboard());
                    mRollsLeft = MAX_ROLLS;
                    mCurrentPlayerView.setText("Current player: " + mPlayers.get(mSelectedPlayerIndex).getName());
                    mMessageView.setText("A new round has started! " + String.valueOf(MAX_ROLLS) + " rolls left.");
                }
            }
        }

        private void unselectAllDice() {
            for (ImageView diceImage : mDice) {
                Dice dice = (Dice) diceImage.getTag();
                dice.unSelect();
                updateDiceUI(diceImage, true, true);
            }
        }
    }

    private void updateDiceUI(ImageView diceImage, boolean isUpdatingBackground, boolean isUpdatingForeground) {

        Dice dice = (Dice)diceImage.getTag();

        if (isUpdatingBackground) {
            int bgColor = dice.isSelected() ? R.color.diceColorSelected: R.color.diceColorUnselected;
            diceImage.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), bgColor));
        }

        if (isUpdatingForeground) {
            int value = dice.getValue();

            Drawable fgImage;
            int resourceId;
            switch (value) {
                case 1:
                    resourceId = R.drawable.dice_1;
                    break;
                case 2:
                    resourceId = R.drawable.dice_2;
                    break;
                case 3:
                    resourceId = R.drawable.dice_3;
                    break;
                case 4:
                    resourceId = R.drawable.dice_4;
                    break;
                case 5:
                    resourceId = R.drawable.dice_5;
                    break;
                case 6:
                    resourceId = R.drawable.dice_6;
                    break;
                default:
                    return;
            }

            fgImage = ContextCompat.getDrawable(getApplicationContext(), resourceId);

            diceImage.setImageDrawable(fgImage);
        }
    }

    private TreeMap<Player, Integer> sortScores(HashMap<Player, Integer> unsortedMap) {

        Comparator<Player> comparator = new ValueComparator<>(unsortedMap);
        TreeMap<Player, Integer> sortedMap = new TreeMap<Player, Integer>(comparator);
        sortedMap.putAll(unsortedMap);

        return sortedMap;
    }

    private class DiceImageListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ImageView diceImage = (ImageView)v;

            if (state == State.PLAYING_GAME) {
                if (mRollsLeft < MAX_ROLLS && mRollsLeft > 0) {
                    Dice dice = (Dice)v.getTag();
                    dice.toggleSelected();

                    updateDiceUI(diceImage, true, false);
                }
            }
        }
    }

    private class RollButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            switch (state) {
                case DECIDING_PLAYER:
                    rollAvailableDice();
                    Integer[] combination = getCombination();

                    int score = 0;
                    for (int i = 0; i < combination.length; i++) {
                        score+= combination[i];
                    }

                    String initialRollMessage = mPlayers.get(mSelectedPlayerIndex).getName() + " rolled " + score + " points. ";

                    mInitialScores.add(new PlayerScore(mPlayers.get(mSelectedPlayerIndex), score));
                    if (mSelectedPlayerIndex + 1 == mPlayers.size()) {

                            Collections.sort(mInitialScores);
                            mPlayers.clear();

                            for (PlayerScore playerScore : mInitialScores) {
                                mPlayers.add(playerScore.getPlayer());
                            }

                            state = State.WAITING;
                            PlayerScore bestStarter = mInitialScores.get(0);
                            mMessageView.setText(initialRollMessage + "" + bestStarter.getName() + " begins!");
                            int startInterval = mNumberOfPlayers == 2 ? 2000 : 1000;
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    state = State.PLAYING_GAME;
                                    handler.post(turnChangeValidator);
                                }
                            }, startInterval);
                    } else {
                        mSelectedPlayerIndex++;
                        mMessageView.setText(initialRollMessage);
                    }

                    break;
                case WAITING:
                    mMessageView.setText("Please wait!");
                    break;
                case PLAYING_GAME:
                    if (mRollsLeft > 0) {
                        mRollsLeft--;
                        rollAvailableDice();
                        sendCombinationToPlayer();
                        mScoreBoardAdapter.notifyDataSetChanged();
                        mMessageView.setText(String.valueOf(mRollsLeft) + " rolls left.");
                    } else {
                        mMessageView.setText(mPlayers.get(mSelectedPlayerIndex).getName() + ", place your points!");
                    }
                    break;
                case ENDING_GAME:
                    mMessageView.setText("Game has ended, please exit now!");
                    break;
            }
        }

        private void rollAvailableDice() {
            for (ImageView diceImage : mDice) {
                Dice dice = (Dice)diceImage.getTag();
                dice.tryRoll();
                updateDiceUI(diceImage, true, true);
            }
        }

        private Integer[] getCombination() {
            Integer[] combination = new Integer[mDice.length];

            for (int i = 0; i < combination.length; i++) {
                Dice dice = (Dice)mDice[i].getTag();
                int value = dice.getValue();
                combination[i] = value;
            }

            return combination;
        }

        private void sendCombinationToPlayer() {
            Integer[] combination = getCombination();

            Player player = mPlayers.get(mSelectedPlayerIndex);
            player.updateScoreboard(combination);
        }
    }

    private AlertDialog createExitDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setMessage("Really want to exit?");
        builder.setTitle("Exit");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {}
        });

        AlertDialog dialog = builder.create();
        return dialog;
    }
}

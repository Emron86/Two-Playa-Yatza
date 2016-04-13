package com.yh.emil.yatza;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Emil on 2016-03-31.
 */
public class PregameSettingsActivity extends Activity {

    private final int NAME_MIN_LENGTH = 3;
    private final int NAME_MAX_LENGTH = 10;

    private EditText mPlayerOneNamingField;
    private EditText mPlayerTwoNamingField;
    private Spinner mNumberOfPlayersSpinner;

    private SharedPreferences sharedPreferences;
    private final String PLAYER_ONE_KEY = "playerOneName";
    private final String PLAYER_TWO_KEY = "playerTwoName";
    private final String SPINNER_SELECTION = "spinnerSelection";
    private final String NUMBER_OF_PLAYERS = "numberOfPlayers";
    private final Integer SINGLE_PLAYER = 0;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregame_settings);

        mPlayerOneNamingField = (EditText)findViewById(R.id.playerOneNamingField);
        mPlayerTwoNamingField = (EditText)findViewById(R.id.playerTwoNamingField);

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String playerOneName = sharedPreferences.getString(PLAYER_ONE_KEY, "Player1");
        String playerTwoName = sharedPreferences.getString(PLAYER_TWO_KEY, "Player2");
        Integer spinnerSelection = sharedPreferences.getInt(SPINNER_SELECTION, 1);

        mPlayerOneNamingField.setText(playerOneName);
        mPlayerTwoNamingField.setText(playerTwoName);

        mNumberOfPlayersSpinner = (Spinner)findViewById(R.id.numberOfPlayersSpinner);
        mNumberOfPlayersSpinner.setSelection(spinnerSelection);

        Button playButton = (Button) findViewById(R.id.pregameStartButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String playerOneName = mPlayerOneNamingField.getText().toString();
                String playerTwoName = mPlayerTwoNamingField.getText().toString();
                Integer spinnerSelection = mNumberOfPlayersSpinner.getSelectedItemPosition();
                //spinnerSelection is index, so add 1 to get actual number of players
                Integer numberOfPlayers = spinnerSelection + 1;

                if (isNameValid(playerOneName) && (isNameValid(playerTwoName)) || spinnerSelection == SINGLE_PLAYER) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(PLAYER_ONE_KEY, playerOneName);
                    editor.putString(PLAYER_TWO_KEY, playerTwoName);
                    editor.putInt(SPINNER_SELECTION, spinnerSelection);
                    editor.commit();

                    Intent i = new Intent(v.getContext(), GameActivity.class);
                    i.putExtra(PLAYER_ONE_KEY, playerOneName);
                    i.putExtra(PLAYER_TWO_KEY, playerTwoName);
                    i.putExtra(NUMBER_OF_PLAYERS, numberOfPlayers);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Name must be between " + NAME_MIN_LENGTH + "-" + NAME_MAX_LENGTH
                                    + " letters and not contains spaces.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView goBackImage = (ImageView) findViewById(R.id.pregameBackButton);
        goBackImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    ((ImageView)view).setImageResource(R.drawable.back_button_pressed);
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((ImageView)view).setImageResource(R.drawable.back_button);
                    Intent i = new Intent(view.getContext(), MainActivity.class);
                    startActivity(i);
                }
                return true;
            }
        });
    }

    private boolean isNameValid(String name) {
        if (name != null) {
            int length = name.length();
            if (length >= NAME_MIN_LENGTH && length <= NAME_MAX_LENGTH && !name.contains(" "))
                return true;
        }
        return false;
    }
}

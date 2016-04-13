package com.yh.emil.yatza.models.scores;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yh.emil.yatza.R;

/**
 * Created by Emil on 2016-04-09.
 */
public class ScoreBoardAdapter extends BaseAdapter {

    private Context mContext;
    private ScoreBoard mScoreBoard;
    private View.OnClickListener mScoreClickListener;

    public ScoreBoardAdapter(Context context, ScoreBoard scoreBoard) {
        this.mContext = context;
        this.mScoreBoard = scoreBoard;
    }

    public ScoreBoardAdapter(Context context, ScoreBoard scoreBoard, View.OnClickListener scoreClickListener) {
        this.mContext = context;
        this.mScoreBoard = scoreBoard;
        this.mScoreClickListener = scoreClickListener;
    }

    public void setScoreBoard(ScoreBoard scoreBoard) {
        ScoreBoard oldScoreBoard = this.mScoreBoard;
        this.mScoreBoard = scoreBoard;
        this.notifyDataSetChanged();
        if (oldScoreBoard != null) {
            oldScoreBoard.lockUpBoard();
            oldScoreBoard.clearScores();
        }
    }

    public boolean processClickOnScoreView(View view) {
        Integer position = (Integer)view.getTag();
        if (mScoreBoard.tryToAddScore(position)) {
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    public void setScoreClickListener(View.OnClickListener mScoreClickListener) {
        this.mScoreClickListener = mScoreClickListener;
    }

    @Override
    public int getCount() {
        return mScoreBoard.getCount();
    }

    @Override
    public Object getItem(int position) {
        return mScoreBoard.getEntry(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        PlaceHolder holder = null;
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(mContext);
            view = li.inflate(R.layout.combination_row, parent, false);
            holder = new PlaceHolder();
            holder.name = (TextView)view.findViewById(R.id.combinationName);
            holder.score = (TextView)view.findViewById(R.id.combinationScore);
            view.setTag(holder);
        } else {
            holder = (PlaceHolder)view.getTag();
        }

        holder.name.setText(mScoreBoard.getName(position));
        holder.score.setText(mScoreBoard.getScore(position));

        int bgColorScore = 0;
        int bgColorName = 0;

        if (mScoreBoard.isSet(position)) {
            bgColorName = Color.parseColor("#88AA88");
            bgColorScore = Color.parseColor("#E4ADAD");
        } else {
            bgColorName = Color.parseColor("#AACCAA");
            bgColorScore = Color.parseColor("#BEEEEF");
        }

        holder.name.setBackgroundColor(bgColorName);
        holder.score.setBackgroundColor(bgColorScore);
        if (mScoreClickListener != null) {
            holder.score.setOnClickListener(mScoreClickListener);
        }

        Integer rowPosition = position;
        holder.score.setTag(rowPosition);

        return view;
    }

    private class PlaceHolder {
        private TextView name;
        private TextView score;
    }
}

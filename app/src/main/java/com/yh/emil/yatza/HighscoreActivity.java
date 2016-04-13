package com.yh.emil.yatza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yh.emil.yatza.models.toplist.TopList;
import com.yh.emil.yatza.models.toplist.TopListEntry;
import com.yh.emil.yatza.models.toplist.TopListManager;
import com.yh.emil.yatza.models.toplist.ToplistEntryAdapter;

import java.util.List;

public class HighscoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        ListView highscoreList = (ListView) findViewById(R.id.highscoreList);
        List<TopListEntry> topListEntries = getTopListEntries();

        ToplistEntryAdapter topListEntryAdapter = new ToplistEntryAdapter(this, R.layout.entry_row, topListEntries);

        if (topListEntries != null) {
            highscoreList.setAdapter(topListEntryAdapter);
        }

        ImageView goBackImage = (ImageView) findViewById(R.id.highscoreBackButton);
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

        TextView highscoreHeader = (TextView) findViewById(R.id.highscoreHeader);
        highscoreHeader.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TopListManager topListManager = new TopListManager();
                topListManager.deleteTopList(getApplicationContext());
                Toast.makeText(getApplicationContext(), "TopList deleted!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<TopListEntry> getTopListEntries() {

        TopListManager topListManager = new TopListManager();
        TopList topList = topListManager.loadTopList(this);

        return topList.getEntries();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

package com.yh.emil.yatza.models.toplist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yh.emil.yatza.R;

import java.util.List;

/**
 * Created by Emil on 2016-04-04.
 */
public class ToplistEntryAdapter extends ArrayAdapter {

    private Context mContext;
    private Integer mLayoutResourceId;
    private List<TopListEntry> mData;

    public ToplistEntryAdapter(Context context, int resource, List<TopListEntry> data) {
        super(context, resource, data);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mData = data;
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        row = inflater.inflate(mLayoutResourceId, parent, false);

        TextView entryRank = (TextView)row.findViewById(R.id.entryRank);
        TextView entryName = (TextView)row.findViewById(R.id.entryName);
        TextView entryScore = (TextView)row.findViewById(R.id.entryScore);

        TopListEntry topListEntry = mData.get(position);

        entryRank.setText(position + 1 + ".");
        entryName.setText(topListEntry.getName());
        entryScore.setText("ScoreEntry2: " + String.valueOf(topListEntry.getScore()));

        return row;
    }
}

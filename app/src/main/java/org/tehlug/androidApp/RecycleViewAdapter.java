package org.tehlug.androidApp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * Created by behdad on 1/30/15.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private ArrayList<RssItem> rssItems;
    private Context context;

    public RecycleViewAdapter(ArrayList<RssItem> rssItems, Context context) {
        this.rssItems = rssItems;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView topic;
        TextView date;
        TextView title;

        public ViewHolder(View v) {
            super(v);
            topic = (TextView) itemView.findViewById(R.id.topic);
            date = (TextView) itemView.findViewById(R.id.date);
            title = (TextView) itemView.findViewById(R.id.title);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent (context, DescriptionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("id", getPosition());
            context.startActivity(intent);
        }
    }

    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meetings_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RssItem rssItem = rssItems.get(position);
        holder.topic.setText(rssItem.getTopic());
        holder.date.setText(rssItem.getDate());
        holder.title.setText(rssItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return rssItems.size();
    }
}

package org.tehlug.androidApp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private ArrayList<RssItem> rssItems;
    private Context context;

    public RecycleViewAdapter(ArrayList<RssItem> rssItems, Context context) {
        this.rssItems = rssItems;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView topic;
        TextView date;
        TextView title;
        MaterialRippleLayout ripple;

        public ViewHolder(View v) {
            super(v);
            topic = (TextView) itemView.findViewById(R.id.topic);
            date = (TextView) itemView.findViewById(R.id.date);
            title = (TextView) itemView.findViewById(R.id.title);

            ripple = (MaterialRippleLayout) itemView.findViewById(R.id.ripple);

            ripple.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, DescriptionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("id", getPosition());
            context.startActivity(intent);
        }
    }

    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meetings_item, parent, false);

        return new ViewHolder(v);
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

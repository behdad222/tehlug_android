package org.tehlug.androidApp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * Created by behdad on 1/30/15.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private ArrayList<RssItem> rssItems;
    private Context context;
    private int centerX;
    private int centerY;
    private View grayView1;
    private View grayView2;
    private long duration;

    public RecycleViewAdapter(ArrayList<RssItem> rssItems, Context context) {
        this.rssItems = rssItems;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener {
        TextView topic;
        TextView date;
        TextView title;

        public ViewHolder(View v) {
            super(v);
            topic = (TextView) itemView.findViewById(R.id.topic);
            date = (TextView) itemView.findViewById(R.id.date);
            title = (TextView) itemView.findViewById(R.id.title);

            v.setOnClickListener(this);
            v.setOnTouchListener(this);
            v.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (Build.VERSION_CODES.LOLLIPOP<=Build.VERSION.SDK_INT) {
                if (View.VISIBLE != grayView1.getVisibility()) {
                    grayView1.setVisibility(View.VISIBLE);
                    duration = 300;
                    int finalRadius = Math.max(grayView2.getWidth(), grayView2.getHeight());

                    Animator anim = ViewAnimationUtils.createCircularReveal(
                            grayView2, centerX, centerY, 0, finalRadius).setDuration(duration);

                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            Intent intent = new Intent(context, DescriptionActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("id", getPosition());
                            context.startActivity(intent);
                            grayView2.setVisibility(View.INVISIBLE);
                            grayView1.setVisibility(View.INVISIBLE);
                        }
                    });
                    grayView2.setVisibility(View.VISIBLE);
                    anim.start();
                } else {
                    Intent intent = new Intent(context, DescriptionActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id", getPosition());
                    context.startActivity(intent);
                    grayView2.setVisibility(View.INVISIBLE);
                    grayView1.setVisibility(View.INVISIBLE);
                }
            } else {
                Intent intent = new Intent(context, DescriptionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", getPosition());
                context.startActivity(intent);
            }
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                grayView1 = view.findViewById(R.id.color);
                grayView2 = view.findViewById(R.id.color2);
                centerX = (int) event.getX();
                centerY = (int) event.getY();
            }
            else if (event.getAction() == MotionEvent.ACTION_CANCEL ){
                grayView1.setVisibility(View.INVISIBLE);
                grayView2.setVisibility(View.INVISIBLE);
            }
            return false;
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onLongClick(View view) {
            grayView1.setVisibility(View.VISIBLE);
            duration = 1500;
            int finalRadius = Math.max(grayView2.getWidth(), grayView2.getHeight());

            Animator anim = ViewAnimationUtils.createCircularReveal(
                    grayView2, centerX, centerY, 0, finalRadius).setDuration(duration);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                }
            });
            grayView2.setVisibility(View.VISIBLE);
            anim.start();
            return false;
        }
    }

    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        // create a new view
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

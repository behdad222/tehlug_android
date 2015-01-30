package org.tehlug.androidApp;

/*
 * Created by behdad on 1/30/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DescriptionActivity extends Activity{
    int id;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_description);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        RssItem rssItem = MainActivity.rssItems.get(id);

        TextView topic;
        TextView date;
        TextView description;

        topic = (TextView) findViewById(R.id.topic);
        date = (TextView) findViewById(R.id.date);
        description = (TextView) findViewById(R.id.description);

        topic.setText(rssItem.getTopic());
        date.setText(rssItem.getDate());
        description.setText(rssItem.getDescription() + "\n");


    }
}
package org.tehlug.androidApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

public class DescriptionActivity extends ActionBarActivity {
    int id;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_description);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        Realm realm = Realm.getInstance(this);

        RealmResults<Meeting> result = realm
                .where(Meeting.class)
                .equalTo("id", id)
                .findAll();

        Meeting meeting = result.first();
        setTitle(meeting.getTitle());

        TextView topic;
        TextView date;
        TextView description;

        topic = (TextView) findViewById(R.id.topic);
        date = (TextView) findViewById(R.id.date);
        description = (TextView) findViewById(R.id.description);

        topic.setText(meeting.getTopic());
        date.setText(meeting.getDate());
        description.setText(meeting.getDescription() + "\n");
    }
}
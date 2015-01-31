package org.tehlug.androidApp;

/*
 * Created by behdad on 2/1/15.
 */

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class CooperationActivity extends ActionBarActivity {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_about_tehlug);
        TextView textView = (TextView) findViewById(R.id.about_tehlug);
        textView.setText(R.string.cooperation);
    }
}
package org.tehlug.androidApp;

/*
 * Created by behdad on 1/31/15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class AboutTehlugActivity extends ActionBarActivity {
    int id;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_about_tehlug);
    }
}
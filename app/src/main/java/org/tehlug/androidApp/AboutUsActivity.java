package org.tehlug.androidApp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AboutUsActivity extends ActionBarActivity implements View.OnClickListener {
    TextView licence;
    int count = 0;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_aboutus);
        licence = (TextView) findViewById(R.id.licence);

        licence.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.licence) {
            if (count >= 5) {
                Toast.makeText(
                        getBaseContext(),
                        getString(R.string.about_hidden),
                        Toast.LENGTH_LONG).show();
            } else count ++;
        }
    }
}
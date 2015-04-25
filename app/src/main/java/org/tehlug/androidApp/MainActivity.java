package org.tehlug.androidApp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.pkmmte.pkrss.Article;
import com.pkmmte.pkrss.Callback;
import com.pkmmte.pkrss.PkRSS;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends ActionBarActivity implements Callback,View.OnClickListener {
    private RecyclerView meetingRecycleView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private Activity activity;
    private TextView noNet;
    private Button tryAgain;
    private ProgressBar progressBar;
    private Realm realm;
    private ArrayList<Meeting> meetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getInstance(this);
        meetings = new ArrayList<>();

        meetingRecycleView = (RecyclerView) findViewById(R.id.meeting_recycle_view);
        meetingRecycleView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        meetingRecycleView.setLayoutManager(layoutManager);
        adapter = new RecycleViewAdapter(meetings, this);
        meetingRecycleView.setAdapter(adapter);

        noNet = (TextView) findViewById(R.id.noNet);
        tryAgain = (Button) findViewById(R.id.tryAgain);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Tracker tracker = ((LugApplication) getApplication()).getTracker(
                LugApplication.TrackerName.APP_TRACKER);

        tracker.setScreenName(this.getClass().getSimpleName());
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        tryAgain.setOnClickListener(this);

        if (Util.isOnline(this)) {
            meetingRecycleView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            noNet.setVisibility(View.GONE);
            tryAgain.setVisibility(View.GONE);
            PkRSS.with(this).load(getString(R.string.rssURL)).callback(this).async();

        } else if (!getCash()) {
            meetingRecycleView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            noNet.setVisibility(View.VISIBLE);
            tryAgain.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;

        switch (id) {
            case R.id.action_place:
                    new AlertDialog.Builder(this)
                            .setTitle(getString(R.string.action_place))
                            .setMessage(getString(R.string.place))
                            .setNeutralButton(getString(R.string.map), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Uri gmmIntentUri = Uri.parse(getString(R.string.geo)).buildUpon().build();
                                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                        mapIntent.setPackage("com.google.android.apps.maps");
                                        startActivity(mapIntent);
                                    } catch (Exception e) {
                                        Toast.makeText(
                                                getBaseContext(),
                                                getString(R.string.cant_show_map),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .setIcon(R.drawable.ic_place_grey600_24dp)
                            .show();

                return true;

            case R.id.action_about_tehlug:
                intent = new Intent(activity, AboutTehlugActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                return true;

            case R.id.action_cooperation:
                intent = new Intent(activity, CooperationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                return true;

            case R.id.action_others:
                intent = new Intent(activity, OthersActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                return true;

            case R.id.action_organization:
                intent = new Intent(activity, OrganizationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                return true;

            case R.id.action_about_us:
                intent = new Intent(activity, AboutUsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                return true;
        }
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    void dataSet(List<Article> articles){
        for (int item = 0; item < articles.size(); item++) {
            final Meeting meeting = new Meeting();
            meeting.setTitle(articles.get(item).getTitle());

            String[] separated = articles.get(item).getDescription().split(": ");
            String[] separatedDate = separated[1].split("\t");
            meeting.setDate(separatedDate[0]);

            String[] separatedTopic = separated[2].split("\t");
            meeting.setTopic(separatedTopic[0]);

            String[] separatedDescription = articles.get(item).getDescription().split("\t\n");
            meeting.setDescription(separatedDescription[2].substring(5));

            meeting.setId(articles.get(item).getId());
            meeting.setSource(String.valueOf(articles.get(item).getSource()));

            meetings.add(meeting);

            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(meeting);
                    realm.commitTransaction();
                }
            });
        }

        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                adapter.notifyDataSetChanged();

                meetingRecycleView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                noNet.setVisibility(View.GONE);
                tryAgain.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void OnPreLoad() {

    }

    @Override
    public void OnLoaded(List<Article> articles) {
        dataSet(articles);
    }

    @Override
    public void OnLoadFailed() {
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                meetingRecycleView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                noNet.setVisibility(View.VISIBLE);
                tryAgain.setVisibility(View.VISIBLE);
            }
        });
    }

    private boolean getCash() {
        RealmResults<Meeting> result = realm
                .where(Meeting.class)
                .findAll();

        if (result.size() == 0) return false;

        for (int i = 0; i < result.size(); i++)
            meetings.add(result.get(i));

        adapter.notifyDataSetChanged();

        meetingRecycleView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        noNet.setVisibility(View.GONE);
        tryAgain.setVisibility(View.GONE);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tryAgain) {
            meetingRecycleView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            noNet.setVisibility(View.GONE);
            tryAgain.setVisibility(View.GONE);
            PkRSS.with(this).load(getString(R.string.rssURL)).callback(this).async();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}

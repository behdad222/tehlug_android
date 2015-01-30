package org.tehlug.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {
    private RecyclerView meetingRecycleView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meetingRecycleView = (RecyclerView) findViewById(R.id.meeting_recycle_view);
        meetingRecycleView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        meetingRecycleView.setLayoutManager(layoutManager);

        adapter = new RecycleViewAdapter();
        meetingRecycleView.setAdapter(adapter);

        getFromServer();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getFromServer() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(getString(R.string.domainURL))
                .build();

        GetMeetingsApi getMeetingsApi = restAdapter.create(GetMeetingsApi.class);
        getMeetingsApi.getMeetings(
                getString(R.string.api_key),
                new Callback<MeetingsResponse>() {
                    @Override
                    public void success(MeetingsResponse meetingsResponse, Response response) {

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }
}

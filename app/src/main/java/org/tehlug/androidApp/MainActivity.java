package org.tehlug.androidApp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.pkmmte.pkrss.Article;
import com.pkmmte.pkrss.Callback;
import com.pkmmte.pkrss.PkRSS;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private RecyclerView meetingRecycleView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<RssItem> rssItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        meetingRecycleView = (RecyclerView) findViewById(R.id.meeting_recycle_view);
        meetingRecycleView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        meetingRecycleView.setLayoutManager(layoutManager);
        rssItems = new ArrayList<>();
        adapter = new RecycleViewAdapter(rssItems);
        meetingRecycleView.setAdapter(adapter);

        PkRSS.with(this).load("http://tehlug.org/rss.php").callback(new Callback() {
            @Override
            public void OnPreLoad() {

            }

            @Override
            public void OnLoaded(List<Article> articles) {
                dataSet(articles);
            }

            @Override
            public void OnLoadFailed() {

            }
        }).async();
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

    void dataSet(List<Article> articles){
        for (int item = 0; item < articles.size(); item++) {
            RssItem rssItem = new RssItem();

            rssItem.setTitle(articles.get(item).getTitle());

            String[] separated = articles.get(item).getDescription().split(":");
            String date = separated[1];
            String description = separated[2];

            String[] separatedDate = date.split("\t");
            rssItem.setDate(separatedDate[0]);

            String[] separatedDescription = description.split("\t");
            rssItem.setDescription(separatedDescription[0]);

            rssItem.setId(articles.get(item).getId());
            rssItem.setSource(articles.get(item).getSource());

            rssItems.add(rssItem);
        }

        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                meetingRecycleView.getAdapter().notifyDataSetChanged();
            }
        });

    }
}

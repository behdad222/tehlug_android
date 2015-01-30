package org.tehlug.androidApp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.pkmmte.pkrss.Article;
import com.pkmmte.pkrss.PkRSS;

import java.util.List;

public class MainActivity extends ActionBarActivity implements com.pkmmte.pkrss.Callback {
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

        PkRSS.with(this).load("http://tehlug.org/rss.php").callback(this).async();
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

    @Override
    public void OnPreLoad() {}

    @Override
    public void OnLoaded(List<Article> articles) {
        for (int a=0 ; a<5; a++){
            try {
                Log.d("title", articles.get(a).getTitle());
                //Log.d("Author", articles.get(a).getAuthor());
                //Log.d("Comments", articles.get(a).getComments());
                //Log.d("content", articles.get(a).getContent());
                Log.d("desription", articles.get(a).getDescription());
                Log.d("tags", String.valueOf(articles.get(a).getTags()));
                Log.d("source", String.valueOf(articles.get(a).getSource()));
                Log.d("id", String.valueOf(articles.get(a).getId()));
                Log.d("extra", String.valueOf(articles.get(a).getExtras()));
                Log.d("date", String.valueOf(articles.get(a).getDate()));
                Log.d("image", String.valueOf(articles.get(a).getImage()));
            } catch (Exception e){
                e.printStackTrace();
            }
;        }
    }

    @Override
    public void OnLoadFailed() {}
}

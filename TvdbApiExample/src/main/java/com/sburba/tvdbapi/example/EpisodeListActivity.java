package com.sburba.tvdbapi.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sburba.tvdbapi.TvdbApi;
import com.sburba.tvdbapi.TvdbItemAdapter;
import com.sburba.tvdbapi.model.Episode;
import com.sburba.tvdbapi.model.Season;

import java.util.Collection;

public class EpisodeListActivity extends Activity {

    public static final String EXTRA_SEASON = "season";
    private static final String TAG = "EpisodeListActivity";

    private TvdbItemAdapter<Episode> mEpisodeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App app = App.getInstance(this);
        mEpisodeAdapter =
                new TvdbItemAdapter<Episode>(this, app.getImageLoader(), R.layout.tvdb_item,
                                             R.id.title, R.id.image);

        setContentView(R.layout.grid_list);
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(mEpisodeAdapter);
        gridView.setOnItemClickListener(mOnEpisodeClickListener);
        Intent intent = getIntent();
        Season season = intent.getParcelableExtra(EXTRA_SEASON);
        if (season != null) {
            TvdbApi tvdbApi = new TvdbApi(App.TVDB_API_KEY, "en", app.getRequestQueue());
            tvdbApi.getEpisodes(season, mEpisodeResponseListener, mErrorListener);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private AdapterView.OnItemClickListener mOnEpisodeClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(EpisodeListActivity.this,
                                   mEpisodeAdapter.getItem(position).getDescText(),
                                   Toast.LENGTH_SHORT).show();
                }
            };

    private Response.Listener<Collection<Episode>> mEpisodeResponseListener =
            new Response.Listener<Collection<Episode>>() {
                @Override
                public void onResponse(Collection<Episode> episodes) {
                    mEpisodeAdapter.addAll(episodes);
                }
            };

    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(EpisodeListActivity.this, "Oh noes! Something has gone awry.",
                           Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Error fetching Episodes: ", volleyError);
        }
    };
}

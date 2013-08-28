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
import com.sburba.tvdbapi.model.Banner;
import com.sburba.tvdbapi.model.Season;
import com.sburba.tvdbapi.model.Series;

import java.util.Collection;

public class SeasonListActivity extends Activity {

    public static final String EXTRA_SERIES = "series";

    private static final String TAG = "SeasonListActivity";

    private TvdbItemAdapter<Season> mSeasonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App app = App.getInstance(this);
        mSeasonAdapter = new TvdbItemAdapter<Season>(this, app.getImageLoader(),
                                                     R.layout.tvdb_item, R.id.title,
                                                     R.id.image);

        setContentView(R.layout.grid_list);
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setOnItemClickListener(mOnSeasonSelectedListener);
        gridView.setAdapter(mSeasonAdapter);

        Intent intent = getIntent();
        Series series = intent.getParcelableExtra(EXTRA_SERIES);
        if (series != null) {
            TvdbApi tvdbApi = new TvdbApi(App.TVDB_API_KEY, "en", app.getRequestQueue());
            tvdbApi.getSeasons(series, mSeasonResponseListener, mErrorListener);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private AdapterView.OnItemClickListener mOnSeasonSelectedListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(SeasonListActivity.this,
                                   mSeasonAdapter.getItem(position).getTitleText(),
                                   Toast.LENGTH_SHORT).show();
                    Intent episodeList =
                            new Intent(SeasonListActivity.this, EpisodeListActivity.class);
                    episodeList.putExtra(EpisodeListActivity.EXTRA_SEASON,
                                         mSeasonAdapter.getItem(position));
                    startActivity(episodeList);
                }
            };

    private Response.Listener<Collection<Season>> mSeasonResponseListener =
            new Response.Listener<Collection<Season>>() {
                @Override
                public void onResponse(Collection<Season> seasons) {
                    for (Season season : seasons) {
                        for (Banner banner : season.banners) {
                            Log.d(TAG, "type1: " + banner.type + " type2: " + banner.type2);
                        }
                    }
                    mSeasonAdapter.addAll(seasons);
                }
            };

    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(SeasonListActivity.this, "Oh noes! Something has gone awry.",
                           Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Error fetching seasons: ", volleyError);
        }
    };
}

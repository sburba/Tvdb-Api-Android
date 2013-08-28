Tvdb-Api-Android
================

Tvdb API that uses Volley for easy asynchronous API requests on Android.

Everything you receive is parcelable so it's easy to pass data between Activities


## Usage
All TvdbApi calls are asynchronous, so you will need to create listeners to receive responses

```java
private Response.Listener<Collection<Series>> mSeriesResponseListener = new Response.Listener<Collection<Series>>() {
    @Override
    public void onResponse(Collection<Series> serieses) {
        for(Series series : serieses) {
            Log.d(TAG, "I found a series: " + series.name); 
        }
    }
};

private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Log.e(TAG, "Oh noes! Something has gone awry: ", volleyError);
    }
};
```

Then just call create the TvdbApi object and make your request:

```java
TvdbApi tvdbApi = new TvdbApi("<YOUR TVDB API KEY>", "en", mRequestQueue);

tvdbApi.searchSeries("Futurama", mSeriesResponseListener, mErrorListener);
```

Take a look at the example project for a more thorough demonstration

Tvdb-Api-Android
================

Tvdb API that uses Volley for easy asynchronous API requests on Android.

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

## Building the example project
As it is set up, it will only build with gradle. Sorry, ant/eclipse users :(. If you're using Android Studio that will work since it uses gradle.

First, get an API key from [theTVDB.com](http://thetvdb.com)

Then, clone the project and init the volley submodule

```shell
git clone git@github.com:sburba/Tvdb-Api-Android.git
git submodule init
git submodule update
```

If you're using Android Studio, import the project **make sure you use import from external model: Gradle**

Edit com.sburba.tvdbapi.example.App and replace `<YOUR API KEY>` with your api key.

`./gradlew assembleDebug` or run using Android Studio

If you're having problems building or adding to your project don't be afraid to contact me.

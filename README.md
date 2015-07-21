Tvdb-Api-Android
================

Tvdb API for easy asynchronous API requests on Android.

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

Then just create the TvdbApi object and make your request:

```java
TvdbApi tvdbApi = new TvdbApi("<YOUR TVDB API KEY>", "en", mRequestQueue);

tvdbApi.searchSeries("Futurama", mSeriesResponseListener, mErrorListener);
```

Take a look at the example project for a more thorough demonstration

## Building the example project
As it is set up, it will only build with gradle. Sorry, ant/eclipse users :(. If you're using Android Studio that will work since it uses gradle.

First, get an API key from [theTVDB.com](http://thetvdb.com)

Then, clone the project

```shell
git clone git@github.com:sburba/Tvdb-Api-Android.git
```
Create a local.properties file, following the example local.properties file

If you're using Android Studio, import the project **make sure you use import from external model: Gradle**

Edit com.sburba.tvdbapi.example.App and replace `<YOUR API KEY>` with your api key.

`./gradlew assembleDebug` or run using Android Studio

If you're having problems building or adding to your project don't be afraid to contact me.

## Including the Library in your project
First clone the project

```shell
git clone git@github.com:sburba/Tvdb-Api-Android.git
```

Run ```./gradlew installArchives``` in the root of the cloned project

In your project add mavenLocal() to your root build.gradle's list of repositories:

        allprojects {
            repositories {
                jcenter()
                mavenLocal()
            }
        }

In your module add ```compile com.sburba:tvdbapi:1.0.0``` to the dependencies

Thanks to Github User [bigno](https://github.com/bigno) for helping me diagnose a few issues with banner and actor parsing!

package com.sburba.tvdbapi.model;

public abstract class TvdbItem {

    public static final int NOT_PRESENT = -1;
    protected static String BASE_IMAGE_URL = "http://thetvdb.com/banners/";

    public abstract String getImageUrl();

    public abstract String getTitleText();

    public abstract String getDescText();
}

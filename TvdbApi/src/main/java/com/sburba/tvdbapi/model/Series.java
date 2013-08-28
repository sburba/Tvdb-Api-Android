package com.sburba.tvdbapi.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.sburba.tvdbapi.xml.XmlException;
import com.sburba.tvdbapi.xml.XmlUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * TVDB Series metadata
 * *Warning* Objects not present in the XML will be null, numbers not present will be NOT_PRESENT
 *
 * @see <a href="http://thetvdb.com/wiki/index.php/API:Base_Series_Record.xml">TVDB API Base Series Record</a>
 */
public class Series extends TvdbItem implements Parcelable {

    /**
     * TVDB Series id
     */
    public final int id;
    public final String[] actors;
    /**
     * Can be "Monday" through "Sunday"
     */
    public final String airsDayOfWeek;
    /**
     * I don't know what timezone TVDB uses but it will look something like this: "8:00 PM"
     */
    public final String airsTime;
    /**
     * Your standard content rating string
     * Example: "TV-PG"
     */
    public final String contentRating;
    public final Date firstAired;
    public final String[] genres;
    public final String imdbId;
    public final String language;
    public final String network;
    public final int networkId;
    public final String overview;
    public final float rating;
    public final int ratingCount;
    public final int runtime;
    /**
     * Series id used on TV.com
     * <b>NOT THE TVDB SERIES ID</b>
     */
    public final int tvComId;
    public final String name;
    public final String status;
    public final String added;
    public final String addedBy;
    public final String banner;
    public final String fanart;
    public final long lastUpdated;
    public final String poster;
    public final String zap2itId;

    private static final String TAG = "Series";
    private static final boolean D = false;

    /**
     * Infuriatingly the api specs define the TVDB Series ID as 'id' in some places and 'seriesid'
     * in others.
     */
    private static final String TAG_ID = "id";
    private static final String TAG_ID2 = "seriesid";
    private static final String TAG_ACTORS = "Actors";
    private static final String TAG_AIRS_DAY_OF_WEEK = "Airs_DayOfWeek";
    private static final String TAG_AIRS_TIME = "Airs_Time";
    private static final String TAG_CONTENT_RATING = "ContentRating";
    private static final String TAG_FIRST_AIRED = "FirstAired";
    private static final String TAG_GENRES = "Genre";
    private static final String TAG_IMDB_ID = "IMDB_ID";
    /** Language is also defined differently in different places*/
    private static final String TAG_LANGUAGE = "Language";
    private static final String TAG_LANGUAGE2 = "language";
    private static final String TAG_NETWORK = "Network";
    private static final String TAG_NETWORK_ID = "NetworkID";
    private static final String TAG_OVERVIEW = "Overview";
    private static final String TAG_RATING = "Rating";
    private static final String TAG_RATING_COUNT = "RatingCount";
    private static final String TAG_RUNTIME = "Runtime";
    /** What's even more ridiculous is there's a 'SeriesID' tag which is actually the TV.com id*/
    private static final String TAG_TV_COM_ID = "SeriesID";
    private static final String TAG_NAME = "SeriesName";
    private static final String TAG_STATUS = "Status";
    private static final String TAG_ADDED = "added";
    private static final String TAG_ADDED_BY = "addedBy";
    private static final String TAG_BANNER = "banner";
    private static final String TAG_FANART = "fanart";
    private static final String TAG_LAST_UPDATED = "lastupdated";
    private static final String TAG_POSTER = "poster";
    private static final String TAG_ZAP2IT_ID = "zap2it_id";
    private static final String DELIMITER = "|";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static Series fromXml(XmlPullParser parser)
            throws XmlPullParserException, IOException, XmlException {
        Builder builder = new Builder();
        while (parser.nextTag() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) continue;

            String tag = parser.getName();
            if (tag.equals(TAG_ID)) {
                builder.setId(XmlUtil.readInt(parser, TAG_ID, NOT_PRESENT));
            } else if (tag.equals(TAG_ID2)) {
                builder.setId(XmlUtil.readInt(parser, TAG_ID2, NOT_PRESENT));
            } else if (tag.equals(TAG_ACTORS)) {
                builder.setActors(XmlUtil.readStringArray(parser, TAG_ACTORS, DELIMITER));
            } else if (tag.equals(TAG_AIRS_DAY_OF_WEEK)) {
                builder.setAirsDayOfWeek(XmlUtil.readText(parser, TAG_AIRS_DAY_OF_WEEK));
            } else if (tag.equals(TAG_AIRS_TIME)) {
                builder.setAirsTime(XmlUtil.readText(parser, TAG_AIRS_TIME));
            } else if (tag.equals(TAG_CONTENT_RATING)) {
                builder.setContentRating(XmlUtil.readText(parser, TAG_CONTENT_RATING));
            } else if (tag.equals(TAG_FIRST_AIRED)) {
                builder.setFirstAired(XmlUtil.readDate(parser, TAG_FIRST_AIRED, DATE_FORMAT));
            } else if (tag.equals(TAG_GENRES)) {
                builder.setGenres(XmlUtil.readStringArray(parser, TAG_GENRES, DELIMITER));
            } else if (tag.equals(TAG_IMDB_ID)) {
                builder.setImdbId(XmlUtil.readText(parser, TAG_IMDB_ID));
            } else if (tag.equals(TAG_LANGUAGE)) {
                builder.setLanguage(XmlUtil.readText(parser, TAG_LANGUAGE));
            } else if (tag.equals(TAG_LANGUAGE2)) {
                builder.setLanguage(XmlUtil.readText(parser, TAG_LANGUAGE2));
            } else if (tag.equals(TAG_NETWORK)) {
                builder.setNetwork(XmlUtil.readText(parser, TAG_NETWORK));
            } else if (tag.equals(TAG_NETWORK_ID)) {
                builder.setNetworkId(XmlUtil.readInt(parser, TAG_NETWORK_ID, NOT_PRESENT));
            } else if (tag.equals(TAG_OVERVIEW)) {
                builder.setOverview(XmlUtil.readText(parser, TAG_OVERVIEW));
            } else if (tag.equals(TAG_RATING)) {
                builder.setRating(XmlUtil.readFloat(parser, TAG_RATING, NOT_PRESENT));
            } else if (tag.equals(TAG_RATING_COUNT)) {
                builder.setRatingCount(XmlUtil.readInt(parser, TAG_RATING_COUNT, NOT_PRESENT));
            } else if (tag.equals(TAG_RUNTIME)) {
                builder.setRuntime(XmlUtil.readInt(parser, TAG_RUNTIME, NOT_PRESENT));
            } else if (tag.equals(TAG_TV_COM_ID)) {
                builder.setTvComId(XmlUtil.readInt(parser, TAG_TV_COM_ID, NOT_PRESENT));
            } else if (tag.equals(TAG_NAME)) {
                builder.setName(XmlUtil.readText(parser, TAG_NAME));
            } else if (tag.equals(TAG_STATUS)) {
                builder.setStatus(XmlUtil.readText(parser, TAG_STATUS));
            } else if (tag.equals(TAG_ADDED)) {
                builder.setAdded(XmlUtil.readText(parser, TAG_ADDED));
            } else if (tag.equals(TAG_ADDED_BY)) {
                builder.setAddedBy(XmlUtil.readText(parser, TAG_ADDED_BY));
            } else if (tag.equals(TAG_BANNER)) {
                builder.setBanner(XmlUtil.readText(parser, TAG_BANNER));
            } else if (tag.equals(TAG_FANART)) {
                builder.setFanart(XmlUtil.readText(parser, TAG_FANART));
            } else if (tag.equals(TAG_LAST_UPDATED)) {
                builder.setLastUpdated(XmlUtil.readLong(parser, TAG_LAST_UPDATED, NOT_PRESENT));
            } else if (tag.equals((TAG_POSTER))) {
                builder.setPoster(XmlUtil.readText(parser, TAG_POSTER));
            } else if (tag.equals(TAG_ZAP2IT_ID)) {
                builder.setZap2itId(XmlUtil.readText(parser, TAG_ZAP2IT_ID));
            } else {
                if (D) Log.d(TAG, "Skipping tag: " + tag);
                XmlUtil.skip(parser);
            }
        }
        return builder.build();
    }

    private Series(int id, String[] actors, String airsDayOfWeek, String airsTime,
                   String contentRating, Date firstAired, String[] genres, String imdbId,
                   String language, String network, int networkId, String overview, float rating,
                   int ratingCount, int runtime, int tvComId, String name, String status,
                   String added, String addedBy, String banner, String fanart, long lastUpdated,
                   String poster, String zap2itId) {
        this.id = id;
        this.actors = actors;
        this.airsDayOfWeek = airsDayOfWeek;
        this.airsTime = airsTime;
        this.contentRating = contentRating;
        this.firstAired = firstAired;
        this.genres = genres;
        this.imdbId = imdbId;
        this.language = language;
        this.network = network;
        this.networkId = networkId;
        this.overview = overview;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.runtime = runtime;
        this.tvComId = tvComId;
        this.name = name;
        this.status = status;
        this.added = added;
        this.addedBy = addedBy;
        this.banner = banner;
        this.fanart = fanart;
        this.lastUpdated = lastUpdated;
        this.poster = poster;
        this.zap2itId = zap2itId;
    }

    protected Series(Parcel in) {
        id = in.readInt();
        actors = in.createStringArray();
        airsDayOfWeek = in.readString();
        airsTime = in.readString();
        contentRating = in.readString();
        long tmpFirstAired = in.readLong();
        firstAired = tmpFirstAired != NOT_PRESENT ? new Date(tmpFirstAired) : null;
        genres = in.createStringArray();
        imdbId = in.readString();
        language = in.readString();
        network = in.readString();
        networkId = in.readInt();
        overview = in.readString();
        rating = in.readFloat();
        ratingCount = in.readInt();
        runtime = in.readInt();
        tvComId = in.readInt();
        name = in.readString();
        status = in.readString();
        added = in.readString();
        addedBy = in.readString();
        banner = in.readString();
        fanart = in.readString();
        lastUpdated = in.readLong();
        poster = in.readString();
        zap2itId = in.readString();
    }

    @Override
    public String getImageUrl() {
        return banner;
    }

    @Override
    public String getTitleText() {
        return name;
    }

    @Override
    public String getDescText() {
        return overview;
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Series> CREATOR = new Parcelable.Creator<Series>() {
        public Series createFromParcel(Parcel in) {
            return new Series(in);
        }

        public Series[] newArray(int size) {
            return new Series[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeStringArray(actors);
        out.writeString(airsDayOfWeek);
        out.writeString(airsTime);
        out.writeString(contentRating);
        out.writeLong(firstAired != null ? firstAired.getTime() : -NOT_PRESENT);
        out.writeStringArray(genres);
        out.writeString(imdbId);
        out.writeString(language);
        out.writeString(network);
        out.writeInt(networkId);
        out.writeString(overview);
        out.writeFloat(rating);
        out.writeInt(ratingCount);
        out.writeInt(runtime);
        out.writeInt(tvComId);
        out.writeString(name);
        out.writeString(status);
        out.writeString(added);
        out.writeString(addedBy);
        out.writeString(banner);
        out.writeString(fanart);
        out.writeLong(lastUpdated);
        out.writeString(poster);
        out.writeString(zap2itId);
    }

    public static class Builder {
        private int id = NOT_PRESENT;
        private String[] actors = null;
        private String airsDayOfWeek = null;
        private String airsTime = null;
        private String contentRating = null;
        private Date firstAired = null;
        private String[] genres = null;
        private String imdbId = null;
        private String language = null;
        private String network = null;
        private int networkId = NOT_PRESENT;
        private String overview = null;
        private float rating = NOT_PRESENT;
        private int ratingCount = NOT_PRESENT;
        private int runTime = NOT_PRESENT;
        private int tvComId = NOT_PRESENT;
        private String name = null;
        private String status = null;
        private String added = null;
        private String addedBy = null;
        private String banner = null;
        private String fanart = null;
        private long lastUpdated = NOT_PRESENT;
        private String poster = null;
        private String zap2itId = null;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setActors(String[] actors) {
            this.actors = actors;
            return this;
        }

        public Builder setAirsDayOfWeek(String airsDayOfWeek) {
            this.airsDayOfWeek = airsDayOfWeek;
            return this;
        }

        public Builder setAirsTime(String airsTime) {
            this.airsTime = airsTime;
            return this;
        }

        public Builder setContentRating(String contentRating) {
            this.contentRating = contentRating;
            return this;
        }

        public Builder setFirstAired(Date firstAired) {
            this.firstAired = firstAired;
            return this;
        }

        public Builder setGenres(String[] genres) {
            this.genres = genres;
            return this;
        }

        public Builder setImdbId(String imdbId) {
            this.imdbId = imdbId;
            return this;
        }

        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder setNetwork(String network) {
            this.network = network;
            return this;
        }

        public Builder setNetworkId(int networkId) {
            this.networkId = networkId;
            return this;
        }

        public Builder setOverview(String overview) {
            this.overview = overview;
            return this;
        }

        public Builder setRating(float rating) {
            this.rating = rating;
            return this;
        }

        public Builder setRatingCount(int ratingCount) {
            this.ratingCount = ratingCount;
            return this;
        }

        public Builder setRuntime(int runTime) {
            this.runTime = runTime;
            return this;
        }

        public Builder setTvComId(int seriesId) {
            this.tvComId = seriesId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setAdded(String added) {
            this.added = added;
            return this;
        }

        public Builder setAddedBy(String addedBy) {
            this.addedBy = addedBy;
            return this;
        }

        public Builder setBanner(String banner) {
            this.banner = TvdbItem.BASE_IMAGE_URL + banner;
            return this;
        }

        public Builder setFanart(String fanart) {
            this.fanart = TvdbItem.BASE_IMAGE_URL + fanart;
            return this;
        }

        public Builder setLastUpdated(long lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        public Builder setPoster(String poster) {
            this.poster = TvdbItem.BASE_IMAGE_URL + poster;
            return this;
        }

        public Builder setZap2itId(String zap2itId) {
            this.zap2itId = zap2itId;
            return this;
        }

        public Series build() {
            return new Series(id, actors, airsDayOfWeek, airsTime, contentRating, firstAired,
                              genres, imdbId, language, network, networkId, overview, rating,
                              ratingCount, runTime, tvComId, name, status, added, addedBy, banner,
                              fanart, lastUpdated, poster, zap2itId);
        }
    }
}
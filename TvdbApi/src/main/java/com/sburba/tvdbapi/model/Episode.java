package com.sburba.tvdbapi.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.sburba.tvdbapi.xml.XmlException;
import com.sburba.tvdbapi.xml.XmlUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Date;

/**
 * TVDB Episode metadata
 * *Warning* Objects not present in the XML will be null, numbers not present will be NOT_PRESENT
 *
 * @see <a href="http://thetvdb.com/wiki/index.php/API:Base_Episode_Record">TVDB API Base Episode Record</a>
 */
public class Episode extends TvdbItem implements Parcelable {

    /**
     * TVDB Episode ID
     */
    public final int id;
    public final int dvdChapter;
    public final int dvdDiskId;
    public final int dvdEpisodeNumber;
    public final int dvdSeason;
    public final String[] directors;
    public final String name;
    public final int number;
    public final Date firstAired;
    public final String[] guestStars;
    public final String imdbId;
    public final String language;
    public final String overview;
    public final String productionCode;
    public final float rating;
    /**
     * Will be 0 if it is a Special
     */
    public final int seasonNumber;
    public final String[] writers;
    public final int absoluteNumber;
    /**
     * Only exists if episode is a Special
     */
    public final int airsAfterSeason;
    /**
     * Only exists if episode is a Special
     */
    public final int airsBeforeEpisode;
    /**
     * Only exists if episode is a Special
     */
    public final int airsBeforeSeason;
    public final String filename;
    /**
     * Update id for when the episode was last updated. It is not a time
     */
    public final long lastUpdated;
    public final int seasonId;
    /**
     * TVDB series id
     */
    public final int seriesId;

    private static final String TAG = "Episode";
    private static final boolean D = false;
    private static final String TAG_ID = "id";
    private static final String TAG_DVD_CHAPTER = "DVD_chapter";
    private static final String TAG_DVD_DISK_ID = "DVD_discid";
    private static final String TAG_DVD_EPISODE_NUMBER = "DVD_episodenumber";
    private static final String TAG_DVD_SEASON = "DVD_season";
    private static final String TAG_DIRECTOR = "Director";
    private static final String TAG_NAME = "EpisodeName";
    private static final String TAG_NUMBER = "EpisodeNumber";
    private static final String TAG_FIRST_AIRED = "FirstAired";
    private static final String TAG_GUEST_STARS = "GuestStars";
    private static final String TAG_IMDB_ID = "IMDB_ID";
    private static final String TAG_LANGUAGE = "Language";
    private static final String TAG_OVERVIEW = "Overview";
    private static final String TAG_PRODUCTION_CODE = "ProductionCode";
    private static final String TAG_RATING = "Rating";
    private static final String TAG_SEASON_NUMBER = "SeasonNumber";
    private static final String TAG_WRITER = "Writer";
    private static final String TAG_ABSOLUTE_NUMBER = "absolute_number";
    private static final String TAG_AIRS_AFTER_SEASON = "airsafter_season";
    private static final String TAG_AIRS_BEFORE_EPISODE = "airsbefore_episode";
    private static final String TAG_AIRS_BEFORE_SEASON = "airsbefore_season";
    private static final String TAG_FILENAME = "filename";
    private static final String TAG_LAST_UPDATED = "lastupdated";
    private static final String TAG_SEASON_ID = "seasonid";
    private static final String TAG_SERIES_ID = "seriesid";
    private static final String DELIMITER = "|";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static Episode fromXml(XmlPullParser parser) throws XmlPullParserException, IOException,
                                                               XmlException {
        Builder builder = new Builder();
        while (parser.nextTag() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) continue;

            String tag = parser.getName();
            if (tag.equals(TAG_ID)) {
                builder.setId(XmlUtil.readInt(parser, TAG_ID, NOT_PRESENT));
            } else if (tag.equals(TAG_DVD_CHAPTER)) {
                builder.setDvdChapter(XmlUtil.readInt(parser, TAG_DVD_CHAPTER, NOT_PRESENT));
            } else if (tag.equals(TAG_DVD_DISK_ID)) {
                builder.setDvdDiskId(XmlUtil.readInt(parser, TAG_DVD_DISK_ID, NOT_PRESENT));
            } else if (tag.equals(TAG_DVD_EPISODE_NUMBER)) {
                builder.setDvdEpisodeNumber(XmlUtil.readInt(parser, TAG_DVD_EPISODE_NUMBER, NOT_PRESENT));
            } else if (tag.equals(TAG_DVD_SEASON)) {
                builder.setDvdSeason(XmlUtil.readInt(parser, TAG_DVD_SEASON, NOT_PRESENT));
            } else if (tag.equals(TAG_DIRECTOR)) {
                builder.setDirectors(XmlUtil.readStringArray(parser, TAG_DIRECTOR, DELIMITER));
            } else if (tag.equals(TAG_NAME)) {
                builder.setName(XmlUtil.readText(parser, TAG_NAME));
            } else if (tag.equals(TAG_NUMBER)) {
                builder.setNumber(XmlUtil.readInt(parser, TAG_NUMBER, NOT_PRESENT));
            } else if (tag.equals(TAG_FIRST_AIRED)) {
                builder.setFirstAired(XmlUtil.readDate(parser, TAG_FIRST_AIRED, DATE_FORMAT));
            } else if (tag.equals(TAG_GUEST_STARS)) {
                builder.setGuestStars(XmlUtil.readStringArray(parser, TAG_GUEST_STARS, DELIMITER));
            } else if (tag.equals(TAG_IMDB_ID)) {
                builder.setImdbId(XmlUtil.readText(parser, TAG_IMDB_ID));
            } else if (tag.equals(TAG_LANGUAGE)) {
                builder.setLanguage(XmlUtil.readText(parser, TAG_LANGUAGE));
            } else if (tag.equals(TAG_OVERVIEW)) {
                builder.setOverview(XmlUtil.readText(parser, TAG_OVERVIEW));
            } else if (tag.equals(TAG_PRODUCTION_CODE)) {
                builder.setProductionCode(XmlUtil.readText(parser, TAG_PRODUCTION_CODE));
            } else if (tag.equals(TAG_RATING)) {
                builder.setRating(XmlUtil.readFloat(parser, TAG_RATING, NOT_PRESENT));
            } else if (tag.equals(TAG_SEASON_NUMBER)) {
                builder.setSeasonNumber(XmlUtil.readInt(parser, TAG_SEASON_NUMBER, NOT_PRESENT));
            } else if (tag.equals(TAG_WRITER)) {
                builder.setWriters(XmlUtil.readStringArray(parser, TAG_WRITER, DELIMITER));
            } else if (tag.equals(TAG_ABSOLUTE_NUMBER)) {
                builder.setAbsoluteNumber(XmlUtil.readInt(parser, TAG_ABSOLUTE_NUMBER, NOT_PRESENT));
            } else if (tag.equals(TAG_AIRS_AFTER_SEASON)) {
                builder.setAirsAfterSeason(XmlUtil.readInt(parser, TAG_AIRS_AFTER_SEASON, NOT_PRESENT));
            } else if (tag.equals(TAG_AIRS_BEFORE_EPISODE)) {
                builder.setAirsBeforeEpisode(XmlUtil.readInt(parser, TAG_AIRS_BEFORE_EPISODE, NOT_PRESENT));
            } else if (tag.equals(TAG_AIRS_BEFORE_SEASON)) {
                builder.setAirsBeforeSeason(XmlUtil.readInt(parser, TAG_AIRS_BEFORE_SEASON, NOT_PRESENT));
            } else if (tag.equals(TAG_FILENAME)) {
                builder.setFilename(XmlUtil.readText(parser, TAG_FILENAME));
            } else if (tag.equals(TAG_LAST_UPDATED)) {
                builder.setLastUpdated(XmlUtil.readLong(parser, TAG_LAST_UPDATED, NOT_PRESENT));
            } else if (tag.equals(TAG_SEASON_ID)) {
                builder.setSeasonId(XmlUtil.readInt(parser, TAG_SEASON_ID, NOT_PRESENT));
            } else if (tag.equals(TAG_SERIES_ID)) {
                builder.setSeriesId(XmlUtil.readInt(parser, TAG_SERIES_ID, NOT_PRESENT));
            } else {
                if (D) Log.w(TAG, "Skipping tag: " + tag);
                XmlUtil.skip(parser);
            }
        }
        return builder.build();
    }

    private Episode(int id, int dvdChapter, int dvdDiskId, int dvdEpisodeNumber, int dvdSeason,
                    String[] directors, String name, int number, Date firstAired,
                    String[] guestStars, String imdbId, String language, String overview,
                    String productionCode, float rating, int seasonNumber, String[] writers,
                    int absoluteNumber, int airsAfterSeason, int airsBeforeEpisode,
                    int airsBeforeSeason, String filename, long lastUpdated, int seasonId,
                    int seriesId) {
        this.id = id;
        this.dvdChapter = dvdChapter;
        this.dvdDiskId = dvdDiskId;
        this.dvdEpisodeNumber = dvdEpisodeNumber;
        this.dvdSeason = dvdSeason;
        this.directors = directors;
        this.name = name;
        this.number = number;
        this.firstAired = firstAired;
        this.guestStars = guestStars;
        this.imdbId = imdbId;
        this.language = language;
        this.overview = overview;
        this.productionCode = productionCode;
        this.rating = rating;
        this.seasonNumber = seasonNumber;
        this.writers = writers;
        this.absoluteNumber = absoluteNumber;
        this.airsAfterSeason = airsAfterSeason;
        this.airsBeforeEpisode = airsBeforeEpisode;
        this.airsBeforeSeason = airsBeforeSeason;
        this.filename = filename;
        this.lastUpdated = lastUpdated;
        this.seasonId = seasonId;
        this.seriesId = seriesId;
    }

    public Episode(Parcel in) {
        id = in.readInt();
        dvdChapter = in.readInt();
        dvdDiskId = in.readInt();
        dvdEpisodeNumber = in.readInt();
        dvdSeason = in.readInt();
        directors = in.createStringArray();
        name = in.readString();
        number = in.readInt();
        long tmpFirstAired = in.readLong();
        firstAired = tmpFirstAired != -1L ? new Date(tmpFirstAired) : null;
        guestStars = in.createStringArray();
        imdbId = in.readString();
        language = in.readString();
        overview = in.readString();
        productionCode = in.readString();
        rating = in.readFloat();
        seasonNumber = in.readInt();
        writers = in.createStringArray();
        absoluteNumber = in.readInt();
        airsAfterSeason = in.readInt();
        airsBeforeEpisode = in.readInt();
        airsBeforeSeason = in.readInt();
        filename = in.readString();
        lastUpdated = in.readLong();
        seasonId = in.readInt();
        seriesId = in.readInt();
    }

    @Override
    public String getImageUrl() {
        return filename;
    }

    @Override
    public String getTitleText() {
        return name;
    }

    @Override
    public String getDescText() {
        return overview;
    }

    /**
     * All episodes are special in my book
     *
     * @return {@link Boolean} indicating whether the Episode is a special
     */
    @SuppressWarnings("unused")
    public boolean isSpecial() {
        return seasonNumber == 0;
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Episode> CREATOR = new Parcelable.Creator<Episode>() {
        @Override
        public Episode createFromParcel(Parcel in) {
            return new Episode(in);
        }

        @Override
        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeInt(dvdChapter);
        out.writeInt(dvdDiskId);
        out.writeInt(dvdEpisodeNumber);
        out.writeInt(dvdSeason);
        out.writeStringArray(directors);
        out.writeString(name);
        out.writeInt(number);
        out.writeLong(firstAired != null ? firstAired.getTime() : -1L);
        out.writeStringArray(guestStars);
        out.writeString(imdbId);
        out.writeString(language);
        out.writeString(overview);
        out.writeString(productionCode);
        out.writeFloat(rating);
        out.writeInt(seasonNumber);
        out.writeStringArray(writers);
        out.writeInt(absoluteNumber);
        out.writeInt(airsAfterSeason);
        out.writeInt(airsBeforeEpisode);
        out.writeInt(airsBeforeSeason);
        out.writeString(filename);
        out.writeLong(lastUpdated);
        out.writeInt(seasonId);
        out.writeInt(seriesId);
    }

    public static class Builder {
        private int id = NOT_PRESENT;
        private int dvdChapter = NOT_PRESENT;
        private int dvdDiskId = NOT_PRESENT;
        private int dvdEpisodeNumber = NOT_PRESENT;
        private int dvdSeason = NOT_PRESENT;
        private String[] directors = new String[0];
        private String name = null;
        private int number = NOT_PRESENT;
        private Date firstAired = null;
        private String[] guestStars = new String[0];
        private String imdbId = null;
        private String language = null;
        private String overview = null;
        private String productionCode = null;
        private float rating = NOT_PRESENT;
        private int seasonNumber = NOT_PRESENT;
        private String[] writers = null;
        private int absoluteNumber = NOT_PRESENT;
        private int airsAfterSeason = NOT_PRESENT;
        private int airsBeforeEpisode = NOT_PRESENT;
        private int airsBeforeSeason = NOT_PRESENT;
        private String filename = null;
        private long lastUpdated = NOT_PRESENT;
        private int seasonId = NOT_PRESENT;
        private int seriesId = NOT_PRESENT;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setDvdChapter(int dvdChapter) {
            this.dvdChapter = dvdChapter;
            return this;
        }

        public Builder setDvdDiskId(int dvdDiskId) {
            this.dvdDiskId = dvdDiskId;
            return this;
        }

        public Builder setDvdEpisodeNumber(int dvdEpisodeNumber) {
            this.dvdEpisodeNumber = dvdEpisodeNumber;
            return this;
        }

        public Builder setDvdSeason(int dvdSeason) {
            this.dvdSeason = dvdSeason;
            return this;
        }

        public Builder setDirectors(String[] directors) {
            this.directors = directors;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setNumber(int number) {
            this.number = number;
            return this;
        }

        public Builder setFirstAired(Date firstAired) {
            this.firstAired = firstAired;
            return this;
        }

        public Builder setGuestStars(String[] guestStars) {
            this.guestStars = guestStars;
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

        public Builder setOverview(String overview) {
            this.overview = overview;
            return this;
        }

        public Builder setProductionCode(String productionCode) {
            this.productionCode = productionCode;
            return this;
        }

        public Builder setRating(float rating) {
            this.rating = rating;
            return this;
        }

        public Builder setSeasonNumber(int seasonNumber) {
            this.seasonNumber = seasonNumber;
            return this;
        }

        public Builder setWriters(String[] writers) {
            this.writers = writers;
            return this;
        }

        public Builder setAbsoluteNumber(int absoluteNumber) {
            this.absoluteNumber = absoluteNumber;
            return this;
        }

        public Builder setAirsAfterSeason(int airsAfterSeason) {
            this.airsAfterSeason = airsAfterSeason;
            return this;
        }

        public Builder setAirsBeforeEpisode(int airsBeforeEpisode) {
            this.airsBeforeEpisode = airsBeforeEpisode;
            return this;
        }

        public Builder setAirsBeforeSeason(int airsBeforeSeason) {
            this.airsBeforeSeason = airsBeforeSeason;
            return this;
        }

        public Builder setFilename(String filename) {
            this.filename = TvdbItem.BASE_IMAGE_URL + filename;
            return this;
        }

        public Builder setLastUpdated(long lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        public Builder setSeasonId(int seasonId) {
            this.seasonId = seasonId;
            return this;
        }

        public Builder setSeriesId(int seriesId) {
            this.seriesId = seriesId;
            return this;
        }

        public Episode build() {
            return new Episode(id, dvdChapter, dvdDiskId, dvdEpisodeNumber, dvdSeason, directors,
                               name,
                               number, firstAired, guestStars, imdbId, language, overview,
                               productionCode, rating, seasonNumber, writers, absoluteNumber,
                               airsAfterSeason, airsBeforeEpisode, airsBeforeSeason, filename,
                               lastUpdated, seasonId, seriesId);
        }
    }
}
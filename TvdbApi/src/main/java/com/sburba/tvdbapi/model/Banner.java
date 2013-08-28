package com.sburba.tvdbapi.model;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.sburba.tvdbapi.xml.XmlException;
import com.sburba.tvdbapi.xml.XmlUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * TVDB Banner metadata
 * *Warning* Objects not present in the XML will be null, numbers not present will be -1
 *
 * @see <a href="http://thetvdb.com/wiki/index.php/API:banners.xml">TVDB API banners.xml</a>
 */
public class Banner implements Parcelable {
    /**
     * TVDB Banner ID, only useful for uniquely identifying a banner
     */
    public final int id;
    /**
     * The path for the banner
     */
    public final String bannerPath;
    /**
     * The path for the thumbnail
     * Only exists if type is fanart
     */
    public final String thumbnailPath;
    /**
     * The path for the thumbnail
     * Only exists if type is fanart
     */
    public final String vignettePath;
    /**
     * Banner rating with four decimal place precision
     */
    public final float rating;
    /**
     * Number of times the banner has been rated
     */
    public final int ratingCount;
    /**
     * Indicates if the seriesname is included in the image or not
     */
    public final boolean hasSeriesName;
    /**
     * {@link Color} int that represents the light accent color the artist picked that goes well
     * with the image. Only available when type is fanart
     */
    public final int lightAccentColor;
    /**
     * {@link Color} int that represents the dark accent color the artist picked that goes well
     * with the image. Only available when type is fanart
     */
    public final int darkAccentColor;
    /**
     * {@link Color} int that represents the neutral midtone color the artist picked that goes well
     * with the image. Only available when type is fanart
     */
    public final int neutralMidtoneColor;
    /**
     * Banner type
     * Can be poster, fanart, series, or season
     */
    public final String type;
    /**
     * For series banners it can be <b>text</b>, <b>graphical</b>, or <b>blank</b>.
     * For season banners it can be <b>season</b> or <b>seasonwide</b>
     * For fanart in can be <b>1280x720</b> or <b>1920x1080</b>
     * For poster it will always be <b>680x1000</b>
     * <p/>
     * Blank Banners will leave the title and show logo off the banner.
     * Text Banners will show the series name as pain text in Arial font.
     * Graphical banners will show the series name in the shows official fot or will display the actual logo for the show.
     * Season banners are the standard DVD cover format while wide season banners will be the same dimensions as the series banners
     */
    public final String type2;
    /**
     * Some banners list the series name in a foreign language. This is the abbreviation of that language
     */
    public final String language;
    /**
     * If the banner is for a specific season, this will be the season number. Otherwise it will be -1
     */
    public final int seasonNumber;

    private static final String TAG = "Banner";
    private static final boolean D = false;

    private static final String TAG_ID = "id";
    private static final String TAG_BANNER_PATH = "BannerPath";
    private static final String TAG_THUMBNAIL_PATH = "ThumbnailPath";
    private static final String TAG_VIGNETTE_PATH = "VignettePath";
    private static final String TAG_BANNER_TYPE = "BannerType";
    private static final String TAG_BANNER_TYPE2 = "BannerType2";
    private static final String TAG_COLORS = "Colors";
    private static final String TAG_LANGUAGE = "Language";
    private static final String TAG_RATING = "Rating";
    private static final String TAG_RATING_COUNT = "RatingCount";
    private static final String TAG_HAS_SERIES_NAME = "SeriesName";
    private static final String TAG_SEASON = "Season";

    private static final String DELIMITER = "|";

    private static final int LIGHT_ACCENT_COLOR_POS = 0;
    private static final int DARK_ACCENT_COLOR_POS = 1;
    private static final int NEUTRAL_MIDTONE_COLOR_POS = 2;

    public static Banner fromXml(XmlPullParser parser)
            throws XmlPullParserException, IOException, XmlException {
        Builder builder = new Builder();
        while (parser.nextTag() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) continue;

            String tag = parser.getName();
            if (tag.equals(TAG_ID)) {
                builder.setId(XmlUtil.readInt(parser, TAG_ID, TvdbItem.NOT_PRESENT));
            } else if (tag.equals(TAG_BANNER_PATH)) {
                builder.setBannerPath(XmlUtil.readText(parser, TAG_BANNER_PATH));
            } else if (tag.equals(TAG_THUMBNAIL_PATH)) {
                builder.setThumbnailPath(XmlUtil.readText(parser, TAG_THUMBNAIL_PATH));
            } else if (tag.equals(TAG_VIGNETTE_PATH)) {
                builder.setVignettePath(XmlUtil.readText(parser, TAG_VIGNETTE_PATH));
            } else if (tag.equals(TAG_BANNER_TYPE)) {
                builder.setType(XmlUtil.readText(parser, TAG_BANNER_TYPE));
            } else if (tag.equals(TAG_BANNER_TYPE2)) {
                builder.setType2(XmlUtil.readText(parser, TAG_BANNER_TYPE2));
            } else if (tag.equals(TAG_COLORS)) {
                builder.setColors(XmlUtil.readStringArray(parser, TAG_COLORS, DELIMITER));
            } else if (tag.equals(TAG_LANGUAGE)) {
                builder.setLanguage(XmlUtil.readText(parser, TAG_LANGUAGE));
            } else if (tag.equals(TAG_RATING)) {
                builder.setRating(XmlUtil.readFloat(parser, TAG_RATING, TvdbItem.NOT_PRESENT));
            } else if (tag.equals(TAG_RATING_COUNT)) {
                builder.setRatingCount(XmlUtil.readInt(parser, TAG_RATING_COUNT, TvdbItem.NOT_PRESENT));
            } else if (tag.equals(TAG_HAS_SERIES_NAME)) {
                builder.setHasSeriesName(XmlUtil.readBool(parser, TAG_HAS_SERIES_NAME));
            } else if (tag.equals(TAG_SEASON)) {
                builder.setSeasonNumber(XmlUtil.readInt(parser, TAG_SEASON, TvdbItem.NOT_PRESENT));
            } else {
                if (D) Log.w(TAG, "Skipping tag: " + tag);
                XmlUtil.skip(parser);
            }
        }
        return builder.build();
    }

    protected Banner(int id, String bannerPath, String thumbnailPath, String vignettePath,
                     float rating, int ratingCount, boolean hasSeriesName, int lightAccentColor,
                     int darkAccentColor, int neutralMidtoneColor, String type,
                     String type2, String language, int seasonNumber) {
        this.id = id;
        this.bannerPath = bannerPath;
        this.thumbnailPath = thumbnailPath;
        this.vignettePath = vignettePath;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.hasSeriesName = hasSeriesName;
        this.lightAccentColor = lightAccentColor;
        this.darkAccentColor = darkAccentColor;
        this.neutralMidtoneColor = neutralMidtoneColor;
        this.type = type;
        this.type2 = type2;
        this.language = language;
        this.seasonNumber = seasonNumber;
    }

    private Banner(Parcel in) {
        id = in.readInt();
        bannerPath = in.readString();
        thumbnailPath = in.readString();
        vignettePath = in.readString();
        rating = in.readFloat();
        ratingCount = in.readInt();
        hasSeriesName = in.readByte() != 0;
        lightAccentColor = in.readInt();
        darkAccentColor = in.readInt();
        neutralMidtoneColor = in.readInt();
        type = in.readString();
        type2 = in.readString();
        language = in.readString();
        seasonNumber = in.readInt();
    }

    public static final Parcelable.Creator<Banner> CREATOR = new Parcelable.Creator<Banner>() {
        public Banner createFromParcel(Parcel in) {
            return new Banner(in);
        }

        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(bannerPath);
        out.writeString(thumbnailPath);
        out.writeString(vignettePath);
        out.writeFloat(rating);
        out.writeInt(ratingCount);
        out.writeByte((byte) ((hasSeriesName) ? 1 : 0));
        out.writeInt(lightAccentColor);
        out.writeInt(darkAccentColor);
        out.writeInt(neutralMidtoneColor);
        out.writeSerializable(type);
        out.writeSerializable(type2);
        out.writeString(language);
        out.writeInt(seasonNumber);
    }

    public static class Builder {
        private int id = TvdbItem.NOT_PRESENT;
        private String bannerPath = null;
        private String thumbnailPath = null;
        private String vignettePath = null;
        private float rating = TvdbItem.NOT_PRESENT;
        private int ratingCount = TvdbItem.NOT_PRESENT;
        private boolean hasSeriesName = false;
        private int lightAccentColor = TvdbItem.NOT_PRESENT;
        private int darkAccentColor = TvdbItem.NOT_PRESENT;
        private int neutralMidtoneColor = TvdbItem.NOT_PRESENT;
        private String type = null;
        private String type2 = null;
        private String language = null;
        private int season = TvdbItem.NOT_PRESENT;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setBannerPath(String bannerPath) {
            this.bannerPath = TvdbItem.BASE_IMAGE_URL + bannerPath;
            return this;
        }

        public Builder setThumbnailPath(String thumbnailPath) {
            this.thumbnailPath = TvdbItem.BASE_IMAGE_URL + thumbnailPath;
            return this;
        }

        public Builder setVignettePath(String vignettePath) {
            this.vignettePath = TvdbItem.BASE_IMAGE_URL + vignettePath;
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

        public Builder setHasSeriesName(boolean hasSeriesName) {
            this.hasSeriesName = hasSeriesName;
            return this;
        }

        public Builder setColors(String[] colorStrings) {
            if (colorStrings.length == 3) {
                setLightAccentColor(getRgbIntFromCSV(colorStrings[LIGHT_ACCENT_COLOR_POS]));
                setDarkAccentColor(getRgbIntFromCSV(colorStrings[DARK_ACCENT_COLOR_POS]));
                setNeutralMidtoneColor(getRgbIntFromCSV(colorStrings[NEUTRAL_MIDTONE_COLOR_POS]));
            }
            return this;
        }

        public Builder setLightAccentColor(int lightAccentColor) {
            this.lightAccentColor = lightAccentColor;
            return this;
        }

        public Builder setDarkAccentColor(int darkAccentColor) {
            this.darkAccentColor = darkAccentColor;
            return this;
        }

        public Builder setNeutralMidtoneColor(int neutralMidtoneColor) {
            this.neutralMidtoneColor = neutralMidtoneColor;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setType2(String type2) {
            this.type2 = type2;
            return this;
        }

        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder setSeasonNumber(int season) {
            this.season = season;
            return this;
        }

        public Banner build() {
            return new Banner(id, bannerPath, thumbnailPath, vignettePath, rating, ratingCount,
                              hasSeriesName, lightAccentColor, darkAccentColor, neutralMidtoneColor,
                              type, type2, language, season);
        }

        private int getRgbIntFromCSV(String csv) {
            String[] values = csv.split(",");
            if (values.length != 3) {
                return TvdbItem.NOT_PRESENT;
            } else {
                return Color.rgb(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
                                 Integer.parseInt(values[2]));
            }
        }
    }
}
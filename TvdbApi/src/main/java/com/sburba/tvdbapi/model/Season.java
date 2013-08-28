package com.sburba.tvdbapi.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.sburba.tvdbapi.xml.XmlException;
import com.sburba.tvdbapi.xml.XmlUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

/**
 * TVDB Season metadata
 * *Warning* Objects not present in the XML will be null, numbers not present will be -1
 */
public class Season extends TvdbItem implements Parcelable {
    public final int seriesId;
    public final int seasonNumber;
    public final int seasonId;
    public final Banner[] banners;
    public final int dvdSeason;
    public final String language;

    @Override
    public String getImageUrl() {
        for (Banner banner : banners) {
            if (banner.type2.equals("season")) {
                return banner.bannerPath;
            }
        }
        return null;
    }

    @Override
    public String getTitleText() {
        //TODO: Use resources to support different languages
        return (seasonNumber == 0) ? "Specials"
                                   : String.format((Locale) null, "S%02d", seasonNumber);
    }

    @Override
    public String getDescText() {
        //TODO: Use resources to support different languages
        return (seasonNumber == 0) ? "Specials"
                                   : String.format((Locale) null, "Season %02d", seasonNumber);
    }

    private static final String TAG_SERIES_ID = "seriesid";
    private static final String TAG_SEASON_NUMBER = "SeasonNumber";
    private static final String TAG_SEASON_ID = "seasonid";
    private static final String TAG_DVD_SEASON = "DVD_season";
    private static final String TAG_LANGUAGE = "Language";

    private Season(int seriesId, int seasonNumber, int seasonId, Banner[] banners, int dvdSeason,
                   String language) {
        this.seriesId = seriesId;
        this.seasonNumber = seasonNumber;
        this.seasonId = seasonId;
        this.banners = banners;
        this.dvdSeason = dvdSeason;
        this.language = language;
    }

    protected Season(Parcel in) {
        seriesId = in.readInt();
        seasonNumber = in.readInt();
        seasonId = in.readInt();
        banners = in.createTypedArray(Banner.CREATOR);
        dvdSeason = in.readInt();
        language = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(seriesId);
        dest.writeInt(seasonNumber);
        dest.writeInt(seasonId);
        dest.writeTypedArray(banners, flags);
        dest.writeInt(dvdSeason);
        dest.writeString(language);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Season> CREATOR = new Parcelable.Creator<Season>() {
        public Season createFromParcel(Parcel in) {
            return new Season(in);
        }

        public Season[] newArray(int size) {
            return new Season[size];
        }
    };

    public static class Builder {
        private int seriesId = NOT_PRESENT;
        private int seasonNumber = NOT_PRESENT;
        private int seasonId = NOT_PRESENT;
        private Collection<Banner> banners;
        private int dvdSeason = NOT_PRESENT;
        private String language;

        public Builder() {
            banners = new ArrayList<Banner>();
        }

        public static Builder fromEpisodeXml(XmlPullParser parser)
                throws XmlPullParserException, IOException, XmlException {
            Builder builder = new Builder();
            while (parser.nextTag() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) continue;

                String tag = parser.getName();
                if (tag.equals(TAG_SERIES_ID)) {
                    builder.setSeriesId(XmlUtil.readInt(parser, TAG_SERIES_ID, NOT_PRESENT));
                } else if (tag.equals(TAG_SEASON_NUMBER)) {
                    builder.setSeasonNumber(XmlUtil.readInt(parser, TAG_SEASON_NUMBER, NOT_PRESENT));
                } else if (tag.equals(TAG_SEASON_ID)) {
                    builder.setSeasonId(XmlUtil.readInt(parser, TAG_SEASON_ID, NOT_PRESENT));
                } else if (tag.equals(TAG_DVD_SEASON)) {
                    builder.setDvdSeason(XmlUtil.readInt(parser, TAG_DVD_SEASON, NOT_PRESENT));
                } else if (tag.equals(TAG_LANGUAGE)) {
                    builder.setLanguage(XmlUtil.readText(parser, TAG_LANGUAGE));
                } else {
                    XmlUtil.skip(parser);
                }
            }
            return builder;
        }

        public Builder setSeriesId(int seriesId) {
            this.seriesId = seriesId;
            return this;
        }

        public Builder setSeasonNumber(int seasonNumber) {
            this.seasonNumber = seasonNumber;
            return this;
        }

        public Builder setSeasonId(int seasonId) {
            this.seasonId = seasonId;
            return this;
        }

        public Builder addBanner(Banner banner) {
            banners.add(banner);
            return this;
        }

        public Builder setDvdSeason(int dvdSeason) {
            this.dvdSeason = dvdSeason;
            return this;
        }

        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public int getSeasonNumber() {
            return seasonNumber;
        }

        public Season build() {
            return new Season(seriesId, seasonNumber, seasonId,
                              banners.toArray(new Banner[banners.size()]), dvdSeason, language);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || Season.Builder.class != o.getClass()) return false;

            Builder builder = (Builder) o;

            return seasonNumber == builder.seasonNumber && seriesId == builder.seriesId;

        }

        @Override
        public int hashCode() {
            int result = seriesId;
            result = 31 * result + seasonNumber;
            return result;
        }
    }
}

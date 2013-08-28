package com.sburba.tvdbapi.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.sburba.tvdbapi.xml.XmlException;
import com.sburba.tvdbapi.xml.XmlUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Actor metadata for a Series
 * *Warning* Objects not present in the XML will be null, numbers not present will be -1
 *
 * @see <a href="http://thetvdb.com/wiki/index.php/API:actors.xml">TVDB API actors.xml</a>
 */
public class Actor extends TvdbItem implements Parcelable {

    /**
     * TVDB Actor ID
     */
    public final int id;
    /**
     * Actor Image URL
     */
    public final String image;
    /**
     * Actor Name
     */
    public final String name;
    /**
     * Actor's role in the Series
     */
    public final String role;
    /**
     * An int from 0-3. 1 being the most important actor on the show and 3 being the third most
     * important actor. 0 means they have no special sort order. Duplicates of 1-3 aren't supposed
     * to be allowed but currently are so the field isn't perfiect but can still be used for basic
     * sorting
     */
    public final int sortOrder;

    private static final String TAG = "Actor";
    private static final boolean D = false;

    private static final String TAG_ID = "id";
    private static final String TAG_IMAGE = "Image";
    private static final String TAG_NAME = "Name";
    private static final String TAG_ROLE = "Role";
    private static final String TAG_SORT_ORDER = "SortOrder";

    public static Actor fromXml(XmlPullParser parser) throws XmlPullParserException, IOException,
                                                             XmlException {
        Builder builder = new Builder();
        while (parser.nextTag() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) continue;
            String tag = parser.getName();

            if (tag.equals(TAG_ID)) {
                builder.setId(XmlUtil.readInt(parser, TAG_ID, NOT_PRESENT));
            } else if (tag.equals(TAG_IMAGE)) {
                builder.setImage(XmlUtil.readText(parser, TAG_IMAGE));
            } else if (tag.equals(TAG_NAME)) {
                builder.setName(XmlUtil.readText(parser, TAG_NAME));
            } else if (tag.equals(TAG_ROLE)) {
                builder.setRole(XmlUtil.readText(parser, TAG_ROLE));
            } else if (tag.equals(TAG_SORT_ORDER)) {
                builder.setSortOrder(XmlUtil.readInt(parser, TAG_SORT_ORDER, NOT_PRESENT));
            } else {
                if (D) Log.w(TAG, "Skipping tag: " + tag);
                XmlUtil.skip(parser);
            }
        }
        return builder.build();
    }

    protected Actor(int id, String image, String name, String role, int sortOrder) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.role = role;
        this.sortOrder = sortOrder;
    }

    protected Actor(Parcel in) {
        id = in.readInt();
        image = in.readString();
        name = in.readString();
        role = in.readString();
        sortOrder = in.readInt();
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Actor> CREATOR = new Parcelable.Creator<Actor>() {
        public Actor createFromParcel(Parcel in) {
            return new Actor(in);
        }

        public Actor[] newArray(int size) {
            return new Actor[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(image);
        dest.writeString(name);
        dest.writeString(role);
        dest.writeInt(sortOrder);
    }

    @Override
    public String getImageUrl() {
        return image;
    }

    @Override
    public String getTitleText() {
        return name;
    }

    @Override
    public String getDescText() {
        return role;
    }

    public static class Builder {
        private int id = NOT_PRESENT;
        private String image = null;
        private String name = null;
        private String role = null;
        private int sortOrder = NOT_PRESENT;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setImage(String image) {
            this.image = TvdbItem.BASE_IMAGE_URL + image;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setRole(String role) {
            this.role = role;
            return this;
        }

        public Builder setSortOrder(int sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        public Actor build() {
            return new Actor(id, image, name, role, sortOrder);
        }
    }
}

package com.sburba.tvdbapi.parser;

import com.sburba.tvdbapi.model.Banner;
import com.sburba.tvdbapi.xml.XmlException;
import com.sburba.tvdbapi.xml.XmlObjectListParser;
import com.sburba.tvdbapi.xml.XmlUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class BannerListParser implements XmlObjectListParser<Banner> {

    public static final int ALL_SEASONS = -1;
    private int mSeasonNumber;

    @SuppressWarnings("unused")
    public BannerListParser() {
        mSeasonNumber = ALL_SEASONS;
    }

    /**
     * Only return Banners that are for the seasonNumber
     *
     * @param seasonNumber season number to return banners for
     */
    public BannerListParser(int seasonNumber) {
        mSeasonNumber = seasonNumber;
    }

    @Override
    public Collection<Banner> parseListFromXmlString(String xml) throws XmlException {
        try {
            return readBannerList(XmlUtil.getXmlPullParser(xml));
        } catch (IOException e) {
            throw new XmlException("Error reading XML String", e);
        } catch (XmlPullParserException e) {
            throw new XmlException("Error parsing XML", e);
        }
    }

    @Override
    public Collection<Banner> parseListFromXmlStrings(Map<String, String> xmlStrings)
            throws XmlException {
        return parseListFromXmlString(xmlStrings.get("banners.xml"));
    }

    public Collection<Banner> readBannerList(XmlPullParser parser)
            throws IOException, XmlPullParserException, XmlException {

        List<Banner> bannerList = new ArrayList<Banner>();
        parser.require(XmlPullParser.START_TAG, null, "Banners");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) continue;
            if (parser.getName().equals("Banner")) {
                Banner banner = Banner.fromXml(parser);
                if (isValidBanner(banner)) bannerList.add(banner);
            } else {
                XmlUtil.skip(parser);
            }
        }
        return bannerList;
    }

    private boolean isValidBanner(Banner banner) {
        return banner != null &&
               (banner.seasonNumber == mSeasonNumber || mSeasonNumber == ALL_SEASONS);
    }
}

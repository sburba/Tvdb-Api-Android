package com.sburba.tvdbapi.xml;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class XmlUtil {

    private static final String TAG = "XmlUtil";
    private static final boolean D = false;

    public static final String NAMESPACE = null;

    /**
     * Reads an int from the next xml tag
     *
     * @param parser  The parser to read the int from
     * @param tagName the name of the tag that contains the int
     */
    public static int readInt(XmlPullParser parser, String tagName, int defaultValue)
            throws XmlPullParserException, IOException, XmlException {

        String text = readText(parser, tagName);
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            if(D) Log.w(TAG, "Error parsing int for tag " + tagName, e);
            return defaultValue;
        }
    }

    /**
     * Reads an long from the next xml tag
     *
     * @param parser  The parser to read the long from
     * @param tagName the name of the tag that contains the long
     */
    public static long readLong(XmlPullParser parser, String tagName, long defaultValue)
            throws XmlPullParserException, IOException, XmlException {

        String text = readText(parser, tagName);
        try {
            return Long.parseLong(text);
        } catch (NumberFormatException e) {
            if(D) Log.w(TAG, "Error parsing long for tag " + tagName, e);
            return defaultValue;
        }
    }

    /**
     * Reads boolean from the next xml tag
     *
     * @param parser  The parser to read the boolean from
     * @param tagName the name of the tag that contains the boolean
     */
    public static boolean readBool(XmlPullParser parser, String tagName)
            throws IOException, XmlPullParserException {
        return readText(parser, tagName).equals("true");
    }

    /**
     * Reads a float from the next xml tag
     *
     * @param parser  The parser to read the float from
     * @param tagName the name of the tag that contains the float
     */
    public static float readFloat(XmlPullParser parser, String tagName, float defaultValue)
            throws IOException, XmlPullParserException, XmlException {
        String text = readText(parser, tagName);
        float result;
        try {
            result = Float.parseFloat(text);
        } catch (NumberFormatException e) {
            if(D) Log.w(TAG, "Error parsing float for tag " + tagName, e);
            return defaultValue;
        }
        return result;
    }

    /**
     * Reads a String from the next xml tag
     *
     * @param parser  The parser to read the String from
     * @param tagName the name of the tag that contains the String
     */
    public static String readText(XmlPullParser parser, String tagName)
            throws XmlPullParserException, IOException {

        String result = "";
        parser.require(XmlPullParser.START_TAG, NAMESPACE, tagName);
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        parser.require(XmlPullParser.END_TAG, NAMESPACE, tagName);
        return result;
    }

    /**
     * Reads a String[] from the next xml tag
     *
     * @param parser    The parser to read the String from
     * @param tagName   the name of the tag that contains the String Array
     * @param delimiter Regular expression delimiter for the text to create the String Array
     * @throws IOException
     * @throws XmlPullParserException
     */
    public static String[] readStringArray(XmlPullParser parser, String tagName, String delimiter)
            throws IOException, XmlPullParserException {

        parser.require(XmlPullParser.START_TAG, NAMESPACE, tagName);
        String[] values = new String[0];
        if (parser.next() == XmlPullParser.TEXT) {
            String text = parser.getText();
            values = text.split(delimiter);
            parser.next();
        }
        parser.require(XmlPullParser.END_TAG, NAMESPACE, tagName);
        return values;
    }

    /**
     * Reads a date from the next xml tag
     *
     * @param parser     The parser to read the date from
     * @param tagName    the name of the tag that contains the date
     * @param dateFormat The date format see {@link SimpleDateFormat} for formatting options
     * @return {@link Date} or null on parseError
     * @throws IOException
     * @throws XmlPullParserException
     */
    public static Date readDate(XmlPullParser parser, String tagName, String dateFormat)
            throws IOException, XmlPullParserException {

        parser.require(XmlPullParser.START_TAG, NAMESPACE, tagName);
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        if (parser.next() == XmlPullParser.TEXT) {
            try {
                date = simpleDateFormat.parse(parser.getText());
            } catch (ParseException e) {
                return null;
            }
            parser.next();
        }
        parser.require(XmlPullParser.END_TAG, NAMESPACE, tagName);
        return date;
    }

    /**
     * Skips the next tag and all of its children
     */
    public static void skip(XmlPullParser parser)
            throws XmlPullParserException, IOException {

        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    /**
     * Get a default XmlPullParser
     */
    public static XmlPullParser getXmlPullParser(String xml)
            throws XmlPullParserException, IOException {
        XmlPullParserFactory xppFactory = XmlPullParserFactory.newInstance();
        xppFactory.setNamespaceAware(false);
        XmlPullParser parser = xppFactory.newPullParser();
        parser.setInput(new StringReader(xml));
        parser.nextTag();
        return parser;
    }
}
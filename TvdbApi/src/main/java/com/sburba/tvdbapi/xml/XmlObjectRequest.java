package com.sburba.tvdbapi.xml;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

/**
 * A request for retrieving a {@link T} XML response body at a given URL
 */
public class XmlObjectRequest<T, S extends XmlObjectParser<T>> extends XmlRequest<T> {
    protected final S mXmlParser;

    /**
     * Creates a new request.
     *
     * @param xmlParser     Parser to create {@link T} from XML at given URL
     * @param url           URL to fetch the XML from
     * @param listener      Listener to receive the XML response
     * @param errorListener Error listener, or null to ignore errors.
     */
    public XmlObjectRequest(S xmlParser, String url, Listener<T> listener,
                            ErrorListener errorListener) {
        super(Method.GET, url, null, listener, errorListener);
        mXmlParser = xmlParser;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String xml =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(parseXml(xml), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (XmlException e) {
            return Response.error(new ParseError(e));
        }
    }

    protected T parseXml(String xml) throws XmlException {
        return mXmlParser.parseXmlString(xml);
    }
}


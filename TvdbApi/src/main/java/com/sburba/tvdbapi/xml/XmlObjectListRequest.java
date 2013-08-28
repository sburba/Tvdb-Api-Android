package com.sburba.tvdbapi.xml;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

/**
 * A request for retrieving a {@link T} response body at a given URL, using
 * {@link S} to parse the XML
 */
public class XmlObjectListRequest<T, S extends XmlObjectListParser<T>>
        extends XmlRequest<Collection<T>> {
    protected final S mXmlParser;

    /**
     * Creates a new request.
     *
     * @param url           URL to fetch the XML from
     * @param listener      Listener to receive the XML response
     * @param errorListener Error listener, or null to ignore errors.
     */
    public XmlObjectListRequest(S xmlParser, String url, Response.Listener<Collection<T>> listener,
                                Response.ErrorListener errorListener) {
        super(Request.Method.GET, url, null, listener, errorListener);
        mXmlParser = xmlParser;
    }

    @Override
    protected Response<Collection<T>> parseNetworkResponse(NetworkResponse response) {
        try {
            String xml =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Collection<T> resultList = mXmlParser.parseListFromXmlString(xml);
            return Response.success(resultList, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (XmlException e) {
            return Response.error(new ParseError(e));
        }
    }
}

package com.sburba.tvdbapi.xml;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZippedXmlObjectListRequest<T, S extends XmlObjectListParser<T>>
        extends XmlObjectListRequest<T, S> {

    public ZippedXmlObjectListRequest(S xmlParser, String url, Listener<Collection<T>> listener,
                                      ErrorListener errorListener) {
        super(xmlParser, url, listener, errorListener);
    }

    @Override
    protected Response<Collection<T>> parseNetworkResponse(NetworkResponse response) {
        try {
            Map<String, String> xmlStrings = unpackZip(new ByteArrayInputStream(response.data));
            Collection<T> resultList = mXmlParser.parseListFromXmlStrings(xmlStrings);
            return Response.success(resultList, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (IOException e) {
            return Response.error(new ParseError(e));
        } catch (XmlException e) {
            return Response.error(new ParseError(e));
        }
    }

    private Map<String, String> unpackZip(InputStream dataStream) throws IOException {
        ZipInputStream zipStream = new ZipInputStream(dataStream);
        byte[] buffer = new byte[1024];
        ZipEntry ze;
        int count;
        Map<String, String> xmlStrings = new HashMap<String, String>();
        while ((ze = zipStream.getNextEntry()) != null) {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            while ((count = zipStream.read(buffer)) != -1) {
                byteStream.write(buffer, 0, count);
            }
            xmlStrings.put(ze.getName(), byteStream.toString(getCharset()));
        }

        return xmlStrings;
    }
}
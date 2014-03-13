package ru.electrictower.orf.http;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;

public class HttpCore
{

    private HttpClient   client   = new DefaultHttpClient();
    private HttpResponse response = null;
    private HttpContext  context  = new BasicHttpContext();
    private HttpPost     post     = new HttpPost();
    private HttpGet      get      = new HttpGet();
    private HttpPut      put      = new HttpPut();
    private HttpPatch    path     = new HttpPatch();
    private HttpDelete   delete   = new HttpDelete();

    public HttpCore()
    {
        super();
        client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        CookieStore cookieStore = new BasicCookieStore();
        context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

    }

    public Response executePostRequest(URI url, HttpEntity entity, Header... headers) throws IOException
    {
        try
        {
            post.setURI(url);
            makeRequestHeaders(post, headers);
            post.setEntity(entity);
            response = client.execute(post, context);
            return makeResponse(response);
        }
        finally
        {
            resetConnection(post);
        }
    }

    public Response executePostRequest(URI url, MultipartEntity entity, Header... headers) throws IOException
    {
        try
        {
            post.setURI(url);
            makeRequestHeaders(post, headers);
            post.setEntity(entity);
            response = client.execute(post, context);
            return makeResponse(response);
        }
        finally
        {
            resetConnection(post);
        }
    }

    public Response executeGetRequest(URI url, Header... headers) throws IOException
    {
        try
        {
            get.setURI(url);
            makeRequestHeaders(get, headers);
            response = client.execute(get, context);
            return makeResponse(response);
        }
        finally
        {
            resetConnection(get);
        }
    }

    public Response executePutRequest(URI url, HttpEntity entity, Header... headers) throws IOException
    {
        try
        {
            put.setURI(url);
            makeRequestHeaders(put, headers);
            put.setEntity(entity);
            response = client.execute(put, context);
            return makeResponse(response);
        }
        finally
        {
            resetConnection(put);
        }
    }

    public Response executeDeleteRequest(URI url, Header... headers) throws IOException
    {
        try
        {
            delete.setURI(url);
            makeRequestHeaders(delete, headers);
            response = client.execute(delete, context);
            return makeResponse(response);
        }
        finally
        {
            resetConnection(delete);
        }
    }

    public Response executePathRequest(URI url, HttpEntity entity, Header... headers) throws IOException
    {
        try
        {
            path.setURI(url);
            makeRequestHeaders(path, headers);
            path.setEntity(entity);
            response = client.execute(path, context);
            return makeResponse(response);
        }
        finally
        {
            resetConnection(path);
        }
    }

    public HttpContext getContext()
    {
        return context;
    }

    public void setContext(HttpContext context) throws UnsupportedEncodingException
    {
        this.context = context;
    }

    private void resetConnection(HttpRequestBase method)
    {
        try
        {
            method.releaseConnection();
            clearHeaders(method);
            client.getConnectionManager().closeExpiredConnections();
            if (response != null)
            {
                EntityUtils.consume(response.getEntity());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void makeRequestHeaders(HttpRequestBase method, Header... headers)
    {
        if (isHeaders(headers))
        {
            method.setHeaders(headers);
        }
    }

    private boolean isHeaders(Header... headers)
    {
        if (headers == null || headers.length == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private void clearHeaders(HttpRequestBase method)
    {
        HeaderIterator iterator = method.headerIterator();
        while (iterator.hasNext())
        {
            iterator.next();
            iterator.remove();
        }
    }

    private Response makeResponse(HttpResponse response) throws IOException
    {
        HttpEntity entity = response.getEntity();
        Response responseObj = null;
        if (entity != null)
        {
            responseObj = new Response(response.getStatusLine().getStatusCode(), EntityUtils.toString(entity));
        }
        else
        {
            responseObj = new Response(response.getStatusLine().getStatusCode(), "NO ENTITY!!!");
        }
        return responseObj;
    }
}

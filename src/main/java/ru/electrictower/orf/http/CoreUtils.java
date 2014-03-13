package ru.electrictower.orf.http;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import ru.electrictower.orf.http.exceptions.EmulatorException;
import ru.electrictower.orf.http.exceptions.ResponseCodeException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

public class CoreUtils
{

    protected final Gson gson = new Gson();

    public URI makeURI(String url, Object... urlParts) throws URISyntaxException
    {
        if (urlParts != null || urlParts.length != 0)
        {
            url = String.format(url, urlParts);
        }
        return new URI(url);
    }


    public HttpEntity makeJsonEntity(Object json) throws UnsupportedEncodingException
    {
        return new StringEntity(makeJson(json));
    }

    private String makeJson(Object jsonBean)
    {
        return gson.toJson(jsonBean);
    }

    public HttpEntity makeParamsEntity(List<NameValuePair> params)
    {
        return new UrlEncodedFormEntity(params, Charset.forName("UTF-8"));
    }

    public <T> T makeJsonObjFromResponse(Response response, Type objClass)
    {
        return gson.fromJson(response.getResponse(),
                             objClass);
    }

    public void checkStatus(Response response, int statusCode, String errorMessage)
    {
        if (response.getStatusCode() != statusCode)
        {
            throw new ResponseCodeException(errorMessage + " [Status code:" + response.getStatusCode() + "]");
        }
    }

    public void processException(Exception e) throws EmulatorException
    {
        e.printStackTrace();
        throw new EmulatorException();
    }

    public String mutateNodeRefToUrl(String nodeRef)
    {
        return nodeRef.replace("://", "/");
    }
}

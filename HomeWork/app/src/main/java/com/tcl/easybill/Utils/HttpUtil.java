package com.tcl.easybill.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    public static HttpClient httpClient = new DefaultHttpClient();
    public static final String BASE_URL="";

    public static String getRequest(String url) throws Exception{

        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        if (httpResponse.getStatusLine().getStatusCode() == 200){
            String result = EntityUtils.toString(httpResponse.getEntity());
            return result;
        }

        return null;
    }
    public static String postRequest(String url , Map<String,String> rawParams)throws Exception{
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (String key : rawParams.keySet()){
            params.add(new BasicNameValuePair(key,rawParams.get(key)));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(params,"gbk"));
        HttpResponse httpResponse = httpClient.execute(httpPost);
        if (httpResponse.getStatusLine().getStatusCode() == 200){
            String result = EntityUtils.toString(httpResponse.getEntity());
            return result;
        }
        return null;
    }


}


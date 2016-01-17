package com.gg.whattv.utils;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * author cipherGG
 * Created by Administrator on 2015/12/25.
 * describe
 */
public class HttpUtils {
    public static final String TV_KEY = "a80527afca773604cc8f85d8c38042fb";
    public static final String TV_CATEGORY_PATH = "http://japi.juhe.cn/tv/getCategory";
    public static final String TV_CHANNEL_PATH = "http://japi.juhe.cn/tv/getChannel";
    public static final String TV_PROGRAM_PATH = "http://japi.juhe.cn/tv/getProgram";

    public static final String NBA_KEY = "00306314ee814600a58b2314abcc6ee5";
    public static final String NBA_NBA_PATH = "http://op.juhe.cn/onebox/basketball/nba";
    public static final String NBA_TEAM_PATH = "http://op.juhe.cn/onebox/basketball/team";
    public static final String NBA_COMBAT_PATH = "http://op.juhe.cn/onebox/basketball/combat";

    public static final String FOOT_KEY = "0c8e419287d175714ce5a802a547b82e";
    public static final String FOOT_LEAGUE_PATH = "http://op.juhe.cn/onebox/football/league";
    public static final String FOOT_TEAM_PATH = "http://op.juhe.cn/onebox/football/team";
    public static final String FOOT_COMBAT_PATH = "http://op.juhe.cn/onebox/football/combat";

    public static final String MOVIE_KEY = "7fa79c072aaeb672bd8b13da7ccb3925";
    public static final String MOVIE_LOCAL_PATH = "http://op.juhe.cn/onebox/movie/pmovie";
    public static final String MOVIE_VIDEO_PATH = "http://op.juhe.cn/onebox/movie/video";

    public static ResponseBody getBody(String url, HashMap<String, Object> params) {
        OkHttpClient client = new OkHttpClient();

        try {
            StringBuilder buffer = new StringBuilder();
            //GET里记得加上？
            buffer.append("?");

            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    buffer.append(entry.getKey())
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue().toString(), "utf-8"))
                            .append("&");
                }
                //删去最后一个符号&
                buffer.deleteCharAt(buffer.length() - 1);
            }

            Request request = new Request.Builder()
                    .url(url + buffer.toString())
                    .build();

            Response response = client.newCall(request).execute();

            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getString(String url, HashMap<String, Object> params) {
        try {
            ResponseBody responseBody = getBody(url, params);

            //看清楚是string，不是toString！！！！！！！！！！！！！！
            return responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream getStream(String url, HashMap<String, Object> params) {
        try {
            ResponseBody responseBody = getBody(url, params);

            return responseBody.byteStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] getBytes(String url, HashMap<String, Object> params) {
        try {
            ResponseBody responseBody = getBody(url, params);

            return responseBody.bytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResponseBody postBody(String url, String json) {
        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();

            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}

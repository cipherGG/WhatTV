package com.gg.whattv;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;
import android.widget.Toast;

import com.gg.whattv.engine.Movie;
import com.gg.whattv.utils.HttpUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;

/**
 * author cipherGG
 * Created by Administrator on 2015/12/25.
 * describe
 */
public class HttpTest extends ApplicationTestCase<Application> {
    public HttpTest() {
        super(Application.class);
    }

    public void testHttp() {
        //String body = HttpUtils.getBody("www.baidu.com", null);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://japi.juhe.cn/tv/getChannel?pId=1&key=a80527afca773604cc8f85d8c38042fb")
                .build();

        try {
            Response response = client.newCall(request).execute();

            Log.e("body", response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testMovie(){
        Movie.getMovieLocal("北京");
    }

}

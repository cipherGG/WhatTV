package com.gg.whattv.engine;

import com.gg.whattv.bean.MovieLocal;
import com.gg.whattv.bean.MovieVideo;
import com.gg.whattv.utils.HttpUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * author cipherGG
 * Created by Administrator on 2015/12/26.
 * describe
 */
public class Movie {

    public static List<MovieLocal.Result.A> getMovie(String city) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("key", HttpUtils.MOVIE_KEY);
        map.put("city", city);

        String data = HttpUtils.getString(HttpUtils.MOVIE_LOCAL_PATH, map);

        Gson gson = new Gson();

        MovieLocal local = gson.fromJson(data, MovieLocal.class);

        if (local == null || local.result == null || local.result.data == null) {
            return new ArrayList<>();

        } else {
            return local.result.data;
        }
    }

    public static List<MovieLocal.Result.A.AA> getMoving(String city) {
        List<MovieLocal.Result.A> local = getMovie(city);

        if (local.size() == 0 || local.get(0).data == null) {
            return new ArrayList<>();

        } else {
            return local.get(0).data;
        }
    }

    public static List<MovieLocal.Result.A.AA> getWillMovie(String city) {
        List<MovieLocal.Result.A> local = getMovie(city);

        if (local.size() == 0 || local.get(1) == null || local.get(1).data == null) {
            return new ArrayList<>();

        } else {
            return local.get(1).data;
        }
    }

    public static List<MovieLocal.Result.A.AA> getMovieLocal(String city) {
        List<MovieLocal.Result.A.AA> movies = new ArrayList<>();

        List<MovieLocal.Result.A.AA> moving = getMoving(city);
        List<MovieLocal.Result.A.AA> movieWill = getWillMovie(city);

        movies.addAll(moving);
        movies.addAll(movieWill);

        return movies;
    }

    public static MovieVideo.Result getVideoDetail(String name) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("key", HttpUtils.MOVIE_KEY);
        map.put("q", name);

        String data = HttpUtils.getString(HttpUtils.MOVIE_VIDEO_PATH, map);

        Gson gson = new Gson();

        MovieVideo video = gson.fromJson(data, MovieVideo.class);

        return video.result;
    }

}

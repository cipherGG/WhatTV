package com.gg.whattv.engine;

import com.gg.whattv.bean.TVCategory;
import com.gg.whattv.bean.TVChannel;
import com.gg.whattv.utils.HttpUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * author cipherGG
 * Created by Administrator on 2015/12/25.
 * describe
 */
public class TV {

    public static List<TVCategory.Channel> getTVChannels(int pId) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("key", HttpUtils.TV_KEY);
        map.put("pId", pId);

        String data = HttpUtils.getString(HttpUtils.TV_CHANNEL_PATH, map);

        Gson gson = new Gson();

        TVCategory tvCategory = gson.fromJson(data, TVCategory.class);

        if (tvCategory == null || tvCategory.result == null) {
            return new ArrayList<>();

        } else {
            return tvCategory.result;
        }
    }

    public static List<TVChannel.Program> getTVProgram(String code, String date) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("key", HttpUtils.TV_KEY);
        map.put("code", code);
        map.put("date", date);

        String data = HttpUtils.getString(HttpUtils.TV_PROGRAM_PATH, map);

        Gson gson = new Gson();

        TVChannel tvChannel = gson.fromJson(data, TVChannel.class);

        if (tvChannel == null || tvChannel.result == null) {
            return new ArrayList<>();

        } else {
            return tvChannel.result;
        }
    }

}

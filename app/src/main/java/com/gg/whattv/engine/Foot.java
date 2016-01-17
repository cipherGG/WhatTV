package com.gg.whattv.engine;

import com.gg.whattv.bean.FootLeague;
import com.gg.whattv.bean.FootTeam;
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
public class Foot {

    public static FootLeague getFootLeague(String leagueName) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("key", HttpUtils.FOOT_KEY);
        map.put("league", leagueName);

        String data = HttpUtils.getString(HttpUtils.FOOT_LEAGUE_PATH, map);

        Gson gson = new Gson();

        return gson.fromJson(data, FootLeague.class);
    }

    public static List<FootLeague.Result.Views.A> getFootSaiCheng(String leagueName) {
        FootLeague league = getFootLeague(leagueName);

        List<FootLeague.Result.Views.A> saicheng1 = league.result.views.saicheng1;
        List<FootLeague.Result.Views.A> saicheng2 = league.result.views.saicheng2;
        List<FootLeague.Result.Views.A> saicheng3 = league.result.views.saicheng3;

        List<FootLeague.Result.Views.A> saiCheng = new ArrayList<>();

        if (saicheng1 != null) {
            saiCheng.addAll(saicheng1);

        }
        if (saicheng2 != null) {
            saiCheng.addAll(saicheng2);

        }
        if (saicheng3 != null) {
            saiCheng.addAll(saicheng3);

        }

        return saiCheng;
    }

    public static List<FootTeam.Result.A> getFootTeam(String teamName) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("key", HttpUtils.FOOT_KEY);
        map.put("team", teamName);

        String data = HttpUtils.getString(HttpUtils.FOOT_TEAM_PATH, map);

        Gson gson = new Gson();

        FootTeam team = gson.fromJson(data, FootTeam.class);

        if (team == null || team.result == null || team.result.list == null) {
            return new ArrayList<>();
        } else {
            return team.result.list;
        }
    }

}

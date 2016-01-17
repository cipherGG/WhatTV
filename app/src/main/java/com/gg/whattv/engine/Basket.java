package com.gg.whattv.engine;

import com.gg.whattv.bean.BasketNBA;
import com.gg.whattv.bean.BasketTeam;
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
public class Basket {

    public static final int NOCOMBAT = 0;
    public static final int OVERCOMBAT = 2;

    public static List<BasketNBA.Result.A> getNBA() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("key", HttpUtils.NBA_KEY);

        String data = HttpUtils.getString(HttpUtils.NBA_NBA_PATH, map);

        Gson gson = new Gson();

        BasketNBA nba = gson.fromJson(data, BasketNBA.class);

        return nba.result.list;
    }

    public static List<BasketNBA.Result.A.AA> getCombat(int flag) {
        List<BasketNBA.Result.A.AA> aas = new ArrayList<>();

        List<BasketNBA.Result.A> nba = getNBA();

        for (BasketNBA.Result.A a : nba) {

            List<BasketNBA.Result.A.AA> tr = a.tr;

            for (BasketNBA.Result.A.AA aa : tr) {

                switch (flag) {
                    //未开赛
                    case 0:
                        if (aa.status.equals(NOCOMBAT + "")) {

                            aas.add(aa);
                        }
                        break;
                    //已完结
                    case 1:
                        if (aa.status.equals(OVERCOMBAT + "")) {

                            aas.add(aa);
                        }
                        break;
                }
            }
        }
        return aas;
    }

    public static List<BasketTeam.Result.A> getTeam(String teamName) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("key", HttpUtils.NBA_KEY);
        map.put("team", teamName);

        String data = HttpUtils.getString(HttpUtils.NBA_TEAM_PATH, map);

        Gson gson = new Gson();

        BasketTeam team = gson.fromJson(data, BasketTeam.class);

        if (team == null || team.result == null || team.result.list == null) {
            return new ArrayList<>();

        } else {
            return team.result.list;
        }
    }

}

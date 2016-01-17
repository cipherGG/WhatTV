package com.gg.whattv.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * author cipherGG
 * Created by Administrator on 2015/12/25.
 * describe
 */
public class TabsUtils {

    public static List<String> getTVTabs() {
        List<String> tabs = new ArrayList<>();
        tabs.add("央视");
        tabs.add("卫视");
        tabs.add("数字");
        tabs.add("城市");
        tabs.add("CETV");
        tabs.add("原创");

        return tabs;
    }

    public static List<String> getNBATabs() {
        List<String> tabs = new ArrayList<>();
        tabs.add("未开赛");
        tabs.add("已结束");
        // tabs.add("骑士");
        tabs.add("勇士");
        // tabs.add("老鹰");
        tabs.add("马刺");
        // tabs.add("猛龙");
        tabs.add("雷霆");
        tabs.add("热火");
        tabs.add("小牛");
        // tabs.add("魔术");
        tabs.add("快船");
        tabs.add("公牛");
        tabs.add("灰熊");
        tabs.add("步行者");
        tabs.add("火箭");
        tabs.add("活塞");
        // tabs.add("爵士");
        tabs.add("凯尔特人");
        tabs.add("掘金");
        tabs.add("黄蜂");
        tabs.add("国王");
        tabs.add("奇才");
        // tabs.add("太阳");
        tabs.add("尼克斯");
        tabs.add("森林狼");
        tabs.add("雄鹿");
        // tabs.add("开拓者");
        tabs.add("篮网");
        tabs.add("鹈鹕");
        tabs.add("76人");
        tabs.add("湖人");

        return tabs;
    }

    public static List<String> getFootTabs() {
        List<String> tabs = new ArrayList<>();
        tabs.add("中超");
        tabs.add("欧冠");
        tabs.add("意甲");
        tabs.add("英超");
        tabs.add("西甲");
        tabs.add("德甲");
        tabs.add("法甲");

        return tabs;
    }

    public static List<String> getMovieTabs() {

        List<String> tabs = new ArrayList<>();
        tabs.add("搜索");
        tabs.add("本地");
        return tabs;
    }
}

package com.gg.whattv.bean;

import java.util.List;

/**
 * author cipherGG
 * Created by Administrator on 2015/12/26.
 * describe
 */
public class MovieVideo {
    public String reason;
    public Result result;
    public String error_code;

    public class Result {
        public String title;
        public String tag;
        public String act;
        public String year;
        public String rating;
        public String area;
        public String dir;
        public String desc;
        public String cover;
        public String vdo_status;
        //public Playlinks playlinks;
        public List<B> video_rec;
        public List<C> act_s;

//        public class Playlinks {
//            public String fengxing;
//            public String qiyi;
//            public String taomi;
//            public String youku;
//        }

        public class B {
            public String cover;
            public String detail_url;
            public String title;
        }

        public class C {
            public String name;
            public String url;
            public String image;
        }
    }
}

package com.gg.whattv.bean;

import java.util.List;

/**
 * author cipherGG
 * Created by Administrator on 2015/12/26.
 * describe
 */
public class FootLeague {
    public String reason;
    public Result result;
    public String error_code;

    public class Result {
        public String key;
        public Tabs tabs;
        public Views views;

        public class Tabs {
            public String saicheng1;
            public String saicheng2;
            public String saicheng3;
            public String jifenbang;
            public String sheshoubang;
        }

        public class Views {
            public List<A> saicheng1;
            public List<A> saicheng2;
            public List<A> saicheng3;
            public List<B> jifenbang;
            public List<C> sheshoubang;

            public class A {
                public String c1;
                public String c2;
                public String c3;
                public String c4R;
                public String c4T1;
                public String c4T1URL;
                public String c4T2;
                public String c4T2URL;
                public String c51;
                public String c51Link;
                public String c52;
                public String c52Link;
            }

            public class B {
                public String c1;
                public String c2;
                public String c2L;
                public String c3;
                public String c41;
                public String c42;
                public String c43;
                public String c5;
                public String c6;
            }

            public class C {
                public String c1;
                public String c2;
                public String c2L;
                public String c3;
                public String c3L;
                public String c4;
                public String c5;
            }
        }
    }
}

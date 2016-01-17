package com.gg.whattv.bean;

import java.util.List;

/**
 * author cipherGG
 * Created by Administrator on 2015/12/25.
 * describe
 */
public class BasketNBA {

    public String reason;
    public Result result;
    public String error_code;

    public class Result {
        public String title;
        public Statuslist statuslist;
        public List<A> list;
        public List<B> teammatch;

        public class Statuslist {
            public String st0;
            public String st1;
            public String st2;
        }

        public class A {
            public String title;
            public List<AA> tr;
            public List<AC> live;
            public List<AD> livelink;
            public List<AB> bottomlink;

            public class AA {
                public String link1text;
                public String link1url;
                public String link2text;
                public String link2url;
                public String m_link1url;
                public String m_link2url;
                public String player1;
                public String player1logo;
                public String player1logobig;
                public String player1url;
                public String player2;
                public String player2logo;
                public String player2logobig;
                public String player2url;
                public String score;
                public String status;
                public String time;
            }

            public class AB {
                public String text;
                public String url;
            }

            public class AC {
                public String player1;
                public String player1info;
                public String player1location;
                public String player1logobig;
                public String player1url;
                public String player2;
                public String player2info;
                public String player2location;
                public String player2logobig;
                public String player2url;
                public String score;
                public String status;
                public String title;
                public String liveurl;
            }

            public class AD {
                public String text;
                public String url;
            }
        }

        public class B {
            public String name;
            public String url;

        }
    }
}

package com.gg.whattv.bean;

import java.util.List;

/**
 * author cipherGG
 * Created by Administrator on 2015/12/25.
 * describe
 */
public class BasketTeam {
    public String reason;
    public Result result;

    public class Result {
        public String title;
        public List<A> list;
        public More1 more1;
        public More2 more2;
        public String error_code;

        public class A {
            public String link1text;
            public String link1url;
            public String link2text;
            public String link2url;
            public String m_link1url;
            public String m_link2url;
            public String m_player1url;
            public String m_player2url;
            public String m_time;
            public String player1;
            public String player1logo;
            public String player1url;
            public String player2;
            public String player2logo;
            public String player2url;
            public String score;
            public String status;
            public String time;
        }

        public class More1 {
            public String link;
            public String text;
        }

        private class More2 {
            public String link;
            public String text;
        }
    }
}

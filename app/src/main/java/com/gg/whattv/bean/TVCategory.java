package com.gg.whattv.bean;

import java.util.List;

/**
 * author cipherGG
 * Created by Administrator on 2015/12/25.
 * describe
 */
public class TVCategory {
    public int error_code;
    public String reason;
    public List<Channel> result;

    public class Channel {
        public String channelName;
        public int pId;
        public String rel;
        public String url;
    }
}

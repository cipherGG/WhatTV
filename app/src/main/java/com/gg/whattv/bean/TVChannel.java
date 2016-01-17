package com.gg.whattv.bean;

import java.util.List;

/**
 * author cipherGG
 * Created by Administrator on 2015/12/25.
 * describe
 */
public class TVChannel {
    public int error_code;
    public String reason;
    public List<Program> result;

    public class Program {
        public String cName;
        public String pName;
        public String pUrl;
        public String time;
    }
}

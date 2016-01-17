package com.gg.whattv.bean;

import java.util.List;

/**
 * author cipherGG
 * Created by Administrator on 2015/12/26.
 * describe
 */
public class MovieLocal {
    public String reason;
    public Result result;
    public String error_code;

    public class Result {
        public String reason;
        public String title;
        public String url;
        public String m_url;
        public List<A> data;
        public String morelink;
        public String date;

        public class A {
            public String link;
            public String name;
            public List<AA> data;

            public class AA {
                public Director director;
                public String grade;
                public String gradeNum;
                public String iconaddress;
                public String iconlinkUrl;
                public String m_iconlinkUrl;
                public More more;
                public Star star;
                public PlayDate playDate;
                public Story story;
                public String subHead;
                public String tvTitle;
                public Type type;

                public class Director {
                    public String showname;
                    public Data data;

                    public class Data {
                        public One one;//////////////////////////////////////////////11111
                        public M_1 m_1;

                        public class One {
                            public String link;
                            public String name;

                        }

                        public class M_1 {
                            public String link;
                        }
                    }
                }

                public class More {
                    public List<AAA> data;
                    public String showname;

                    public class AAA {
                        public String name;
                        public String link;
                    }
                }

                public class Star {
                    public Data data;
                    public String showname;

                    public class Data {
                        public M_1 m_1;
                        public M_2 m_2;
                        public M_3 m_3;
                        public M_4 m_4;
                        public One one;
                        public Two two;
                        public Three three;
                        public Four four;

                        public class M_1 {
                            public String link;
                        }

                        public class M_2 {
                            public String link;
                        }

                        public class M_3 {
                            public String link;
                        }

                        public class M_4 {
                            public String link;
                        }

                        public class One {
                            public String link;
                            public String name;
                        }

                        public class Two {
                            public String link;
                            public String name;
                        }

                        public class Three {
                            public String link;
                            public String name;
                        }

                        public class Four {
                            public String link;
                            public String name;
                        }
                    }
                }

                public class PlayDate {
                    public String showname;
                    public String data;
                    public String data2;
                }

                public class Story {
                    public String showname;
                    public Data data;

                    public class Data {
                        public String storyBrief;
                        public String storyMoreLink;
                    }
                }

                public class Type {
                    public String showname;
                    public Data data;

                    public class Data {
                        public One one;
                        public Two two;

                        public class One {
                            public String link;
                            public String name;
                        }

                        public class Two {
                            public String link;
                            public String name;
                        }
                    }
                }
            }
        }
    }
}

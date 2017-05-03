package lixiaoqian.bwei.com.haonanshou;

import java.io.Serializable;
import java.util.List;

/**
 * @类的用途：
 * @author: 李晓倩
 * @date: 2017/4/22
 */

public class GsonBean implements Serializable{

    /**
     * datalist : [{"cid":"5857","course_tname":"丁丁","course_name":"3DMAX效果图-实战篇（欧式二）","course_price":"20.00","course_pic":"http://img.dianfu.net/img/20170216/40799f98cfc235f2a2eb46837d5cff84.jpg","course_paycount":"0","school_name":"吉大教育"},{"cid":"1167","course_tname":"中公教育","course_name":"行测数量关系核心考点之行程问题1","course_price":"0.00","course_pic":"http://img.dianfu.net/img/20150914/1d132d22df1e8b5331ff960ff96f0158.jpg","course_paycount":"30","school_name":"中公教育"},{"cid":"1170","course_tname":"中公教育","course_name":"行测数量关系核心考点之行程问题2","course_price":"0.00","course_pic":"http://img.dianfu.net/img/20150914/92048cc02319aba296cdadf9140c6602.jpg","course_paycount":"41","school_name":"中公教育"},{"cid":"1173","course_tname":"中公教育","course_name":"行测数量关系核心考点之行程问题3","course_price":"0.00","course_pic":"http://img.dianfu.net/img/20150914/aaed07356bea4bc4e1a24b6164297f5d.jpg","course_paycount":"24","school_name":"中公教育"},{"cid":"1161","course_tname":"中公教育","course_name":"行测数量关系 核心考点之不定方程1","course_price":"0.00","course_pic":"http://img.dianfu.net/img/20150911/3e8af2128c1ead7f86e45797ae13f91a.jpg","course_paycount":"17","school_name":"中公教育"},{"cid":"1162","course_tname":"中公教育","course_name":"行测数量关系 核心考点之不定方程2","course_price":"0.00","course_pic":"http://img.dianfu.net/img/20150911/1623e1ea09471f3a7eef7c10d6fa6979.jpg","course_paycount":"33","school_name":"中公教育"},{"cid":"1163","course_tname":"中公教育","course_name":"行测数量关系 核心考点之不定方程3","course_price":"0.00","course_pic":"http://img.dianfu.net/img/20150911/7c86ea4816766dc04f491d5066a8a67c.jpg","course_paycount":"11","school_name":"中公教育"},{"cid":"1156","course_tname":"中公教育","course_name":"行测数量关系核心考点之数学运算必备知识1","course_price":"0.00","course_pic":"http://img.dianfu.net/img/20150911/7c3297250d255e59f781079b3e33db12.jpg","course_paycount":"21","school_name":"中公教育"},{"cid":"1146","course_tname":"中公教育","course_name":"行测数量关系核心考点之数学运算必备知识2","course_price":"0.00","course_pic":"http://img.dianfu.net/img/20150911/6f945dd193ef1ff8d2ae85b0ea7a725d.jpg","course_paycount":"32","school_name":"中公教育"},{"cid":"1149","course_tname":"中公教育","course_name":"行测数量关系核心考点之数学运算必备知识3","course_price":"0.00","course_pic":"http://img.dianfu.net/img/20150911/f5839810c1d9be265ccb8b29f11b206b.jpg","course_paycount":"34","school_name":"中公教育"}]
     * count : 3819
     * limit : 10
     * curpage : 1
     */

    private int count;
    private int limit;
    private int curpage;
    private List<DatalistBean> datalist;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCurpage() {
        return curpage;
    }

    public void setCurpage(int curpage) {
        this.curpage = curpage;
    }

    public List<DatalistBean> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<DatalistBean> datalist) {
        this.datalist = datalist;
    }

    public static class DatalistBean implements  Serializable{
        /**
         * cid : 5857
         * course_tname : 丁丁
         * course_name : 3DMAX效果图-实战篇（欧式二）
         * course_price : 20.00
         * course_pic : http://img.dianfu.net/img/20170216/40799f98cfc235f2a2eb46837d5cff84.jpg
         * course_paycount : 0
         * school_name : 吉大教育
         */

        private String cid;
        private String course_tname;
        private String course_name;
        private String course_price;
        private String course_pic;
        private String course_paycount;
        private String school_name;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCourse_tname() {
            return course_tname;
        }

        public void setCourse_tname(String course_tname) {
            this.course_tname = course_tname;
        }

        public String getCourse_name() {
            return course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }

        public String getCourse_price() {
            return course_price;
        }

        public void setCourse_price(String course_price) {
            this.course_price = course_price;
        }

        public String getCourse_pic() {
            return course_pic;
        }

        public void setCourse_pic(String course_pic) {
            this.course_pic = course_pic;
        }

        public String getCourse_paycount() {
            return course_paycount;
        }

        public void setCourse_paycount(String course_paycount) {
            this.course_paycount = course_paycount;
        }

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }
    }
}

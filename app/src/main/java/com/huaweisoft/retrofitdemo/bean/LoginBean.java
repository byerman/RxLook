package com.huaweisoft.retrofitdemo.bean;

import java.util.List;

public class LoginBean extends BaseBean{

    /**
     * data : {"chapterTops":[],"collectIds":[],"email":"","icon":"","id":1944,"password":"","token":"","type":0,"username":"老白1038"}
     * errorCode : 0
     * errorMsg :
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * chapterTops : []
         * collectIds : []
         * email :
         * icon :
         * id : 1944
         * password :
         * token :
         * type : 0
         * username : 老白1038
         */

        private String email;
        private String icon;
        private int id;
        private String password;
        private String token;
        private int type;
        private String username;
        private List<?> chapterTops;
        private List<?> collectIds;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<?> getChapterTops() {
            return chapterTops;
        }

        public void setChapterTops(List<?> chapterTops) {
            this.chapterTops = chapterTops;
        }

        public List<?> getCollectIds() {
            return collectIds;
        }

        public void setCollectIds(List<?> collectIds) {
            this.collectIds = collectIds;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "email='" + email + '\'' +
                    ", icon='" + icon + '\'' +
                    ", id=" + id +
                    ", password='" + password + '\'' +
                    ", token='" + token + '\'' +
                    ", type=" + type +
                    ", username='" + username + '\'' +
                    ", chapterTops=" + chapterTops +
                    ", collectIds=" + collectIds +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "data=" + data.toString() +
                ", errorCode=" + getErrorCode() +
                ", errorMsg='" + getErrorMsg() + '\'' +
                '}';
    }
}

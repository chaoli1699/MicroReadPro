package cn.lenovo.microreadpro.model;

/**
 * Created by Aaron on 2017/4/8.
 */

public class MUser {

    private String code;
    private String info;
    private User user;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class User{
        private int uid;
        private String username;
        private String password;
        private int sex;
        private String regist_time;
        private String last_login_time;
        private String district;
        private String introduce;
        private String role;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getRegist_time() {
            return regist_time;
        }

        public void setRegist_time(String regist_time) {
            this.regist_time = regist_time;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}

package cn.lenovo.microreadpro.model;

import java.io.Serializable;

/**
 * Created by Aaron on 2016/12/30.
 */

public class UserBean implements Serializable {

    public enum SEX implements Serializable {男,女}

    private String username;
    private String password;
    private SEX sex;
    private String sign;
    private String distance;
    private int loginStatus;
    private FontBean font;

    public UserBean(){
        this.username="";
        this.password="";
        this.sex=SEX.男;
        this.distance="未知";
        this.sign="此人很懒，尚未添加任何心情。";
        this.loginStatus=0;
        this.font=new FontBean();
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

    public SEX getSex() {
        return sex;
    }

    public void setSex(SEX sex) {
        this.sex = sex;
    }

    public String getSign() {
        return sign;
    }

    public int getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public FontBean getFont() {
        return font;
    }

    public void setFont(FontBean font) {
        this.font = font;
    }
}

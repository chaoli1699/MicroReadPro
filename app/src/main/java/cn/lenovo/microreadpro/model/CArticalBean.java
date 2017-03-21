package cn.lenovo.microreadpro.model;

import java.io.Serializable;

/**
 * Created by Aaron on 2017/2/10.
 */

public class CArticalBean extends ArticalBox.Artical implements Serializable{

    private String col_time;
    private String short_desc;
    private UserBean belongs;

    public String getCol_time() {
        return col_time;
    }

    public void setCol_time(String col_time) {
        this.col_time = col_time;
    }

    public UserBean getBelongs() {
        return belongs;
    }

    public void setBelongs(UserBean belongs) {
        this.belongs = belongs;
    }

    public String getShortDesc() {
        return short_desc;
    }

    public void setShortDesc(String shortDesc) {
        this.short_desc = shortDesc;
    }
}

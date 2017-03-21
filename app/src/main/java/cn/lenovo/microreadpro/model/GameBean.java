package cn.lenovo.microreadpro.model;

import java.io.Serializable;

/**
 * Created by Aaron on 2017/2/26.
 */

public class GameBean implements Serializable{

    private String name;
    private String desc;
    private String img_path;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

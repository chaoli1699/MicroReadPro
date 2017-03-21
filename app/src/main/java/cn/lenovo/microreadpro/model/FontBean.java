package cn.lenovo.microreadpro.model;

import java.io.Serializable;

/**
 * Created by Aaron on 2017/2/8.
 */

public class FontBean implements Serializable{

    private int font_size;
    private String font_color;
    private String font_family;

    public FontBean(){
        this.font_size=18;
        this.font_color="grey";
        //宋体、黑体、微软雅黑、Arial, Helvetica, sans-serif此4种字体。
        this.font_family="宋体";
    }

    public int getFont_size() {
        return font_size;
    }

    public void setFont_size(int font_size) {
        this.font_size = font_size;
    }

    public String getFont_color() {
        return font_color;
    }

    public void setFont_color(String font_color) {
        this.font_color = font_color;
    }

    public String getFont_family() {
        return font_family;
    }

    public void setFont_family(String font_family) {
        this.font_family = font_family;
    }
}

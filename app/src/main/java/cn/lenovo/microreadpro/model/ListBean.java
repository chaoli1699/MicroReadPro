package cn.lenovo.microreadpro.model;

/**
 * Created by Aaron on 2017/1/2.
 */

public class ListBean {

    private String title;
    private String subtitle;
    private String image;
    private String value;
    private boolean iconShow;
    private boolean isButton;

    public ListBean(){
        this.title="";
        this.subtitle="";
        this.image="";
        this.value="";
        this.iconShow=false;
        isButton=false;
    }

    public boolean isIconShow() {
        return iconShow;
    }

    public void setIconShow(boolean iconShow) {
        this.iconShow = iconShow;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isButton() {
        return isButton;
    }

    public void setButton(boolean button) {
        isButton = button;
    }
}

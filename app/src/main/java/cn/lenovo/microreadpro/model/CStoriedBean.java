package cn.lenovo.microreadpro.model;

/**
 * Created by Aaron on 2017/1/4.
 */

public class CStoriedBean extends NewsEntity.StoriesBean {

    private UserBean belongs;

    public UserBean getBelongs() {
        return belongs;
    }

    public void setBelongs(UserBean belongs) {
        this.belongs = belongs;
    }
}

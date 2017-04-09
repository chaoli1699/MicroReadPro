package cn.lenovo.microreadpro.view;

import java.util.List;

import cn.lenovo.microreadpro.model.ListBean;

/**
 * Created by Aaron on 2017/1/2.
 */

public interface UserCenterView {

    void getUserInfoSuccess(List<ListBean> myListObjs);

    void chanInfoSuccess();
    void chanInfoFail(String msg);
}

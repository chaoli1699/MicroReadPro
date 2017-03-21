package cn.lenovo.microreadpro.view;

import cn.lenovo.microreadpro.base.BaseView;

/**
 * Created by Aaron on 2017/1/2.
 */

public interface LoginView extends BaseView {

    void LoginSuccess();
    void LoginFail(String msg);
}

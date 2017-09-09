package cn.lenovo.eleccal.view;

import java.util.List;

import cn.lenovo.eleccal.base.BaseView;
import cn.lenovo.eleccal.model.User;

/**
 * Created by Aaron on 2017/1/9.
 */

public interface ManagerView extends BaseView{

    void getUsersSuccess(List<User> users);
    void insertUserSuccess();
    void removeUserSuccess();
    void calculaeSuccess();
    void resetUserMacSuccess();

    void getUsersFail(String msg);
    void insertUserFail(String msg);
    void removeUserFail(String msg);
    void calculateFail(String msg);
}

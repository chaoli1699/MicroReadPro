package cn.lenovo.microreadpro.presenter;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.UserBean;
import cn.lenovo.microreadpro.view.RegistView;

/**
 * Created by Aaron on 2017/1/2.
 */

public class RegistPresenter extends BasePresenter<RegistView> {

    private MyApplication mApp;
    private UserBean currentUser;

    public RegistPresenter(RegistView view){
        attachView(view);
        mApp= (MyApplication) MyApplication.getInstance();
    }

    public void regist(String name,String pwd){

        if (mApp.isUserExist(name)){
            view.registFail("用户已存在");
        }else {
            currentUser=new UserBean();
            currentUser.setUsername(name);
            currentUser.setPassword(pwd);
            mApp.resetUsers(currentUser);
            view.registSuccess();
        }
    }
}

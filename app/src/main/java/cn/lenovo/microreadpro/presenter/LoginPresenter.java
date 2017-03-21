package cn.lenovo.microreadpro.presenter;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.utils.ACache;
import cn.lenovo.microreadpro.view.LoginView;

/**
 * Created by Aaron on 2017/1/2.
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    private MyApplication mApp;

    public LoginPresenter(LoginView view){
        attachView(view);
        mApp= (MyApplication) MyApplication.getInstance();
    }

    /**
     * 验证密码是否正确
     * @param pwd
     * @return
     */
    private boolean checkPwd(String pwd){

        if (mApp.currentUser.getPassword().equals(pwd)){
            return true;
        }
        return false;
    }

    /**
     * 登录
     * @param name
     * @param pwd
     */
    public void login(String name,String pwd){

        if (mApp.isUserExist(name)){
            if (checkPwd(pwd)){
                mApp.currentUser.setLoginStatus(1);
                view.LoginSuccess();
            }else {
                view.LoginFail("密码不匹配");
            }
        }else {
            view.LoginFail("用户不存在");
        }
    }
}

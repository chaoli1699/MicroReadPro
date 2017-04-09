package cn.lenovo.microreadpro.presenter;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.MUser;
import cn.lenovo.microreadpro.model.UserBean;
import cn.lenovo.microreadpro.net.ApiCallback;
import cn.lenovo.microreadpro.utils.SystermParams;
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

//        if (mApp.isUserExist(name)){
//            view.registFail("用户已存在");
//        }else {
//            currentUser=new UserBean();
//            currentUser.setUsername(name);
//            currentUser.setPassword(pwd);
//            mApp.resetUsers(currentUser);
//            view.registSuccess();
//        }

        addSubscription(SystermParams.microReadApiStores.user_manager("regist", name, pwd), new ApiCallback<MUser>() {
            @Override
            public void onSuccess(MUser model) {
                if (model.getCode().equals("0")){
                    view.registSuccess();
                }else {
                    view.registFail(model.getInfo());
                }
            }

            @Override
            public void onFailure(String msg) {
                view.registFail(msg);
            }

            @Override
            public void onFinish() {
                view.hideLoading();
            }
        });
    }
}

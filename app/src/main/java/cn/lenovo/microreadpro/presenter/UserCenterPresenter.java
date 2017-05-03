package cn.lenovo.microreadpro.presenter;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.MUFeture;
import cn.lenovo.microreadpro.net.ApiCallback;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.UserCenterView;

/**
 * Created by Aaron on 2017/1/2.
 */

public class UserCenterPresenter extends BasePresenter<UserCenterView> {

    private MyApplication mApp;

    public UserCenterPresenter(UserCenterView view){
        attachView(view);
        mApp= (MyApplication) MyApplication.getInstance();
    }

    public void getUFetures(){
        addSubscription(SystermParams.microReadApiStores.load_ufeture("load", mApp.currentUser.getUid()), new ApiCallback<MUFeture>() {
            @Override
            public void onSuccess(MUFeture model) {
                if (model.getCode()==0){
                    view.getUFeturesSuccess(model.getUfetures());
                }
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onFinish() {

            }
        });

    }
}

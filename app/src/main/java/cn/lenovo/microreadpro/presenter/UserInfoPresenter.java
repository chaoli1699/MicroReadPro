package cn.lenovo.microreadpro.presenter;

import java.util.ArrayList;
import java.util.List;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.MUFeture;
import cn.lenovo.microreadpro.model.MUser;
import cn.lenovo.microreadpro.net.ApiCallback;
import cn.lenovo.microreadpro.net.MicroReadApiStores;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.UserInfoView;

/**
 * Created by Aaron on 2017/1/2.
 */

public class UserInfoPresenter extends BasePresenter<UserInfoView> {

    private MyApplication mApp;

    public UserInfoPresenter(UserInfoView view){
        attachView(view);
        mApp= (MyApplication) MyApplication.getInstance();
    }

    public void chan_sex(){

        addSubscription(SystermParams.microReadApiStores.chan_sex("chansex", mApp.currentUser.getUid(), mApp.currentUser.getSex()), new ApiCallback<MUser>() {
            @Override
            public void onSuccess(MUser model) {

                if (model.getCode().equals("0")){
                    mApp.currentUser=model.getUser();
                    view.chanInfoSuccess();
                }else {
                    view.chanInfoFail(model.getInfo());
                }

            }

            @Override
            public void onFailure(String msg) {
                view.chanInfoFail(msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    public void chan_city(){

        addSubscription(SystermParams.microReadApiStores.chan_city("chancity", mApp.currentUser.getUid(), mApp.currentUser.getDistrict()), new ApiCallback<MUser>() {
            @Override
            public void onSuccess(MUser model) {

                if (model.getCode().equals("0")){
                    mApp.currentUser=model.getUser();
                    view.chanInfoSuccess();
                }else {
                    view.chanInfoFail(model.getInfo());
                }

            }

            @Override
            public void onFailure(String msg) {
                view.chanInfoFail(msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    public void chan_sign(){

        addSubscription(SystermParams.microReadApiStores.chan_sign("chansign", mApp.currentUser.getUid(), mApp.currentUser.getIntroduce()), new ApiCallback<MUser>() {
            @Override
            public void onSuccess(MUser model) {

                if (model.getCode().equals("0")){
                    mApp.currentUser=model.getUser();
                    view.chanInfoSuccess();
                }else {
                    view.chanInfoFail(model.getInfo());
                }

            }

            @Override
            public void onFailure(String msg) {
                view.chanInfoFail(msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    public void getUserInfo(){
        List<MUFeture.UFeture> fetures=new ArrayList<>();

        String head_path=mApp.currentUser.getHead_path();
        String username=mApp.currentUser.getUsername();
        String sex=(mApp.currentUser.getSex()>0)? "男":"女";
        String district=(mApp.currentUser.getDistrict().equals(""))? "未知":mApp.currentUser.getDistrict();
        String sign=(mApp.currentUser.getIntroduce().equals(""))? "未填写":mApp.currentUser.getIntroduce();

        MUFeture.UFeture feture=new MUFeture().new UFeture(1, "account", "账号", head_path, username, "", 0, 0, 0, -1, 1, "");
        MUFeture.UFeture feture2=new MUFeture().new UFeture(2, "sex", "性别", "", "", "", 0, 0, 0, -1, 1, sex);
        MUFeture.UFeture feture3=new MUFeture().new UFeture(3, "district", "地区", "", "", "", 0, 0, 0, -1, 0, district);
        MUFeture.UFeture feture4=new MUFeture().new UFeture(4, "sign", "签名", "", "", "", 0, 0, 0, -1, 0, sign);
        MUFeture.UFeture feture5=new MUFeture().new UFeture(5, "logout", "", "", "", "", 0, 1, 0, -1, 1, "");

        fetures.add(feture);
        fetures.add(feture2);
        fetures.add(feture3);
        fetures.add(feture4);
        fetures.add(feture5);

        view.getUserInfoSuccess(fetures);

    }
}

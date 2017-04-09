package cn.lenovo.microreadpro.presenter;

import java.util.ArrayList;
import java.util.List;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.ListBean;
import cn.lenovo.microreadpro.model.MUser;
import cn.lenovo.microreadpro.model.UserBean;
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

        List<ListBean> listItems=new ArrayList<>();

        ListBean myListObj=new ListBean();
        myListObj.setTitle("账号");
        myListObj.setSubtitle(mApp.currentUser.getUsername());
        myListObj.setImage("这里有图片");
        listItems.add(myListObj);

        ListBean myListObj2=new ListBean();
        myListObj2.setTitle("性别");
        myListObj2.setValue((mApp.currentUser.getSex()>0)? "男": "女");
        listItems.add(myListObj2);

        ListBean myListObj3=new ListBean();
        myListObj3.setTitle("地区");
        myListObj3.setValue((mApp.currentUser.getDistrict()==null)? "未知":mApp.currentUser.getDistrict());
        listItems.add(myListObj3);

        ListBean myListObj4=new ListBean();
        myListObj4.setTitle("签名");
//        myListObj4.setValue(mApp.currentUser.getSign());
        myListObj4.setValue((mApp.currentUser.getIntroduce().equals(""))? "此人很懒，暂无任何心情！":mApp.currentUser.getIntroduce());
        listItems.add(myListObj4);

        ListBean myListObj5=new ListBean();
        listItems.add(myListObj5);

        ListBean myListObj6=new ListBean();
        myListObj6.setTitle("退出账号");
        myListObj6.setButton(true);
        listItems.add(myListObj6);

        view.getUserInfoSuccess(listItems);
    }
}

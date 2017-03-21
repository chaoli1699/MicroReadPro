package cn.lenovo.microreadpro.presenter;

import java.util.ArrayList;
import java.util.List;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.ListBean;
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

    public void getUserInfo(){

        List<ListBean> listItems=new ArrayList<>();

        ListBean myListObj=new ListBean();
        myListObj.setTitle("账号");
        myListObj.setSubtitle(mApp.currentUser.getUsername());
        myListObj.setImage("这里有图片");
        listItems.add(myListObj);

        ListBean myListObj2=new ListBean();
        myListObj2.setTitle("性别");
        myListObj2.setValue(mApp.currentUser.getSex()+"");
        listItems.add(myListObj2);

        ListBean myListObj3=new ListBean();
        myListObj3.setTitle("地区");
        myListObj3.setValue(mApp.currentUser.getDistance());
        listItems.add(myListObj3);

        ListBean myListObj4=new ListBean();
        myListObj4.setTitle("签名");
        myListObj4.setValue(mApp.currentUser.getSign());
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

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

    public void getMyList(){

        List<ListBean> listItems=new ArrayList<>();

        ListBean myListObj=new ListBean();
        myListObj.setTitle("账号");
        myListObj.setSubtitle(mApp.currentUser.getUsername());
        myListObj.setSex(mApp.currentUser.getSex());
        myListObj.setImage("这里有图片");
        myListObj.setIconShow(true);
        myListObj.setLineShow(false);
        listItems.add(myListObj);

        ListBean myListObj2=new ListBean();
        listItems.add(myListObj2);

        ListBean myListObj3=new ListBean();
        myListObj3.setTitle("时光轴");
        myListObj3.setIconShow(true);
        listItems.add(myListObj3);

        ListBean myListObj4=new ListBean();
        myListObj4.setTitle("收藏");
        myListObj4.setIconShow(true);
        myListObj4.setLineShow(false);
        listItems.add(myListObj4);

        view.getMyListSuccess(listItems);
    }
}

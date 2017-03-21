package cn.lenovo.eleccal.presenter;

import java.util.List;

import cn.lenovo.eleccal.base.BasePresenter;
import cn.lenovo.eleccal.base.MyApplication;
import cn.lenovo.eleccal.model.User;
import cn.lenovo.eleccal.view.MainView;

/**
 * Created by Aaron on 2017/1/9.
 */

public class MainPresenter extends BasePresenter<MainView> {

    private List<User> users;
    private MyApplication mApp;

    public MainPresenter(MainView view){
        attachView(view);
        mApp= (MyApplication) MyApplication.getInstance();
    }

    public void submitValues(User user){
        mApp.resetUser(user);
        view.submitSuccess();
    }
}

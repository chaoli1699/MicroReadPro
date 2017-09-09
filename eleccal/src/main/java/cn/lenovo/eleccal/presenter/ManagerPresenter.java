package cn.lenovo.eleccal.presenter;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cn.lenovo.eleccal.base.BasePresenter;
import cn.lenovo.eleccal.base.MyApplication;
import cn.lenovo.eleccal.model.User;
import cn.lenovo.eleccal.utils.LogUtil;
import cn.lenovo.eleccal.view.ManagerView;

/**
 * Created by Aaron on 2017/1/9.
 */

public class ManagerPresenter extends BasePresenter<ManagerView>{


    private MyApplication mApp;
    private List<User> users=null;

    public ManagerPresenter(ManagerView view){
        attachView(view);
        mApp= (MyApplication) MyApplication.getInstance();
    }

    public void getUsers(){
        users= mApp.getUsers();
        view.getUsersSuccess(users);
    }

    public boolean userExist(User mUser){

        if (users.size()>0){
            for (User user: users){
                if (user.getName().equals(mUser.getName())){
                    return true;
                }
            }
        }
        return false;
    }

    public void inseretUser(User mUser){
        if (!userExist(mUser)){
            if (users.size()<mApp.user_max){
                users.add(mUser);
                mApp.aCache.put("users",new Gson().toJson(users));
                view.insertUserSuccess();
            }else {
                view.insertUserFail("添加用户超过上限（"+mApp.user_max+"户）");
            }
        }else {
            view.insertUserFail("用户已存在");
        }
    }

    public void removeUser(User mUser){

        if (userExist(mUser)){
            for (int i=0;i<users.size();i++){
                User user=users.get(i);
                if (user.getName().equals(mUser.getName())){
                    users.remove(i);
                    mApp.aCache.put("users",new Gson().toJson(users));
                    view.removeUserSuccess();
                }
            }
        }
    }

    public void calculate(float total_fee){

        float left_fee=total_fee;
        float aver_fee;
        float should_pay;
        if (users.size()>0){
            for (User u:users){
                if (left_fee<u.getAir_fee()){
                    view.calculateFail("费用不合理，请核查后再计算！");
                    return;
                }
                left_fee-=u.getAir_fee();
            }

            aver_fee=left_fee/users.size();

            for (User u:users){
                should_pay=u.getAir_fee()+aver_fee;
                should_pay=(Math.round(should_pay*100))/100;
                u.setShould_pay(should_pay);

                mApp.resetUser(u);
            }

            view.calculaeSuccess();
        }
    }

    public void resetUserMax(String user_max){
        mApp.aCache.put("user_max",user_max);
        mApp.user_max=Integer.valueOf(user_max);
        view.resetUserMacSuccess();
    }
}

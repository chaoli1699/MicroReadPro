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
//        if (users.size()>0){
            view.getUsersSuccess(users);
//        }else {
//            view.getUsersFail("用户列表为空");
//        }
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
            if (users.size()<4){
                users.add(mUser);
                mApp.aCache.put("users",new Gson().toJson(users));
                view.insertUserSuccess();
            }else {
                view.insertUserFail("添加用户超过上限（4户）");
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
        }else {
            view.removeUserFail("所选用户不存在");
        }
    }

    public void calculate(float total_fee){

        if (users==null){
            getUsers();
        }

        float air_total=0;
        float aver_fee=0;
        if (users.size()>0){
            for (User u:users){
                air_total+=u.getAir_fee();
            }
            LogUtil.d(air_total+"");

            aver_fee=(total_fee-air_total)/(users.size());

            for (int i=0;i<users.size();i++){
                User user=users.get(i);
                float should_pay=user.getAir_fee()+aver_fee;
                should_pay=(Math.round(should_pay*100))/100;
                user.setShould_pay(should_pay);

                mApp.resetUser(user);
            }
            view.calculaeSuccess();
        }else {
            view.calculateFail("用户列表为空");
        }
    }
}

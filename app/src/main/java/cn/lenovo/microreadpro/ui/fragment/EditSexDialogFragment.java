package cn.lenovo.microreadpro.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.mcxiaoke.bus.Bus;

import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.UserBean;
import cn.lenovo.microreadpro.utils.SystermParams;

import static cn.lenovo.microreadpro.model.UserBean.SEX.女;
import static cn.lenovo.microreadpro.model.UserBean.SEX.男;


/**
 * Created by Aaron on 2017/1/5.
 */

public class EditSexDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{

    private String[] items={"男","女"};
//    private UserBean.SEX chosed=男;
    private int chosed=1;
    private int position=0;
    private MyApplication mApp;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
        mApp= (MyApplication) MyApplication.getInstance();

        if (mApp.currentUser.getSex()>0){
            position=0;
        }else {
            position=1;
        }

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                .setTitle("选择性别")
                .setSingleChoiceItems(items, position ,this)
                .setPositiveButton("确定", this)
                .setNegativeButton("取消", this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        switch (i){
            case -1:
                mApp.currentUser.setSex(chosed);
                Bus.getDefault().post("chansex");
                break;
            case 0:
                chosed=1;
                break;
            case 1:
                chosed=0;
                break;
        }

        /**
         *mApp.currentUser.setLoginStatus(0);
         Intent intent=new Intent(about_user);
         intent.putExtra("islogin",false);
         getActivity().sendBroadcast(intent);
         */
    }
}

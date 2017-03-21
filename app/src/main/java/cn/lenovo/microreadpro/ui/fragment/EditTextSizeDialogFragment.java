package cn.lenovo.microreadpro.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.mcxiaoke.bus.Bus;

import cn.lenovo.microreadpro.base.MyApplication;


/**
 * Created by Aaron on 2017/1/5.
 */

public class EditTextSizeDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{

    private String[] items={"超大号","大号","正常","小号"};
    private int[] size={24,21,18,15};
    private MyApplication mApp;
    private int fontSize=size[2];
    private int position;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
        mApp= (MyApplication) MyApplication.getInstance();

        for (int i=0;i<size.length;i++){
            if (mApp.font.getFont_size()==size[i]){
                fontSize=size[i];
                position=i;
            }
        }

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                .setTitle("选择字体大小")
                .setSingleChoiceItems(items, position ,this)
                .setPositiveButton("确定", this)
                .setNegativeButton("取消", this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        switch (i){
            case -1:
                if (mApp.currentUser==null){
                    mApp.font.setFont_size(fontSize);
                }else {
                    mApp.currentUser.getFont().setFont_size(fontSize);
                    mApp.resetUsers(mApp.currentUser);
                }
                Bus.getDefault().post("resize");
                break;
            case 0:
            case 1:
            case 2:
            case 3:
                fontSize=size[i];
                break;
        }
    }
}

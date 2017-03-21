package cn.lenovo.eleccal.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.mcxiaoke.bus.Bus;

import cn.lenovo.eleccal.utils.EventUtil;

/**
 * Created by Aaron on 2017/1/9.
 */

public class RemoveUserDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                .setTitle("提示")
                .setMessage("确定要移除该用户？")
                .setPositiveButton("确定",this)
                .setNegativeButton("取消",this);
//        return super.onCreateDialog(savedInstanceState);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
//        EventUtil.showToast(getActivity(),i+"");
        if (i==-1){
            Bus.getDefault().post("positive");
        }
    }
}

package cn.lenovo.microreadpro.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.mcxiaoke.bus.Bus;

/**
 * Created by Aaron on 2017/2/22.
 */

public class ExitWhileSpeakingDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                .setTitle("温馨提示")
                .setMessage("您正在使用阅读功能，退出页面将停止阅读这篇文章！")
                .setPositiveButton("确定",this)
                .setNegativeButton("取消",this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i==-1){
            Bus.getDefault().post("exit");
        }
    }
}

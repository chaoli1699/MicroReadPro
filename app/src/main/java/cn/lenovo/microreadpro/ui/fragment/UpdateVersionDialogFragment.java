package cn.lenovo.microreadpro.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import cn.lenovo.microreadpro.ui.activity.MainActivity;
import cn.lenovo.microreadpro.ui.activity.UpdateWebActivity;

/**
 * Created by Aaron on 2017/2/22.
 */

public class UpdateVersionDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                .setTitle("温馨提示")
                .setMessage("有新版本，是否现在更新？！")
                .setPositiveButton("更新",this)
                .setNegativeButton("下次再说",this);
        return builder.create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(false);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i==-1){
            Intent intent=new Intent(getActivity(),UpdateWebActivity.class);
            startActivity(intent);
            getActivity().finish();
        }else if (i==-2){
            Intent intent=new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

    }
}

package cn.lenovo.microreadpro.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.utils.SystermParams;

/**
 * Created by Aaron on 2017/2/8.
 */

public class EditSignDialogFragment extends DialogFragment implements View.OnClickListener{

    @Bind(R.id.fragment_edit_sign_content)
    AppCompatEditText content;
    @Bind(R.id.fragment_edit_sign_cancel)
    AppCompatButton cancel;
    @Bind(R.id.fragment_edit_sign_ok)
    AppCompatButton ok;

    private View rootView;
    private MyApplication mApp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        mApp= (MyApplication) MyApplication.getInstance();

        if (rootView==null) {
            rootView = inflater.inflate(R.layout.fragment_edit_sign, container);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        cancel.setOnClickListener(this);
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.fragment_edit_sign_cancel:
                dismiss();
                break;

            case R.id.fragment_edit_sign_ok:

                if (content.getText().length()>0){
                    mApp.currentUser.setSign(content.getText().toString());
                    Intent intent=new Intent(SystermParams.action);
                    intent.putExtra("user","change");
                    getActivity().sendBroadcast(intent);
                    dismiss();
                }else {
                    content.setError(getResources().getString(R.string.sign_isEmpty));
                }

                break;
        }
    }
}

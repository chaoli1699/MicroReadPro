package cn.lenovo.microreadpro.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mcxiaoke.bus.Bus;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.base.MRFragment;
import cn.lenovo.microreadpro.presenter.LoginPresenter;

import cn.lenovo.microreadpro.utils.EventUtil;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.LoginView;

/**
 * Created by Aaron on 2017/1/2.
 */

public class LoginFragment extends MRFragment<LoginPresenter> implements LoginView,View.OnClickListener {

    @Bind(R.id.fg_login_name)
    AppCompatEditText name;
    @Bind(R.id.fg_login_pwd)
    AppCompatEditText pwd;
    @Bind(R.id.fg_login_check)
    Button check;
    @Bind(R.id.fg_login_toRegist)
    TextView toregist;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        if (rootView==null){
            rootView=inflater.inflate(R.layout.fragment_login,container,false);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        check.setOnClickListener(this);
        toregist.setOnClickListener(this);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void LoginSuccess() {
        Intent intent=new Intent(SystermParams.action);
        intent.putExtra("user","login");
        getActivity().sendBroadcast(intent);
        getActivity().finish();
    }

    @Override
    public void LoginFail(String msg) {
        EventUtil.showToast(getActivity(),msg);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("LoginFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("LoginFragment");
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if (id==R.id.fg_login_check){
            String username=name.getText().toString();
            String password=pwd.getText().toString();
            if (TextUtils.isEmpty(username)){
                name.setError("账户名为空");
                return;
            }
            if (TextUtils.isEmpty(password)){
                pwd.setError("密码为空");
                return;
            }
            mPresenter.login(username,password);
        }else if (id==R.id.fg_login_toRegist){
            Bus.getDefault().post("注册");
        }
    }
}

package cn.lenovo.microreadpro.ui.fragment;

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
import cn.lenovo.microreadpro.presenter.RegistPresenter;
import cn.lenovo.microreadpro.utils.EventUtil;
import cn.lenovo.microreadpro.view.RegistView;

/**
 * Created by Aaron on 2017/1/2.
 */

public class RegistFragment extends MRFragment<RegistPresenter> implements RegistView,View.OnClickListener {

    @Bind(R.id.fg_regist_name)
    AppCompatEditText name;
    @Bind(R.id.fg_regist_pwd)
    AppCompatEditText pwd;
    @Bind(R.id.fg_regist_pwd2)
    AppCompatEditText pwd2;
    @Bind(R.id.fg_regist_check)
    Button check;
    @Bind(R.id.fg_regist_toLogin)
    TextView tologin;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        if (rootView==null){
            rootView=inflater.inflate(R.layout.fragment_regist,container,false);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        check.setOnClickListener(this);
        tologin.setOnClickListener(this);
    }

    @Override
    protected RegistPresenter createPresenter() {
        return new RegistPresenter(this);
    }

    @Override
    public void registSuccess() {
        Bus.getDefault().post("登录");
        EventUtil.showToast(getActivity(),"恭喜您，注册成功");
    }

    @Override
    public void registFail(String msg) {
        EventUtil.showToast(getActivity(),msg);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("RegistFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("RegistFragment");
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if (id==R.id.fg_regist_check){
            String username=name.getText().toString();
            String password=pwd.getText().toString();
            String again_password=pwd2.getText().toString();
            if (TextUtils.isEmpty(username)){
                name.setError("账户名为空");
                return;
            }
            if (TextUtils.isEmpty(password)){
                pwd.setError("密码为空");
                return;
            }
            if (TextUtils.isEmpty(again_password)){
                pwd2.setError("再次输入为空");
            }

            if (password.equals(again_password)){
                mPresenter.regist(username,password);
            }else {
                EventUtil.showToast(getActivity(),"两次输入密码不一致");
            }

        }else if (id==R.id.fg_regist_toLogin){
            Bus.getDefault().post("登录");
        }
    }
}

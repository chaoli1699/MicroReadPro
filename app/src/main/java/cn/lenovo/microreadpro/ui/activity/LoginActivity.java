package cn.lenovo.microreadpro.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.base.BaseActivity;

import cn.lenovo.microreadpro.ui.fragment.LoginFragment;
import cn.lenovo.microreadpro.ui.fragment.RegistFragment;

/**
 * Created by Aaron on 2017/1/2.
 */

public class LoginActivity extends BaseActivity {

    @Bind(R.id.toolbar_for_c_u)
    Toolbar toolbar;
    private Fragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_login);

        toolbar.setTitle("登录");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragment=new LoginFragment();
        replaceFragment();
    }

    @BusReceiver
    public void onStringEvent(String event) {
        // handle your event
        toolbar.setTitle(event);
        if (event.equals("注册")){
            fragment=new RegistFragment();
            replaceFragment();
        }else if (event.equals("登录")){
            fragment=new LoginFragment();
            replaceFragment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bus.getDefault().register(this);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bus.getDefault().unregister(this);
    }

    private void replaceFragment(){

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragment.isAdded()) {
            ft.show(fragment);
        } else {
            ft.replace(R.id.login_container, fragment);
        }
        ft.commitAllowingStateLoss();
    }
}

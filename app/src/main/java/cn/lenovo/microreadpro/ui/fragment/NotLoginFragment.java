package cn.lenovo.microreadpro.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.base.BaseFragment;
import cn.lenovo.microreadpro.ui.activity.LoginActivity;

/**
 * Created by Aaron on 2017/1/2.
 */

public class NotLoginFragment extends BaseFragment {

    @Bind(R.id.toolbar_for_c_u)
    Toolbar toolbar;
    @Bind(R.id.fg_usercenter_tologin)
    TextView tologin;

    private View rootView;
    private static DrawerLayout drawer;
    private static final String action="loginStatus";

    public interface NotLoginFragmentExtra{
        DrawerLayout getDrawer();
    }

    public static void setDrawer(NotLoginFragmentExtra notLoginFragmentExtra){
        if (drawer==null){
            drawer=notLoginFragmentExtra.getDrawer();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        if (rootView==null){
           rootView=inflater.inflate(R.layout.fragment_usercenter_notlogin,container,false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView(){

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        toolbar.setTitle(getString(R.string.user_center));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("NotLoginFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("NotLoginFragment");
    }
}

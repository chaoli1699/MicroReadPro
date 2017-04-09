package cn.lenovo.microreadpro.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcxiaoke.bus.Bus;
import com.umeng.analytics.MobclickAgent;
import com.zaaach.citypicker.CityPickerActivity;

import butterknife.Bind;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.base.BaseActivity;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.MUser;
import cn.lenovo.microreadpro.model.ShareBean;
import cn.lenovo.microreadpro.ui.fragment.ArticalFragment;
import cn.lenovo.microreadpro.ui.fragment.GameFragment;
import cn.lenovo.microreadpro.ui.fragment.NewsFragment;
import cn.lenovo.microreadpro.ui.fragment.NotLoginFragment;
import cn.lenovo.microreadpro.ui.fragment.UserCenterFragment;
import cn.lenovo.microreadpro.utils.EventUtil;
import cn.lenovo.microreadpro.utils.ShareUtil;
import cn.lenovo.microreadpro.utils.SystermParams;

import static cn.lenovo.microreadpro.ui.fragment.UserCenterFragment.REQUEST_CODE_PICK_CITY;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    ImageView userHead;
    TextView userName;
    TextView userSign;

    private Fragment fragment;
    private Long firstTime = 0L;
    private MyApplication mApp;

    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String user=intent.getStringExtra("user");
            String page=intent.getStringExtra("page");

            if (user!=null){
                if (user.equals("logout")){
                    mApp.isLogin=false;
                    EventUtil.showToast(MainActivity.this,"您已退出登录！");
                    mApp.resetCurrentUsers(null);
                    setMainDisplay("news");
                }else if (user.equals("login")){
                    mApp.isLogin=true;
                    EventUtil.showToast(MainActivity.this,"登录成功！");
                    mApp.resetCurrentUsers(mApp.currentUser);
                    setMainDisplay("news");
                }else if (user.equals("change")){
                    EventUtil.showToast(MainActivity.this,"修改成功！");
                    mApp.resetCurrentUsers(mApp.currentUser);
                    setMainDisplay("center");
                }
            }else if (page!=null){
                setMainDisplay(page);
            }

        }
    };

    public void setMainDisplay(String page){
        if (mApp.isLogin){
            userName.setText(mApp.currentUser.getUsername());
//            userSign.setText(mApp.currentUser.getSign());
            userSign.setText((mApp.currentUser.getIntroduce().equals("")? getResources().getString(R.string.default_userSign):mApp.currentUser.getIntroduce()));
        }else {
            userName.setText(getResources().getString(R.string.default_userName));
            userSign.setText(getResources().getString(R.string.default_userSign));
        }

        if (page.equals("none")){
            fragment = new NotLoginFragment();
            NotLoginFragment.setDrawer(new NotLoginFragment.NotLoginFragmentExtra() {
                @Override
                public DrawerLayout getDrawer() {
                    return drawer;
                }
            });
        }else if (page.equals("news")){
            fragment = new NewsFragment();
            NewsFragment.setDrawer(new NewsFragment.NewsFragmentExtra() {
                @Override
                public DrawerLayout getDrawer() {
                    return drawer;
                }
            });
        }else if (page.equals("artical")){
            fragment = new ArticalFragment();
            ArticalFragment.setDrawer(new ArticalFragment.ArticalFragmentExtra() {
                @Override
                public DrawerLayout getDrawer() {
                    return drawer;
                }
            });
        }else if (page.equals("game")){
            fragment=new GameFragment();
            GameFragment.setDrawer(new GameFragment.GameFragmenExtra() {
                @Override
                public DrawerLayout getDrawer() {
                    return drawer;
                }
            });
        }else if(page.equals("center")){
            fragment = new UserCenterFragment();
            UserCenterFragment.setDrawer(new UserCenterFragment.UserCenterFragmentExtra() {
                @Override
                public DrawerLayout getDrawer() {
                    return drawer;
                }
            });
        }else if (page.equals("collection")){
            startActivity(new Intent(MainActivity.this,CollectionActivity.class));
        }else if (page.equals("app")){
            startActivity(new Intent(MainActivity.this,AboutAppActivity.class));
        }else if(page.equals("share")){
            String str="我在使用"+getResources().getString(R.string.app_name)
                    +"关注时事，品读文章，还有更多精品游戏等你来玩，快来试试吧！点击下载->";
            ShareBean shareBean=new ShareBean();
            shareBean.setTitle(str + SystermParams.APK_DOWMLOAD_URL);
            shareBean.setText(str + SystermParams.APK_DOWMLOAD_URL);
            shareBean.setUrl(SystermParams.APK_DOWMLOAD_URL);
//            shareBean.setImage_url(SystermParams.DEFAULT_IMAGE_URL);
            ShareUtil.showShare(this,shareBean);
        }

        //切换页面
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragment.isAdded()) {
            ft.show(fragment);
        } else {
            ft.replace(R.id.main_content, fragment);
        }
        ft.commitAllowingStateLoss();
        drawer.closeDrawer(GravityCompat.START);
    }

    private void initFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(SystermParams.action);
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
        mApp= (MyApplication) MyApplication.getInstance();

        navigationView.setNavigationItemSelectedListener(this);

        View headView=navigationView.getHeaderView(0);
        userHead= (ImageView) headView.findViewById(R.id.user_head);
        userName= (TextView) headView.findViewById(R.id.user_name);
        userSign= (TextView) headView.findViewById(R.id.user_sign);

        userHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mApp.isLogin){
                    setMainDisplay("center");
                }else {
                    setMainDisplay("none");
                }
            }
        });

        setMainDisplay("news");
    }

    //重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK){
            if (data != null){
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
//                resultTV.setText("当前选择：" + city);
                mApp.currentUser.setDistrict(city);
                Bus.getDefault().post("chancity");
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 1500) {
                EventUtil.showToast(this, "再按一次退出");
                firstTime = secondTime;
            } else {
                mApp.exitApp();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        int id = item.getItemId();
        String page="news";
        if (id==R.id.nav_latest) {
            page="news";
        } else if (id==R.id.nav_artical){
            page="artical";
        } else if (id==R.id.nav_game){
            page="game";
        } else if (id == R.id.nav_collect){
            if (mApp.isLogin){
                page="collection";
            }else {
                page="none";
            }
        } else if (id == R.id.nav_app){
            page="app";
        } else if (id == R.id.nav_share) {
            page="share";
        }
        setMainDisplay(page);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onResume() {
        super.onResume();
        initFilter();
        MobclickAgent.onResume(this);
    }


    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}

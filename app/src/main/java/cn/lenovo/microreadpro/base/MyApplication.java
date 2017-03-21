package cn.lenovo.microreadpro.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import cn.lenovo.microreadpro.model.FontBean;
import cn.lenovo.microreadpro.model.UserBean;
import cn.lenovo.microreadpro.utils.ACache;
import cn.lenovo.microreadpro.utils.LogUtil;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.utils.TTSpeaker;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by lenovo on 2016/11/28.
 */

public class MyApplication extends Application {

    private static MyApplication mInstance;
    public static Context mContext;
    public Set<Activity> allActivities;
    public boolean isLogin=false;
    public List<UserBean> users;
    public UserBean currentUser;
    public ACache aCache;
    public FontBean font=new FontBean();
    public boolean isLatest=true;

    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;
    public TTSpeaker mTTSpeaker;

    @Override
    public void onCreate() {
        super.onCreate();

        aCache=ACache.get(this);
        mContext = getApplicationContext();
        mInstance = this;
        initScreenSize();
        //初始化友盟
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        LogUtil.init(this, true, false, 'd', "Race");
        //获取登录用户
        getCurrentUser();
        //创建播音员
        createSpeakerThread.start();
        //初始化分享接口
        ShareSDK.initSDK(this);
    }

    private Thread createSpeakerThread=new Thread(new Runnable() {
        @Override
        public void run() {
            mTTSpeaker=new TTSpeaker(MyApplication.this);
        }
    });

    public static Context getInstance() {
        return mInstance;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = mInstance.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mInstance.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 判断用户是否存在
     * @param name
     * @return
     */
    public boolean isUserExist(String name){

        if (name!=null&&name.length()>0){
            for (UserBean user: users){
                if (user.getUsername().equals(name)){
                    currentUser=user;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 重置用户列表
     */
    public void resetUsers(UserBean user){
        if (isUserExist(user.getUsername())){
            for (UserBean u:users){
                if (user.getUsername().equals(u.getUsername())){
                    u.setLoginStatus(user.getLoginStatus());
                }
            }
        }else {
            users.add(user);
        }
        aCache.put("users",new Gson().toJson(users));
    }

    /**
     * 获取登录用户信息
     */
    public void getCurrentUser(){

        if (users==null){
            users=SystermParams.getUsers();
        }

        for (UserBean user: users){
            if (user.getLoginStatus()==1){
                currentUser=user;
                font=currentUser.getFont();
                isLogin=true;
            }
        }
    }

    /**
     * 登记页面
     * @param act
     */
    public void registerActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<Activity>();
        }
        allActivities.add(act);
    }

    /**
     * 销毁页面
     * @param act
     */
    public void unregisterActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    /**
     * 退出应用
     */
    public void exitApp() {

        if (mTTSpeaker!=null){
            mTTSpeaker.release();
        }

        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    if (act != null && !act.isFinishing())
                        act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 获取当前系统语言
     *
     * @return 当前系统语言
     */
    public static String getLanguage() {
        Locale locale = mInstance.getResources().getConfiguration().locale;
        String language = locale.getDefault().toString();
        return language;
    }

    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }

    public static boolean isNetworkAvailable(Context context) {
        if(context !=null){
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if(info !=null){
                return info.isAvailable();
            }
        }
        return false;
    }
}

package cn.lenovo.eleccal.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import cn.lenovo.eleccal.model.User;
import cn.lenovo.eleccal.utils.ACache;

/**
 * Created by Aaron on 2017/1/9.
 */

public class MyApplication extends Application{

    private static MyApplication mInstance;
    public static Context mContext;
    public Set<Activity> allActivities;
    public ACache aCache;
    public List<User> users;

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

    @Override
    public void onCreate() {
        super.onCreate();

        aCache= ACache.get(this);
        mContext = getApplicationContext();
        mInstance = this;
        initScreenSize();
    }

    public static Context getInstance() {
        return mInstance;
    }

    public List<User> getUsers(){

        if (users==null){
            users=new ArrayList<>();
        }else {
            users.clear();
        }

        JSONArray usersJson=aCache.getAsJSONArray("users");
        if (usersJson!=null){
            for(int i=0;i<usersJson.length();i++){
                User user=new Gson().fromJson(usersJson.optString(i), User.class);
                users.add(user);
            }
        }

        return users;
    }

    public void resetUser(User currentUser){
        for (User user:users){
            if(user.getName().equals(currentUser.getName())){
                user.setName(currentUser.getName());
                user.setLast_data(currentUser.getLast_data());
                user.setCurrent_data(currentUser.getCurrent_data());
                user.setAir_fee(currentUser.getAir_fee());
                user.setShould_pay(currentUser.getShould_pay());
            }
        }

        aCache.put("users",new Gson().toJson(users));
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

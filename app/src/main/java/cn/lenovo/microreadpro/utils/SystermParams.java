package cn.lenovo.microreadpro.utils;

import android.content.Context;
import android.os.Environment;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.CArticalBean;
import cn.lenovo.microreadpro.model.CStoriedBean;
import cn.lenovo.microreadpro.model.UserBean;
import cn.lenovo.microreadpro.net.AppClientGB2312;
import cn.lenovo.microreadpro.net.AppInfoApiStores;
import cn.lenovo.microreadpro.net.ArticalMWApiStores;
import cn.lenovo.microreadpro.net.GameApiStores;
import cn.lenovo.microreadpro.net.NewsApiStores;
import cn.lenovo.microreadpro.net.AppClient;

/**
 * Created by lenovo on 2016/12/1.
 */

public class SystermParams {

    public static NewsApiStores nwesApiStores= AppClient.getRetrofit(NewsApiStores.API_ZHIHU_URL).create(NewsApiStores.class);
    public static GameApiStores gameApiStores=AppClient.getRetrofitForUTF_8HTML(GameApiStores.API_GAME_URL).create(GameApiStores.class);
    public static ArticalMWApiStores articalMWApiStores= AppClientGB2312.getRetrofitForGB2312HTML(ArticalMWApiStores.API_MEIWEN_URL).create(ArticalMWApiStores.class);
    public static AppInfoApiStores appInfoApiStores= AppClient.getRetrofitForUTF_8HTML(AppInfoApiStores.API_APPINFO_URL).create(AppInfoApiStores.class);

    public static String FILE_PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+"/MicroView/";
    public static String COLLECTION_STATUS="read";
    public static final String action="currentUser";
    public static final String APK_DOWMLOAD_URL="http://a.app.qq.com/o/simple.jsp?pkgname=cn.lenovo.microreadpro";
    public static final String NEWS_SHARE_URL="https://daily.zhihu.com/story/";
    public static final String DEFAULT_IMAGE_URL="http://api.open.qq.com/tfs/show_img.php?appid=1105904055&uuid=app1105904055_40_40%7C1048576%7C1486616319.7352";
    public static final String APP_CACHE_DIRNAME="/MRWebCache";

    /**
     * 获取用户列表
     */
    public static List<UserBean> getUsers(){

        MyApplication mApp= (MyApplication) MyApplication.getInstance();
        List<UserBean> users=new ArrayList<>();
        if (users==null){
            users=new ArrayList<>();
        }else {
            users.clear();
        }

        JSONArray usersJson=mApp.aCache.getAsJSONArray("users");
        if (usersJson!=null){

            try {
                for(int i=0;i<usersJson.length();i++){
                    UserBean user=new Gson().fromJson(usersJson.optString(i), UserBean.class);
                    users.add(user);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return users;
    }

    /**
     * 获取收藏列表 news
     * @return
     */
    public static List<CStoriedBean> getTotalNewsCollection() {

        MyApplication mApp= (MyApplication) MyApplication.getInstance();
        List<CStoriedBean> collection=new ArrayList<>();

        JSONArray collectionJson=mApp.aCache.getAsJSONArray("colletcion");
        if (collectionJson!=null){
            for(int i=0;i<collectionJson.length();i++){
                CStoriedBean mStories=new Gson().fromJson(collectionJson.optString(i), CStoriedBean.class);
//                if (mStories.getBelongs().getUsername().equals(mApp.currentUser.getUsername())){
                collection.add(mStories);
//                }
            }
        }
        return collection;
    }

    /**
     * 获取收藏列表 artical
     * @return
     */
    public static List<CArticalBean> getTotalArticalCollection() {

        MyApplication mApp= (MyApplication) MyApplication.getInstance();
        List<CArticalBean> collection=new ArrayList<>();

        JSONArray collectionJson=mApp.aCache.getAsJSONArray("colletcion_artical");
        if (collectionJson!=null){
            for(int i=0;i<collectionJson.length();i++){
                CArticalBean mArticals=new Gson().fromJson(collectionJson.optString(i), CArticalBean.class);
//                if (mStories.getBelongs().getUsername().equals(mApp.currentUser.getUsername())){
                collection.add(mArticals);
//                }
            }
        }
        return collection;
    }

    /**
     * 清理缓存
     */
    public static void clearWebViewCache(Context context){
        try {
            context.deleteDatabase("webview.db");
            context.deleteDatabase("webviewCache.db");
        }catch (Exception e){
            e.printStackTrace();
        }

        File appCacheDir = new File(context.getFilesDir().getAbsolutePath()+APP_CACHE_DIRNAME);
        File webviewCacheDir=new File(context.getCacheDir().getAbsolutePath()+"/webviewCache");

        if (appCacheDir.exists()){
            delateFile(appCacheDir);
        }

        if (webviewCacheDir.exists()){
            if (delateFile(webviewCacheDir)){
                EventUtil.showToast(context,"清理缓存成功！");
            }else {
                EventUtil.showToast(context,"清理缓存失败！");
            }
        }
    }

    /**
     * 递归删除file
     * @param file
     */
    public static boolean delateFile(File file){

        if (file.exists()){
            if (file.isFile()){
                file.delete();
            }else if (file.isDirectory()){
                File files[]=file.listFiles();
                for (int i=0;i<files.length;i++){
                    delateFile(files[i]);
                }
            }
            file.delete();
            return true;
        }else {
            LogUtil.i("File doesn't exit");
            return false;
        }
    }
}

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
import cn.lenovo.microreadpro.model.MCollection;
import cn.lenovo.microreadpro.model.UserBean;
import cn.lenovo.microreadpro.net.AppClientGB2312;
import cn.lenovo.microreadpro.net.AppInfoApiStores;
import cn.lenovo.microreadpro.net.ArticalMWApiStores;
import cn.lenovo.microreadpro.net.GameApiStores;
import cn.lenovo.microreadpro.net.MicroReadApiStores;
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
    public static MicroReadApiStores microReadApiStores =AppClient.getRetrofit(MicroReadApiStores.API_MICROREAD_URL).create(MicroReadApiStores.class);

    public static String FILE_PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+"/MicroView/";
    public static String COLLECTION_STATUS="read";
    public static final String action="currentUser";
    public static final String APK_DOWMLOAD_URL="http://a.app.qq.com/o/simple.jsp?pkgname=cn.lenovo.microreadpro";
    public static final String NEWS_SHARE_URL="https://daily.zhihu.com/story/";
    public static final String DEFAULT_IMAGE_URL="http://api.open.qq.com/tfs/show_img.php?appid=1105904055&uuid=app1105904055_40_40%7C1048576%7C1486616319.7352";
    public static final String APP_CACHE_DIRNAME="/MRWebCache";

    /**
     * 获取收藏列表
     * @return
     */
    public static List<MCollection.Artical> getTotalCollection(String atid) {

        MyApplication mApp= (MyApplication) MyApplication.getInstance();
        List<MCollection.Artical> collection=new ArrayList<>();

        JSONArray collectionJson=mApp.aCache.getAsJSONArray(atid);
        if (collectionJson!=null){
            for(int i=0;i<collectionJson.length();i++){
                MCollection.Artical mStories=new Gson().fromJson(collectionJson.optString(i), MCollection.Artical.class);
//                if (mStories.getBelongs().getUsername().equals(mApp.currentUser.getUsername())){
                collection.add(mStories);
//                }
            }
        }
        return collection;
    }

}

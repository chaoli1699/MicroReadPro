package cn.lenovo.microreadpro.presenter;

import android.app.Application;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.MVersion;
import cn.lenovo.microreadpro.net.ApiCallback;
import cn.lenovo.microreadpro.utils.LogUtil;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.SplashView;

/**
 * Created by Aaron on 2017/3/20.
 */

public class SplashPresenter extends BasePresenter<SplashView> {

    private MyApplication mApp;

    public SplashPresenter(SplashView view){
        attachView(view);
        mApp= (MyApplication) MyApplication.getInstance();
    }

    public void checkVersion(){

//        addSubscription(SystermParams.appInfoApiStores.getHtmlForUpdate("cn.lenovo.microreadpro"), new ApiCallback<String>() {
//            @Override
//            public void onSuccess(String model) {
//                Document doc= Jsoup.parse(model);
//                Elements apkInfo=doc.getElementsByClass("det-othinfo-data");
//                String latestVersion=apkInfo.get(0).childNode(0)+"";
//                latestVersion=latestVersion.replace("\n","");
////                String apkAuthor=apkInfo.get(2).childNode(0)+"";
//                LogUtil.i("latestVersion",latestVersion);
//                String localVersion=mApp.getVersion();
//                LogUtil.i("localVersion",localVersion);
//
//                if (!latestVersion.equals(localVersion)){
//                    mApp.isLatest=false;
//                    view.isLatestVersion(false);
//                }else {
//                    view.isLatestVersion(true);
//                }
//
//            }
//
//            @Override
//            public void onFailure(String msg) {
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        });

        addSubscription(SystermParams.microReadApiStores.check_version("latest"), new ApiCallback<MVersion>() {
            @Override
            public void onSuccess(MVersion model) {
                if (model.getCode().equals("0")){
                    int local_code=mApp.getVersionCode();
                    int latest_code=model.getVersion().getVersion_code();

                    if (latest_code>local_code){
                        mApp.isLatest=false;
                    }
                }

                view.isLatestVersion(mApp.isLatest);
            }

            @Override
            public void onFailure(String msg) {
                view.isLatestVersion(mApp.isLatest);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}

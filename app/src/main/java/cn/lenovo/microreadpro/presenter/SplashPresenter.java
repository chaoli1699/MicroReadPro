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

                view.isLatestVersion(mApp.isLatest, model.getVersion().getStart_path());
            }

            @Override
            public void onFailure(String msg) {
                view.isLatestVersion(mApp.isLatest,"");
            }

            @Override
            public void onFinish() {

            }
        });
    }
}

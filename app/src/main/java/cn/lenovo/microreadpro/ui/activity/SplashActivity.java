package cn.lenovo.microreadpro.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.base.MRActivity;
import cn.lenovo.microreadpro.presenter.SplashPresenter;
import cn.lenovo.microreadpro.ui.fragment.UpdateVersionDialogFragment;
import cn.lenovo.microreadpro.view.SplashView;

public class SplashActivity extends MRActivity<SplashPresenter> implements SplashView{

    private static final int SPLASH_TIME=1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mPresenter.checkVersion();
    }

    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenter(this);
    }

    private Thread timeThread=new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(SPLASH_TIME);
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    });

    @Override
    public void isLatestVersion(boolean isLatest) {

        if (!isLatest){
            new UpdateVersionDialogFragment().show(getSupportFragmentManager(),"update_version_dialog");
        }else {
            timeThread.start();
        }
    }

}

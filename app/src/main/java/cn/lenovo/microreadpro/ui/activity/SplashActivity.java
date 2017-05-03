package cn.lenovo.microreadpro.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.base.MRActivity;
import cn.lenovo.microreadpro.net.MicroReadApiStores;
import cn.lenovo.microreadpro.presenter.SplashPresenter;
import cn.lenovo.microreadpro.ui.fragment.UpdateVersionDialogFragment;
import cn.lenovo.microreadpro.view.SplashView;
import butterknife.Bind;
import com.bumptech.glide.Glide;

public class SplashActivity extends MRActivity<SplashPresenter> implements SplashView{

    @Bind(R.id.activity_splash_start)
    ImageView start;
    private static final int SPLASH_TIME=3000;

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
    public void isLatestVersion(boolean isLatest, String start_page) {

        if (!isLatest){
            new UpdateVersionDialogFragment().show(getSupportFragmentManager(),"update_version_dialog");
        }else {
            String url=MicroReadApiStores.API_MICROREAD_URL+start_page;
            Glide.with(this).load(url).into(start);
            timeThread.start();
        }
    }

}

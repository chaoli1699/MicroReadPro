package cn.lenovo.microreadpro.ui.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;

import butterknife.Bind;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.base.MRActivity;
import cn.lenovo.microreadpro.model.GameBean;
import cn.lenovo.microreadpro.presenter.GameDetailPresenter;
import cn.lenovo.microreadpro.ui.fragment.ExitWhileGameingDialogFragment;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.GameDetailView;

/**
 * Created by Aaron on 2017/2/26.
 */

public class GameDetailActivity extends MRActivity<GameDetailPresenter> implements GameDetailView {

//    @Bind(R.id.appbar_layout)
//    AppBarLayout barLayout;
//    @Bind(R.id.toolbar_for_c_u)
//    Toolbar toolbar;
    @Bind(R.id.activity_game_webview)
    WebView webView;

    private GameBean mGameBean;
    private WebSettings settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        initView();
    }

    private void initView(){
        mGameBean= (GameBean) getIntent().getSerializableExtra("game");
//        toolbar.setTitle(mGameBean.getName());
//        toolbar.setTitleTextColor(Color.WHITE);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        //开启app缓存
        String cacheDirPath=getFilesDir().getAbsolutePath()+ SystermParams.APP_CACHE_DIRNAME;
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCachePath(cacheDirPath);
        settings.setAppCacheEnabled(true);
        settings.setAppCacheMaxSize(8*1024*1024);

        //开启Dom缓存
        settings.setDomStorageEnabled(true);

        //开启数据库缓存
        settings.setDatabasePath(cacheDirPath);
        settings.setDatabaseEnabled(true);

        if (Build.VERSION.SDK_INT>=19){
            settings.setLoadsImagesAutomatically(true);
        }else {
            settings.setLoadsImagesAutomatically(false);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                showProgressDialog();
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                dismissProgressDialog();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
                if (!webView.getSettings().getLoadsImagesAutomatically()){
                    webView.getSettings().setLoadsImagesAutomatically(true);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                webView.loadDataWithBaseURL(null,"网络故障，请检查您的网络连接状况后再尝试！","text/html","utf-8",null);
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//                return super.onJsAlert(view, url, message, result);
                result.confirm();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });

        webView.loadUrl(mGameBean.getUrl());
    }

    @BusReceiver
    public void onStringEvent(String event) {
        // handle your event
        if (event.equals("exit")){
            finish();
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        new ExitWhileGameingDialogFragment().show(getSupportFragmentManager(),"GameDetailActivity");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.activity_detail_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return true;
    }

    @Override
    protected GameDetailPresenter createPresenter() {
        return new GameDetailPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
        Bus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
        Bus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.clearCache(true);
//        clearWebViewCache();
    }
}

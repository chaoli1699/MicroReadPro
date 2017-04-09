package cn.lenovo.microreadpro.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.base.MRActivity;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.ArticalBox;
import cn.lenovo.microreadpro.model.MCollection;
import cn.lenovo.microreadpro.model.ShareBean;
import cn.lenovo.microreadpro.net.ArticalMWApiStores;
import cn.lenovo.microreadpro.presenter.ArticalDetailPresenter;
import cn.lenovo.microreadpro.ui.fragment.EditTextSizeDialogFragment;
import cn.lenovo.microreadpro.ui.fragment.ExitWhileSpeakingDialogFragment;
import cn.lenovo.microreadpro.ui.service.TTSpeakerService;
import cn.lenovo.microreadpro.utils.EventUtil;
import cn.lenovo.microreadpro.utils.LogUtil;
import cn.lenovo.microreadpro.utils.ShareUtil;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.ArticalDetailView;

public class ArticalDetailActivity extends MRActivity<ArticalDetailPresenter> implements ArticalDetailView
        ,View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.web_view)
    WebView webView;
    @Bind(R.id.iv_web_img)
    ImageView webImg;
    @Bind(R.id.tv_img_title)
    TextView imgTitle;
    @Bind(R.id.tv_img_source)
    TextView imgSource;
    @Bind(R.id.btn_essay_speak)
    Button essaySpeak;
    @Bind(R.id.iv_speak_vioce)
    ImageView speakerVoice;

    @Bind(R.id.scroll_view)
    NestedScrollView scrollView;
    @Bind(R.id.float_menu)
    FloatingActionsMenu float_menu;
    @Bind(R.id.resize_text)
    FloatingActionButton resize_text;
    @Bind(R.id.artical_comments)
    FloatingActionButton comments;

    private String str;
    private Intent serviceIntent;
    private int type=0;
    private MyApplication mApp;

    private AnimationDrawable AnimVoice;
    private ShareBean shareBean;

//    private CArticalBean mArticalBean;
    private MCollection.Artical mCArtical;
    private ArticalBox.Artical artical;
    private boolean isCollected=false;
    private boolean isSpeaking=false;

    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(action)) {
                if (isSpeaking){
                    stopService(serviceIntent);
                    serviceIntent=null;
                }
            }
        }
    };

    private void initFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artical_detail);

        initView();
        mApp= (MyApplication) MyApplication.getInstance();

    }


    private void initView() {
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        artical = (ArticalBox.Artical) getIntent().getSerializableExtra("artical");
//        mPresenter.getArticalDetail(artical);

        AnimVoice= (AnimationDrawable) speakerVoice.getDrawable();
        AnimVoice.stop();

        essaySpeak.setOnClickListener(this);
        resize_text.setOnClickListener(this);
        comments.setOnClickListener(this);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (serviceIntent!=null) {
                    stopService(serviceIntent);
                    serviceIntent=null;
                }
                showProgressDialog();
                view.loadUrl(url);
                type=1;
                str=url;
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                dismissProgressDialog();
            }
        });

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (float_menu.isExpanded()){
                    float_menu.collapse();
                }

                if (scrollY>oldScrollY){
                    //up
                    float_menu.setVisibility(View.VISIBLE);
                }else {
                    //down
                    float_menu.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.btn_essay_speak:

                if (mApp.mTTSpeaker!=null){
                    if (serviceIntent!=null){
                        stopService(serviceIntent);
                        serviceIntent=null;
                    }

                    if (str!=null){
                        serviceIntent=new Intent(ArticalDetailActivity.this, TTSpeakerService.class);
                        serviceIntent.putExtra("type", type);
                        serviceIntent.putExtra("str", str);
                        startService(serviceIntent);
                    }
                }else {
                    EventUtil.showSnackbar(view,"播音员尚未准备好，请稍后再试");
                }

                break;
            case R.id.resize_text:
                new EditTextSizeDialogFragment().show(getSupportFragmentManager(),"edit_textSize");
                break;
            case R.id.artical_comments:
                Intent intent=new Intent(ArticalDetailActivity.this,CommentActivity.class);
                Bundle b=new Bundle();
                b.putSerializable("mcart",mCArtical);
                intent.putExtras(b);
                startActivity(intent);
                break;
        }
    }


    @BusReceiver
    public void onStringEvent(String event) {
        // handle your event
        if (event.equals("start")){
            isSpeaking=true;
            AnimVoice.start();
        }else if (event.equals("finish")||event.equals("stop")){
            isSpeaking=false;
            AnimVoice.stop();
        }else if (event.equals("exit")){
            finish();
        }else if (event.equals("resize")){
            if (float_menu.isExpanded()){
                float_menu.collapse();
            }
            if (mApp.isLogin){
//                mApp.getCurrentUser();
            }
            mPresenter.getArticalDetail(artical);
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (isSpeaking){
            new ExitWhileSpeakingDialogFragment().show(getSupportFragmentManager(),"exit_while_speaking_dialog");
        }else {
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_detail_menu,menu);
        mPresenter.getArticalDetail(artical);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if (id==android.R.id.home){
            onBackPressed();
        }else if (id==R.id.like){

            if (isCollected){
//                mPresenter.removeCollection(mArticalBean);
            }else {
                if (mCArtical!=null){
                    mPresenter.addCollection(mCArtical);
                }
            }
        }else if(id==R.id.share){
            if (shareBean!=null) {
                ShareUtil.showShare(this, shareBean);
            }
        }else {
            if (serviceIntent != null) {
                stopService(serviceIntent);
                serviceIntent = null;
            }

            if (id == R.id.speaker0) {
                mApp.mTTSpeaker.changeSpeaker(0, item.getTitle() + "");
            } else if (id == R.id.speaker1) {
                mApp.mTTSpeaker.changeSpeaker(1, item.getTitle() + "");
            } else if (id == R.id.speaker2) {
                mApp.mTTSpeaker.changeSpeaker(2, item.getTitle() + "");
            } else if (id == R.id.speaker3) {
                mApp.mTTSpeaker.changeSpeaker(3, item.getTitle() + "");
            }
        }

//        return super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    protected ArticalDetailPresenter createPresenter() {
        return new ArticalDetailPresenter(this);
    }

    private void setCollectStatus(int status){

        MenuItem item=toolbar.getMenu().findItem(R.id.like);
        LogUtil.d("id",item);
        if (status==0){
            isCollected=false;
            item.setIcon(R.mipmap.like);
        }else if (status==1){
            isCollected=true;
            item.setIcon(R.mipmap.liked);

        }
    }

    private void setCArticalBean(ArticalBox.Artical mArtical){
//        mArticalBean=new CArticalBean();
//        mArticalBean.setTitle(mArtical.getTitle());
//        mArticalBean.setAuthor("作者："+mArtical.getAuthor());
//        mArticalBean.setCol_time("收藏于"+TimeUtils.date2String(TimeUtils.getCurTimeDate()));
//        if (mArtical.getImagePath()!=null){
//            mArticalBean.setImagePath(mArtical.getImagePath());
//        }
//        mArticalBean.setDetailPath(artical.getDetailPath());
////        mArticalBean.setBelongs(mApp.currentUser);

        mCArtical=new MCollection.Artical();
        mCArtical.setTitle(mArtical.getTitle());
        mCArtical.setAuthor("作者："+mArtical.getAuthor());
        mCArtical.setSource("美文收藏");
        mCArtical.setAtid(-2);
        mCArtical.setImage_path((mArtical.getImagePath()==null)? "":mArtical.getImagePath());
        mCArtical.setDetail_path(artical.getDetailPath());
    }

    private void setShareBean(ArticalBox.Artical mArtical){
        shareBean=new ShareBean();
        shareBean.setTitle(mArtical.getTitle());
        shareBean.setText("向你推荐了文章：" + mArtical.getTitle() + "，点击查看->"+ ArticalMWApiStores.API_MEIWEN_URL+mArtical.getDetailPath());
        shareBean.setUrl(ArticalMWApiStores.API_MEIWEN_URL+mArtical.getDetailPath());
        if (mArtical.getImagePath()!=null){
            shareBean.setImage_url(ArticalMWApiStores.API_MEIWEN_URL+mArtical.getImagePath());
        }else {
            shareBean.setImage_url(SystermParams.DEFAULT_IMAGE_URL);
        }

    }

    @Override
    public void getArticalDetailSuccess(ArticalBox.Artical artical) {

        setCArticalBean(artical);
        setShareBean(artical);

        str=artical.getContent();
        webView.loadDataWithBaseURL(null, str, "text/html", "utf-8", null);

        imgTitle.setText(artical.getTitle());
        imgSource.setText("文/" + artical.getAuthor());
        Glide.with(this).load(ArticalMWApiStores.API_MEIWEN_URL+artical.getImagePath()).into(webImg);

        if (mApp.isLogin&&mPresenter.isCollected(mCArtical)){
            setCollectStatus(1);
        }

    }

    @Override
    public void getArticalDetailFail(String msg) {
        EventUtil.showToast(this,msg);
    }

    @Override
    public void addArticalCollectionSuccess() {
        setCollectStatus(1);
        EventUtil.showToast(ArticalDetailActivity.this,"加入收藏成功");
        Bus.getDefault().post("artical_changed");
    }

    @Override
    public void addArticalCollectionFail(String msg) {
        EventUtil.showToast(this,msg);
    }

    @Override
    public void removeArticalCollectionSuccess() {
        setCollectStatus(0);
        EventUtil.showToast(this,"已为您移除收藏！");
        Bus.getDefault().post("artical_changed");
    }

    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                if (serviceIntent!=null){
                    stopService(serviceIntent);
                    serviceIntent=null;
                }

                webView.goBack();//返回上一页面
                return true;
            } else {
//                finish();//退出程序
                if (!EventUtil.isFastDoubleClick()){
                    onBackPressed();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onResume() {
        super.onResume();
        initFilter();
        Bus.getDefault().register(this);
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
        Bus.getDefault().unregister(this);
        if (serviceIntent!=null){
            stopService(serviceIntent);
        }

    }


}

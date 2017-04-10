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

import java.util.ArrayList;

import butterknife.Bind;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.base.MRActivity;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.CStoriedBean;
import cn.lenovo.microreadpro.model.MCollection;
import cn.lenovo.microreadpro.model.NewsDetailEntity;
import cn.lenovo.microreadpro.model.ShareBean;
import cn.lenovo.microreadpro.presenter.NewsDetailPresenter;
import cn.lenovo.microreadpro.ui.fragment.EditTextSizeDialogFragment;
import cn.lenovo.microreadpro.ui.fragment.ExitWhileSpeakingDialogFragment;
import cn.lenovo.microreadpro.ui.service.TTSpeakerService;
import cn.lenovo.microreadpro.utils.EventUtil;
import cn.lenovo.microreadpro.utils.LogUtil;
import cn.lenovo.microreadpro.utils.ShareUtil;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.NewsDetailView;

public class NewsDetailActivity extends MRActivity<NewsDetailPresenter> implements NewsDetailView
        , View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private String id;
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
//    @Bind(R.id.resize_text)
//    FloatingActionButton resize_text;
    @Bind(R.id.news_comments)
    FloatingActionButton comments;

//    private CStoriedBean mStoriesBean;
    private MCollection.Artical mCArtical;
    private String str;
    private Intent serviceIntent;
    private int type=0;
    private MyApplication mApp;

    private AnimationDrawable AnimVoice;
    private ShareBean shareBean;
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
        setContentView(R.layout.activity_news_detail);

        initView();
        mApp= (MyApplication) MyApplication.getInstance();
    }

    private void initView() {
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id = getIntent().getStringExtra("id");
//        mPresenter.getNewsDetail(id);

        AnimVoice= (AnimationDrawable) speakerVoice.getDrawable();
        AnimVoice.stop();

        essaySpeak.setOnClickListener(this);
//        resize_text.setOnClickListener(this);
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
                        serviceIntent=new Intent(NewsDetailActivity.this, TTSpeakerService.class);
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
            case R.id.news_comments:
                Intent intent=new Intent(NewsDetailActivity.this,CommentActivity.class);
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
//            if (float_menu.isExpanded()){
//                float_menu.collapse();
//            }
//            if (mApp.isLogin){
////                mApp.getCurrentUser();
//            }
//            mPresenter.getNewsDetail(id);
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
        mPresenter.getNewsDetail(id);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if (id==android.R.id.home){
            onBackPressed();
        }else if (id==R.id.like){

            if (mApp.isLogin){
                if (isCollected){
//                mPresenter.removeCollection(mStoriesBean);
                }else {
                    if (mCArtical!=null){
                        mPresenter.addCollection(mCArtical);
                    }
                }
            }else {
                EventUtil.showToast(NewsDetailActivity.this,"请先登录再添加收藏");
            }

        }else if (id==R.id.share){
            if (shareBean!=null){
                ShareUtil.showShare(this,shareBean);
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
    protected NewsDetailPresenter createPresenter() {
        return new NewsDetailPresenter(this);
    }

    private void setCollectStatus(int status){

        MenuItem item=toolbar.getMenu().findItem(R.id.like);
        LogUtil.d("item",item);
        if (status==0){
            item.setIcon(R.mipmap.like);
            isCollected=false;
        }else if (status==1){
            item.setIcon(R.mipmap.liked);
            isCollected=true;
        }
    }

    private void setCStoriesBean(NewsDetailEntity newsDetailEntity){
//        mStoriesBean=new CStoriedBean();
//        ArrayList<String> images=new ArrayList<>();
//        images.add(newsDetailEntity.getImage());
//        mStoriesBean.setId(Integer.valueOf(newsDetailEntity.getId()));
//        mStoriesBean.setTitle(newsDetailEntity.getTitle());
//        mStoriesBean.setImages(images);
//        mStoriesBean.setGa_prefix(newsDetailEntity.getGa_prefix());
//        mStoriesBean.setType(Integer.valueOf(newsDetailEntity.getType()));
////        mStoriesBean.setBelongs(mApp.currentUser);

        mCArtical=new MCollection.Artical();
        mCArtical.setTitle(newsDetailEntity.getTitle());
        mCArtical.setAuthor(newsDetailEntity.getImage_source());
        mCArtical.setSource("知乎话题");
        mCArtical.setAtid(-1);
        mCArtical.setImage_path(newsDetailEntity.getImage());
        mCArtical.setDetail_path(id);
    }

    private void setShareBean(NewsDetailEntity newsDetailEntity){
        shareBean=new ShareBean();
        shareBean.setTitle(newsDetailEntity.getTitle());
        shareBean.setText("向你推荐了文章：" + newsDetailEntity.getTitle() + "，点击查看->" + SystermParams.NEWS_SHARE_URL + id);
        shareBean.setUrl(SystermParams.NEWS_SHARE_URL + id);
        shareBean.setImage_url(newsDetailEntity.getImage());
    }

    @Override
    public void getNewsDetailSuccess(NewsDetailEntity newsDetailEntity) {

        setCStoriesBean(newsDetailEntity);
        setShareBean(newsDetailEntity);

//        String head = "<head>\n" +
//                "\t<link rel=\"stylesheet\" href=\"" + newsDetailEntity.getCss()[0] + "\"/>\n" +
//                "</head>";
//        String img = "<div class=\"headline\">";
//        str = head + newsDetailEntity.getBody().replace(img, " ").replace("*","---");
        str=newsDetailEntity.getBody();

        webView.loadDataWithBaseURL(null, str , "text/html", "utf-8", null);

        imgTitle.setText(newsDetailEntity.getTitle());
        imgSource.setText("来自:" + newsDetailEntity.getImage_source());
        Glide.with(this).load(newsDetailEntity.getImage()).into(webImg);

        if (mApp.isLogin&&mPresenter.isCollected(mCArtical)){
            setCollectStatus(1);
        }

    }

    @Override
    public void getNewsDetailFail(String msg) {
        EventUtil.showToast(this,msg);
    }

    @Override
    public void addCollectionSuccess() {
        setCollectStatus(1);
        EventUtil.showToast(NewsDetailActivity.this,"加入收藏成功");
        Bus.getDefault().post("news_changed");
    }

    @Override
    public void addCollectionFail(String msg) {
        EventUtil.showToast(this,msg);
    }

    @Override
    public void removeCollectionSuccess() {
        setCollectStatus(0);
        EventUtil.showToast(this,"已为您移除收藏！");
        Bus.getDefault().post("news_changed");
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

package cn.lenovo.microreadpro.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.CommentRecyclerAdapter;
import cn.lenovo.microreadpro.base.MRActivity;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.MCollection;
import cn.lenovo.microreadpro.model.MComment;
import cn.lenovo.microreadpro.presenter.CommentPresenter;
import cn.lenovo.microreadpro.presenter.MomentPresenter;
import cn.lenovo.microreadpro.ui.fragment.SureToDelateDialogFragment;
import cn.lenovo.microreadpro.utils.EventUtil;
import cn.lenovo.microreadpro.view.CommentView;
import cn.lenovo.microreadpro.view.MomentView;

/**
 * Created by Aaron on 2017/4/9.
 */

public class MomentActivity extends MRActivity<MomentPresenter> implements MomentView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.toolbar_for_c_u)
    Toolbar toolbar;
    @Bind(R.id.activity_moment_recycleView)
    EasyRecyclerView mRecyclerView;
    @Bind(R.id.content)
    EditText content;
    @Bind(R.id.add_moment)
    Button add_moment;
    @Bind(R.id.moment_container)
    RelativeLayout container;

    private LinearLayoutManager mLinearLayoutManager;
    private CommentRecyclerAdapter mCommentRecyclerAdapter;
    private MyApplication mApp;
//    private List<MComment.Comment> comments=new ArrayList<>();
    private int acid=-1;
    private int aim_uid=-1;
    private String mom_type="moment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment);

        initView();
    }

    private void initView(){

        mom_type=getIntent().getStringExtra("mom_type");
        mApp= (MyApplication) MyApplication.getInstance();

        mLinearLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mCommentRecyclerAdapter =new CommentRecyclerAdapter(this, mom_type);
        mRecyclerView.setAdapter(mCommentRecyclerAdapter);

//        mNewsCollectionRecyclerAdapter.setMore(R.layout.view_more,this);
        mCommentRecyclerAdapter.setError(R.layout.view_error);
        mRecyclerView.setRefreshListener(this);
        mRecyclerView.setEmptyView(R.layout.view_empty);
//        mCommentRecyclerAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                if (container.getVisibility()==View.GONE){
//                    container.setVisibility(View.VISIBLE);
//                }
//                content.setText("@"+comments.get(position).getUsername()+": ");
//            }
//        });

        add_moment.setOnClickListener(this);
        if (mom_type.equals("moment")){
            toolbar.setTitle("时光轴");
            mPresenter.getMoments();
        }else if (mom_type.equals("pmoment")){
            toolbar.setTitle("我的时光");
            mPresenter.getPMomentOrTrash(mApp.currentUser.getUid(), 0);
        }else if (mom_type.equals("trash")){
            toolbar.setTitle("回收站");
            mPresenter.getPMomentOrTrash(mApp.currentUser.getUid(), 1);
        }
//        mPresenter.getMoments();

        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @BusReceiver
    public void onStringEvent(String event) {
        // handle your event
        if (event.contains("addc")){
            String[] msplit=event.split(",");
            acid=Integer.valueOf(msplit[1]);
            aim_uid=Integer.valueOf(msplit[2]);
            if (container.getVisibility()==View.GONE){
                container.setVisibility(View.VISIBLE);
            }
        }

        if (event.contains("mmtot")){
            acid=Integer.valueOf(event.substring(6));
//            mPresenter.removeMoment(acid);
            new SureToDelateDialogFragment().show(getSupportFragmentManager(),"SureToDelate");
        }

        if (event.equals("remove")){
            mPresenter.removeMoment(acid);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_moment_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        switch (id){
            case R.id.add_moment:
                acid=-1;
                if (container.getVisibility()==View.GONE){
                    container.setVisibility(View.VISIBLE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getMomentsSuccess(List<MComment.PComment> commentList) {

        if (container.getVisibility()==View.VISIBLE){
            content.setText("");
            container.setVisibility(View.GONE);
        }

        mCommentRecyclerAdapter.clear();
        mCommentRecyclerAdapter.addAll(commentList);
    }

    @Override
    public void getMomentsFail(String msg) {
//        EventUtil.showToast(this,msg);
    }

    @Override
    protected MomentPresenter createPresenter() {
        return new MomentPresenter(this);
    }

    @Override
    public void onRefresh() {
        if (mom_type.equals("moment")){
            mPresenter.getMoments();
        }else if (mom_type.equals("pmoment")){
            mPresenter.getPMomentOrTrash(mApp.currentUser.getUid(), 0);
        }else if (mom_type.equals("trash")){
            mPresenter.getPMomentOrTrash(mApp.currentUser.getUid(), 1);
        }
//        mPresenter.getMoments();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        Bus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_moment:

                if (mApp.isLogin){
                    String str=content.getText().toString().trim();
                    if (TextUtils.isEmpty(str)){
//                        content.setError("评论内容为空！");
                        EventUtil.showSnackbar(view,"评论内容不能为空");
                    }else {
                        EventUtil.closeInputKeyBoard(MomentActivity.this,content);
                        if (acid<0){
                            mPresenter.addMoment(str);
                        }else {
                            mPresenter.addChildcom(acid, aim_uid, str);
                        }
                    }
                }else {
                    EventUtil.showSnackbar(view,"请先登录再发表评论");
                }

                break;
        }
    }
}

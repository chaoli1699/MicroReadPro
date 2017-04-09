package cn.lenovo.microreadpro.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.CommentRecyclerAdapter;
import cn.lenovo.microreadpro.base.MRActivity;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.MCollection;
import cn.lenovo.microreadpro.model.MComment;
import cn.lenovo.microreadpro.presenter.CommentPresenter;
import cn.lenovo.microreadpro.utils.EventUtil;
import cn.lenovo.microreadpro.view.CommentView;

/**
 * Created by Aaron on 2017/4/9.
 */

public class CommentActivity extends MRActivity<CommentPresenter> implements CommentView, View.OnClickListener {

    @Bind(R.id.toolbar_for_c_u)
    Toolbar toolbar;
    @Bind(R.id.activity_comment_recycleView)
    EasyRecyclerView mRecyclerView;
    @Bind(R.id.content)
    EditText content;
    @Bind(R.id.add_comment)
    Button add_comment;
    @Bind(R.id.comment_container)
    RelativeLayout container;

    private MCollection.Artical mcart;
    private LinearLayoutManager mLinearLayoutManager;
    private CommentRecyclerAdapter mCommentRecyclerAdapter;
    private MyApplication mApp;
    private List<MComment.Comment> comments=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initView();
    }

    private void initView(){

        mcart = (MCollection.Artical) getIntent().getSerializableExtra("mcart");
        mApp= (MyApplication) MyApplication.getInstance();

        toolbar.setTitle(mcart.getTitle());
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLinearLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mCommentRecyclerAdapter =new CommentRecyclerAdapter(this);
        mRecyclerView.setAdapter(mCommentRecyclerAdapter);

//        mNewsCollectionRecyclerAdapter.setMore(R.layout.view_more,this);
        mCommentRecyclerAdapter.setError(R.layout.view_error);
//        mRecyclerView.setRefreshListener(this);
        mRecyclerView.setEmptyView(R.layout.view_empty);
        mCommentRecyclerAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (container.getVisibility()==View.GONE){
                    container.setVisibility(View.VISIBLE);
                }
                content.setText("@"+comments.get(position).getUsername()+": ");
            }
        });

        add_comment.setOnClickListener(this);
        mPresenter.getComments(mcart.getDetail_path());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_comment_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        switch (id){
            case R.id.add_comment:
                if (container.getVisibility()==View.GONE){
                    container.setVisibility(View.VISIBLE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getCommentsSuccess(List<MComment.Comment> commentList) {

        if (container.getVisibility()==View.VISIBLE){
            content.setText("");
            container.setVisibility(View.GONE);
        }

        comments.clear();
        comments=commentList;

        mCommentRecyclerAdapter.clear();
        mCommentRecyclerAdapter.addAll(commentList);
    }

    @Override
    public void getCommentsFail(String msg) {
        EventUtil.showToast(this,msg);
    }

    @Override
    protected CommentPresenter createPresenter() {
        return new CommentPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_comment:

                if (mApp.isLogin){
                    String str=content.getText().toString().trim();
                    if (TextUtils.isEmpty(str)){
                        content.setError("评论内容为空！");
                    }else {
                        mPresenter.addComment(mcart, str);
                    }
                }else {
                    EventUtil.showSnackbar(view,"请先登录再发表评论");
                }

                break;
        }
    }
}

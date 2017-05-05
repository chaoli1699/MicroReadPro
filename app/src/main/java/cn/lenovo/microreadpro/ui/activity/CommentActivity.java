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
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;
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
import cn.lenovo.microreadpro.ui.fragment.SureToDelateDialogFragment;
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
    @Bind(R.id.activity_comment_comCount)
    TextView com_ount;

    private MCollection.Artical mcart;
    private LinearLayoutManager mLinearLayoutManager;
    private CommentRecyclerAdapter mCommentRecyclerAdapter;
    private MyApplication mApp;
//    private List<MComment.Comment> comments=new ArrayList<>();
    private int acid=-1;
    private int aim_uid=-1;

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
        mCommentRecyclerAdapter =new CommentRecyclerAdapter(this, "comment");
        mRecyclerView.setAdapter(mCommentRecyclerAdapter);

//        mNewsCollectionRecyclerAdapter.setMore(R.layout.view_more,this);
        mCommentRecyclerAdapter.setError(R.layout.view_error);
//        mRecyclerView.setRefreshListener(this);
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

        add_comment.setOnClickListener(this);
        mPresenter.getComments(mcart.getDetail_path());
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
            mPresenter.removeComent(acid);
        }
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
                acid=-1;
                if (container.getVisibility()==View.GONE){
                    container.setVisibility(View.VISIBLE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getCommentsSuccess(int comCount, List<MComment.PComment> commentList) {

        com_ount.setText("最新评论("+comCount+")");

        if (container.getVisibility()==View.VISIBLE){
            content.setText("");
            container.setVisibility(View.GONE);
        }

//        comments.clear();
//        comments=commentList;

        mCommentRecyclerAdapter.clear();
        mCommentRecyclerAdapter.addAll(commentList);
    }

    @Override
    public void getCommentsFail(String msg) {
//        EventUtil.showToast(this,msg);
    }

    @Override
    protected CommentPresenter createPresenter() {
        return new CommentPresenter(this);
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
            case R.id.add_comment:

                if (mApp.isLogin){
                    String str=content.getText().toString().trim();
                    if (TextUtils.isEmpty(str)){
//                        content.setError("评论内容为空！");
                        EventUtil.showSnackbar(view,"评论内容不能为空");
                    }else {
                        EventUtil.closeInputKeyBoard(CommentActivity.this,content);
                        if (acid<0){
                            mPresenter.addComment(mcart, str);
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

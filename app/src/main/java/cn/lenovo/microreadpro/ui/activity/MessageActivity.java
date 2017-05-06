package cn.lenovo.microreadpro.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.MessageRecyclerAdapter;
import cn.lenovo.microreadpro.base.MRActivity;
import cn.lenovo.microreadpro.model.MMessage;
import cn.lenovo.microreadpro.presenter.MessagePresenter;
import cn.lenovo.microreadpro.view.MessageView;

/**
 * Created by Aaron on 2017/5/6.
 */

public class MessageActivity extends MRActivity<MessagePresenter> implements MessageView {

    @Bind(R.id.toolbar_for_c_u)
    Toolbar toolbar;
    @Bind(R.id.activity_message_recycleview)
    EasyRecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;
    private MessageRecyclerAdapter mMessageRecyclerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        initView();
    }

    private void initView(){

        toolbar.setTitle("消息");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLinearLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerAdapter=new MessageRecyclerAdapter(this);
        mRecyclerView.setAdapter(mMessageRecyclerAdapter);

//        mCollectionRecyclerAdapter.setMore(R.layout.view_more,this);
        mMessageRecyclerAdapter.setError(R.layout.view_error);
//        mRecyclerView.setRefreshListener(this);
        mRecyclerView.setEmptyView(R.layout.view_empty);

        mPresenter.getMessages();
    }

    @Override
    protected MessagePresenter createPresenter() {
        return new MessagePresenter(this);
    }

    @Override
    public void getMessageSuccess(List<MMessage.Message> messageList) {
        mMessageRecyclerAdapter.addAll(messageList);
    }

    @Override
    public void getMessageFail(String msg) {

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}

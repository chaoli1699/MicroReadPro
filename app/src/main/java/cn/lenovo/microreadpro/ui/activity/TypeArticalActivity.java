package cn.lenovo.microreadpro.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.ArticalBoxRecycleAdapter;
import cn.lenovo.microreadpro.base.MRActivity;
import cn.lenovo.microreadpro.model.ArticalBox;
import cn.lenovo.microreadpro.presenter.TypeArticalPresenter;
import cn.lenovo.microreadpro.utils.EventUtil;
import cn.lenovo.microreadpro.utils.LogUtil;
import cn.lenovo.microreadpro.view.TypeArticalView;

/**
 * Created by Aaron on 2017/3/6.
 */

public class TypeArticalActivity extends MRActivity<TypeArticalPresenter> implements TypeArticalView
        , SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener{

    @Bind(R.id.toolbar_for_c_u)
    Toolbar toolbar;
    @Bind(R.id.activity_type_artical_recycleView)
    EasyRecyclerView mEasyRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;
    private ArticalBoxRecycleAdapter mAarticalBoxRecycleAdapter;
    private ArticalBox articalBox;
    private List<ArticalBox> articalBoxList=new ArrayList<>();
    private String source;
    private List<String> mPagePaths=new ArrayList<>();
    private int mPagePath=1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_artical);

        initView();
    }

    private void initView(){

        articalBox= (ArticalBox) getIntent().getSerializableExtra("articalBox");
        source= (String) getIntent().getSerializableExtra("source");

        toolbar.setTitle(articalBox.getBox_title());
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLinearLayoutManager=new LinearLayoutManager(this);
        mEasyRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAarticalBoxRecycleAdapter=new ArticalBoxRecycleAdapter(this);
        mEasyRecyclerView.setAdapter(mAarticalBoxRecycleAdapter);
//        mAarticalBoxRecycleAdapter.setMore(R.layout.view_more,this);
        mAarticalBoxRecycleAdapter.setError(R.layout.view_error);
        mEasyRecyclerView.setRefreshListener(this);
        mEasyRecyclerView.setEmptyView(R.layout.view_empty);

        mAarticalBoxRecycleAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                if (articalBoxList.get(position).getBox_path()!=null){
                    Intent intent=new Intent(TypeArticalActivity.this, TypeArticalActivity.class);
                    Bundle b=new Bundle();
                    b.putString("source","typeArtical");
                    b.putSerializable("articalBox",articalBoxList.get(position));
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });

        if (source.equals("typeArtical")){
            mPresenter.getMWChildrenTypeArtical(articalBox.getBox_path());
        }else if (source.equals("main")){
            if (articalBox.getBox_path().equals("/lizhi/")){
                mPresenter.getMWChildrenTypeArtical(articalBox.getBox_path());
            }else {
                mPresenter.getMVTypeArticals(articalBox.getBox_path());
            }
        }
    }

    @Override
    protected TypeArticalPresenter createPresenter() {
        return new TypeArticalPresenter(this);
    }

    @Override
    public void getTypeArticalFail(String msg) {
        EventUtil.showToast(this,msg);
    }

    @Override
    public void getTypeAarticalMWSuccess(List<ArticalBox> articalBoxes,List<String> pagePaths) {

        if (pagePaths!=null){
            mPagePaths=pagePaths;
            mAarticalBoxRecycleAdapter.setMore(R.layout.view_more,this);
        }

        articalBoxList=articalBoxes;
        mAarticalBoxRecycleAdapter.addAll(articalBoxes);
    }

    @Override
    public void onRefresh() {
        mPagePath=1;
        mAarticalBoxRecycleAdapter.clear();
        if (articalBox.getBox_path().contains(".html")||articalBox.getBox_path().equals("/lizhi/")){
            mPresenter.getMWChildrenTypeArtical(articalBox.getBox_path());
        }else {
            mPresenter.getMVTypeArticals(articalBox.getBox_path());
        }
    }

    @Override
    public void onLoadMore() {
        if (mPagePath<mPagePaths.size()){
            String [] str=articalBox.getBox_path().split("/");
            mPresenter.getMWChildrenTypeArtical(str[1]+"/"+mPagePaths.get(mPagePath++));
        }else {
            mAarticalBoxRecycleAdapter.setNoMore(R.layout.view_nomore);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

package cn.lenovo.microreadpro.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.NewsRecycleAdapter;
import cn.lenovo.microreadpro.adapter.holder.TopStoriesViewHolder;
import cn.lenovo.microreadpro.base.MRFragment;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.NewsEntity;
import cn.lenovo.microreadpro.presenter.NewsPresenter;
import cn.lenovo.microreadpro.ui.activity.NewsDetailActivity;
import cn.lenovo.microreadpro.utils.EventUtil;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.NewsView;

/**
 * Created by lenovo on 2016/11/30.
 */

public class NewsFragment extends MRFragment<NewsPresenter> implements NewsView,
        RecyclerArrayAdapter.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener {

    @Bind(R.id.top_stories_banner)
    ConvenientBanner mTopStoriesBanner;
    @Bind(R.id.fg_news_recycleView)
    EasyRecyclerView mRecyclerView;
//    @Bind(R.id.toolbar_news_toolbar)
//    Toolbar toolbar;
    @Bind(R.id.float_menu)
    FloatingActionsMenu float_menu;
    @Bind(R.id.user_center)
    FloatingActionButton user_center;
    @Bind(R.id.artical)
    FloatingActionButton artical;
    @Bind(R.id.game)
    FloatingActionButton game;
    @Bind(R.id.my_collection)
    FloatingActionButton my_collection;
    @Bind(R.id.about_app)
    FloatingActionButton about_app;
    @Bind(R.id.share)
    FloatingActionButton share;

    private View rootView;

    private LinearLayoutManager mLinearLayoutManager;
    private NewsRecycleAdapter mNewsRecycleAdapter;
    private NewsEntity mNewsEntity;
    private List<NewsEntity.StoriesBean> storiesBeen=new ArrayList<>();
    private static DrawerLayout drawer;
    private static final int CHANGE_TIME=5000;

    private MyApplication mApp;

    public interface NewsFragmentExtra{
        DrawerLayout getDrawer();
    }

    public static void setDrawer(NewsFragmentExtra newsFragmentExtra){
        if (drawer==null){
            drawer=newsFragmentExtra.getDrawer();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        if (rootView==null){
            rootView=inflater.inflate(R.layout.fragment_news,container,false);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        mApp= (MyApplication) MyApplication.getInstance();
    }


    @Override
    protected NewsPresenter createPresenter() {
        return new NewsPresenter(NewsFragment.this);
    }

    private void initView(){

//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        setHasOptionsMenu(true);
//
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        mLinearLayoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mNewsRecycleAdapter =new NewsRecycleAdapter(getActivity());
        mRecyclerView.setAdapter(mNewsRecycleAdapter);

        mNewsRecycleAdapter.setMore(R.layout.view_more,this);
        mNewsRecycleAdapter.setError(R.layout.view_error);
        mRecyclerView.setRefreshListener(this);
        mRecyclerView.setEmptyView(R.layout.view_empty);
        mNewsRecycleAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                toastShow(storiesBeen.get(position).getTitle());
                Intent intent=new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("id",storiesBeen.get(position).getId()+"");
                startActivity(intent);
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    if (float_menu.isExpanded()){
                        float_menu.collapse();
                    }
                }
            });
        }

        user_center.setOnClickListener(this);
        artical.setOnClickListener(this);
        game.setOnClickListener(this);
        my_collection.setOnClickListener(this);
        about_app.setOnClickListener(this);
        share.setOnClickListener(this);

        mPresenter.getLatestNews();

    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        String page="news";
        if (id==R.id.user_center) {
            if (mApp.isLogin){
                page="center";
            }else {
                page="none";
            }
        } else if (id==R.id.artical){
            page="artical";
        } else if (id==R.id.game){
            page="game";
        } else if (id == R.id.my_collection){
            if (mApp.isLogin){
                page="collection";
            }else {
                page="none";
            }
        } else if (id == R.id.about_app){
            page="app";
        } else if (id == R.id.share) {
            page="share";
        }

        if (float_menu.isExpanded()){
            float_menu.collapse();
        }

        Intent intent=new Intent(SystermParams.action);
        intent.putExtra("page",page);
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void getNewsSuccess(final NewsEntity newsEntity) {

        mNewsEntity=newsEntity;
        storiesBeen.addAll(newsEntity.getStories());
        mNewsRecycleAdapter.addAll(newsEntity.getStories());

        if (newsEntity.getTop_stories()!=null){
            mTopStoriesBanner.setPages(new CBViewHolderCreator() {
                @Override
                public Object createHolder() {
                    return new TopStoriesViewHolder();
                }
            },newsEntity.getTop_stories())
                    .setDrawingCacheEnabled(true);

            mTopStoriesBanner.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent=new Intent(getActivity(), NewsDetailActivity.class);
                    intent.putExtra("id",newsEntity.getTop_stories().get(position).getId()+"");
                    startActivity(intent);
                }
            });

            if (!mTopStoriesBanner.isTurning()){
                mTopStoriesBanner.startTurning(CHANGE_TIME);
            }
        }
    }

    @Override
    public void getNewsFail(String msg) {
        EventUtil.showToast(getActivity(),msg);
    }

    @Override
    public void onLoadMore() {
//        mBanner.stopTurning();
        mPresenter.getBeforeNews(mNewsEntity.getDate());
    }

    @Override
    public void onRefresh() {
        mTopStoriesBanner.stopTurning();
        storiesBeen.clear();
        mNewsEntity=null;
        mNewsRecycleAdapter.clear();
        mPresenter.getLatestNews();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mTopStoriesBanner.isTurning()){
            mTopStoriesBanner.startTurning(CHANGE_TIME);
        }

        MobclickAgent.onPageStart("NewsFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        mTopStoriesBanner.stopTurning();
        MobclickAgent.onPageEnd("NewsFragment");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

package cn.lenovo.microreadpro.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.NewsCollectionRecyclerAdapter;
import cn.lenovo.microreadpro.base.MRFragment;
import cn.lenovo.microreadpro.model.CStoriedBean;
import cn.lenovo.microreadpro.presenter.NewsCollectionPresenter;
import cn.lenovo.microreadpro.ui.activity.NewsDetailActivity;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.NewsCollectionView;

/**
 * Created by Aaron on 2016/12/31.
 */

public class NewsCollectionFragment extends MRFragment<NewsCollectionPresenter> implements NewsCollectionView {
    @Bind(R.id.fg_collection_recycleView)
    EasyRecyclerView mRecyclerView;

    private View rootView;
    private GridLayoutManager mGridLayoutManager;
    private NewsCollectionRecyclerAdapter mNewsCollectionRecyclerAdapter;
    private List<CStoriedBean> collectedStoriesBeans;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        if (rootView==null){
            rootView=inflater.inflate(R.layout.fragment_collection,container,false);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @BusReceiver
    public void onStringEvent(String event) {
        // handle your event
        if (event.equals("news_changed")){
            mNewsCollectionRecyclerAdapter.clear();
            mPresenter.getCollection();
        }
    }

    private void initView(){
        mGridLayoutManager=new GridLayoutManager(getActivity(),3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mNewsCollectionRecyclerAdapter =new NewsCollectionRecyclerAdapter(getActivity());
        mRecyclerView.setAdapter(mNewsCollectionRecyclerAdapter);

//        mNewsCollectionRecyclerAdapter.setMore(R.layout.view_more,this);
        mNewsCollectionRecyclerAdapter.setError(R.layout.view_error);
//        mRecyclerView.setRefreshListener(this);
        mRecyclerView.setEmptyView(R.layout.view_empty);
        mNewsCollectionRecyclerAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                if (SystermParams.COLLECTION_STATUS.equals("read")){
                    Intent intent=new Intent(getActivity(), NewsDetailActivity.class);
                    intent.putExtra("id", collectedStoriesBeans.get(position).getId()+"");
                    startActivity(intent);
                }
            }
        });

        mPresenter.getCollection();
    }

    @Override
    protected NewsCollectionPresenter createPresenter() {
        return new NewsCollectionPresenter(NewsCollectionFragment.this);
    }

    @Override
    public void getCollectionSuccess(List<CStoriedBean> storiesBeans) {

        collectedStoriesBeans =storiesBeans;
        mNewsCollectionRecyclerAdapter.addAll(storiesBeans);
    }

    @Override
    public void getCollectionFail(String msg) {
//        EventUtil.showToast(getActivity(),msg);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bus.getDefault().register(this);
        MobclickAgent.onPageStart("NewsCollectionFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("NewsCollectionFragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        Bus.getDefault().unregister(this);
    }
}

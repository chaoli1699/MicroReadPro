package cn.lenovo.microreadpro.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
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
import cn.lenovo.microreadpro.adapter.ArticalCollectionRecyclerAdapter;
import cn.lenovo.microreadpro.base.MRFragment;
import cn.lenovo.microreadpro.model.CArticalBean;
import cn.lenovo.microreadpro.presenter.ArticalCollectionPresenter;
import cn.lenovo.microreadpro.ui.activity.ArticalDetailActivity;
import cn.lenovo.microreadpro.view.ArticalCollectionView;

/**
 * Created by Aaron on 2016/12/31.
 */

public class ArticalCollectionFragment extends MRFragment<ArticalCollectionPresenter> implements ArticalCollectionView {

    @Bind(R.id.fg_collection_recycleView)
    EasyRecyclerView mRecyclerView;

    private View rootView;
    private LinearLayoutManager mLinearLayoutManager;
    private ArticalCollectionRecyclerAdapter mArticalCollectionRecyclerAdapter;
    private List<CArticalBean> collectedArticalBeans;

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

    @Override
    protected ArticalCollectionPresenter createPresenter() {
        return new ArticalCollectionPresenter(ArticalCollectionFragment.this);
    }

    @BusReceiver
    public void onStringEvent(String event) {
        // handle your event
        if (event.equals("artical_changed")){
            mArticalCollectionRecyclerAdapter.clear();
            mPresenter.getCollection();
        }
    }

    private void initView(){
        mLinearLayoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mArticalCollectionRecyclerAdapter =new ArticalCollectionRecyclerAdapter(getActivity());
        mRecyclerView.setAdapter(mArticalCollectionRecyclerAdapter);

//        mNewsCollectionRecyclerAdapter.setMore(R.layout.view_more,this);
        mArticalCollectionRecyclerAdapter.setError(R.layout.view_error);
//        mRecyclerView.setRefreshListener(this);
        mRecyclerView.setEmptyView(R.layout.view_empty);
        mArticalCollectionRecyclerAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getActivity(), ArticalDetailActivity.class);
                Bundle b=new Bundle();
                b.putSerializable("artical",collectedArticalBeans.get(position));
                intent.putExtras(b);
                getActivity().startActivity(intent);
            }
        });

        mPresenter.getCollection();
    }

    @Override
    public void getCollectionSuccess(List<CArticalBean> carticalBean) {

        collectedArticalBeans =carticalBean;
        mArticalCollectionRecyclerAdapter.addAll(carticalBean);
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

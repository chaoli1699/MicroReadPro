package cn.lenovo.microreadpro.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.ArticalBoxRecycleAdapter;
import cn.lenovo.microreadpro.base.MRFragment;
import cn.lenovo.microreadpro.model.ArticalBox;
import cn.lenovo.microreadpro.presenter.ArticalPresenter;
import cn.lenovo.microreadpro.ui.activity.TypeArticalActivity;
import cn.lenovo.microreadpro.utils.EventUtil;
import cn.lenovo.microreadpro.view.ArticalView;

/**
 * Created by Aaron on 2017/2/6.
 */

public class ArticalFragment extends MRFragment<ArticalPresenter> implements ArticalView, SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.toolbar_for_c_u)
    Toolbar toolbar;
    @Bind(R.id.fg_artical_recycleView)
    EasyRecyclerView mRecyclerView;

    private View rootView;
    private static DrawerLayout drawer;

    private LinearLayoutManager mLinearLayoutManager;
    private ArticalBoxRecycleAdapter mAarticalBoxRecycleAdapter;
    private List<ArticalBox> articalBoxList=new ArrayList<>();

    public interface ArticalFragmentExtra{
        DrawerLayout getDrawer();
    }

    public static void setDrawer(ArticalFragment.ArticalFragmentExtra articalFragmentExtra){
        if (drawer==null){
            drawer=articalFragmentExtra.getDrawer();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        if (rootView==null){
            rootView=inflater.inflate(R.layout.fragment_artical,container,false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView(){

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        toolbar.setTitle(getString(R.string.artical));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mLinearLayoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAarticalBoxRecycleAdapter=new ArticalBoxRecycleAdapter(getActivity());
        mRecyclerView.setAdapter(mAarticalBoxRecycleAdapter);
        mAarticalBoxRecycleAdapter.setError(R.layout.view_error);
        mRecyclerView.setRefreshListener(this);
        mRecyclerView.setEmptyView(R.layout.view_empty);

        mAarticalBoxRecycleAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                if (articalBoxList.get(position).getBox_path()!=null){
                    Intent intent=new Intent(getActivity(), TypeArticalActivity.class);
                    Bundle b=new Bundle();
                    b.putString("source","main");
                    b.putSerializable("articalBox",articalBoxList.get(position));
                    intent.putExtras(b);
                    getActivity().startActivity(intent);
                }
            }
        });

        mPresenter.getMVArticals("/");
    }

    @Override
    protected ArticalPresenter createPresenter() {
        return new ArticalPresenter(ArticalFragment.this);
    }

    @Override
    public void onRefresh() {
        articalBoxList.clear();
        mAarticalBoxRecycleAdapter.clear();
        mPresenter.getMVArticals("/");
    }

    @Override
    public void getArticalFail(String msg) {
        EventUtil.showToast(getActivity(),msg);
    }

    @Override
    public void getAarticalMWSuccess(List<ArticalBox> articalBoxes) {
        articalBoxList=articalBoxes;
        mAarticalBoxRecycleAdapter.addAll(articalBoxes);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ArticalFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ArticalFragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

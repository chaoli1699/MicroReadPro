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
import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;
import com.umeng.analytics.MobclickAgent;
import com.zaaach.citypicker.CityPickerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.UserCenterRecycleAdapter;
import cn.lenovo.microreadpro.base.MRFragment;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.ListBean;
import cn.lenovo.microreadpro.model.MUFeture;
import cn.lenovo.microreadpro.presenter.UserCenterPresenter;
import cn.lenovo.microreadpro.ui.activity.CollectionActivity;
import cn.lenovo.microreadpro.ui.activity.MainActivity;
import cn.lenovo.microreadpro.ui.activity.MomentActivity;
import cn.lenovo.microreadpro.ui.activity.UserInfoActivity;
import cn.lenovo.microreadpro.utils.EventUtil;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.UserCenterView;

/**
 * Created by Aaron on 2017/1/2.
 */

public class UserCenterFragment extends MRFragment<UserCenterPresenter> implements UserCenterView, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.toolbar_for_c_u)
    Toolbar toolbar;
    @Bind(R.id.fg_usercenter_recycleView)
    EasyRecyclerView mRecyclerView;

    private View rootView;
    private static DrawerLayout drawer;
    private LinearLayoutManager mLinearLayoutManager;
    private UserCenterRecycleAdapter mUserCenterRecycleAdapter;
    public static final int REQUEST_CODE_PICK_CITY = 0;
    private List<MUFeture.UFeture> fetures=new ArrayList<>();

    public interface UserCenterFragmentExtra{
        DrawerLayout getDrawer();
    }

    public static void setDrawer(UserCenterFragmentExtra userCenterFragmentExtra){
        if (drawer==null){
            drawer=userCenterFragmentExtra.getDrawer();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        if (rootView==null){
            rootView=inflater.inflate(R.layout.fragment_usercenter,container,false);
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
        toolbar.setTitle(getString(R.string.user_center));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mLinearLayoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mUserCenterRecycleAdapter=new UserCenterRecycleAdapter(getActivity());
        mRecyclerView.setAdapter(mUserCenterRecycleAdapter);

//        mCollectionRecyclerAdapter.setMore(R.layout.view_more,this);
        mUserCenterRecycleAdapter.setError(R.layout.view_error);
        mRecyclerView.setRefreshListener(this);
        mRecyclerView.setEmptyView(R.layout.view_empty);
        mUserCenterRecycleAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (fetures.get(position).getUfid()){
                    case 1:
                        Intent intent=new Intent(getActivity(), UserInfoActivity.class);
                        intent.putExtra("head_path", fetures.get(position).getHead_path());
                        startActivity(intent);
                        break;
                    case 2:
                        //启动
                        startActivity(new Intent(getActivity(),MomentActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(),CollectionActivity.class));
                        break;
                }
            }
        });

        mPresenter.getUFetures();
    }

    @Override
    protected UserCenterPresenter createPresenter() {
        return new UserCenterPresenter(this);
    }

    @Override
    public void getUFeturesSuccess(List<MUFeture.UFeture> fetureList) {
        fetures.addAll(fetureList);
        mUserCenterRecycleAdapter.addAll(fetureList);
    }

    @Override
    public void onRefresh() {
        mUserCenterRecycleAdapter.clear();
        mPresenter.getUFetures();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("UserCenterFragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("UserCenterFragment");
    }
}

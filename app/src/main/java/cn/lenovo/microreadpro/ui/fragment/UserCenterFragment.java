package cn.lenovo.microreadpro.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
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
import com.zaaach.citypicker.CityPickerActivity;

import java.util.List;

import butterknife.Bind;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.UserCenterRecycleAdapter;
import cn.lenovo.microreadpro.base.MRFragment;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.ListBean;
import cn.lenovo.microreadpro.presenter.UserCenterPresenter;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.UserCenterView;

/**
 * Created by Aaron on 2017/1/2.
 */

public class UserCenterFragment extends MRFragment<UserCenterPresenter> implements UserCenterView{

    @Bind(R.id.toolbar_for_c_u)
    Toolbar toolbar;
    @Bind(R.id.fg_usercenter_recycleView)
    EasyRecyclerView mRecyclerView;

    private View rootView;
    private static DrawerLayout drawer;
    private LinearLayoutManager mLinearLayoutManager;
    private UserCenterRecycleAdapter mUserCenterRecycleAdapter;
    private MyApplication mApp;
    public static final int REQUEST_CODE_PICK_CITY = 0;

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
        mApp= (MyApplication) MyApplication.getInstance();

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
//        mRecyclerView.setRefreshListener(this);
        mRecyclerView.setEmptyView(R.layout.view_empty);
        mUserCenterRecycleAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position){
                    case 1:
                        new EditSexDialogFragment().show(getFragmentManager(),"edit_sex_fragment");
                        break;
                    case 2:
                        //启动
                        getActivity().startActivityForResult(new Intent(getActivity(), CityPickerActivity.class),
                                REQUEST_CODE_PICK_CITY);
                        break;
                    case 3:
                        new EditSignDialogFragment().show(getFragmentManager(),"edit_sign_fragment");
                        break;
                    case 5:
                        mApp.currentUser.setLoginStatus(0);
                        Intent intent=new Intent(SystermParams.action);
                        intent.putExtra("user","logout");
                        getActivity().sendBroadcast(intent);
                        break;
                }

            }
        });

        mPresenter.getUserInfo();

    }

    @Override
    protected UserCenterPresenter createPresenter() {
        return new UserCenterPresenter(this);
    }

    @Override
    public void getUserInfoSuccess(List<ListBean> listItems) {
        mUserCenterRecycleAdapter.addAll(listItems);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("UserCenterFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("UserCenterFragment");
    }
}

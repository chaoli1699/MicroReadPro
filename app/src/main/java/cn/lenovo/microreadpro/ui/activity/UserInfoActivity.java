package cn.lenovo.microreadpro.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;
import com.umeng.analytics.MobclickAgent;
import com.zaaach.citypicker.CityPickerActivity;

import java.util.List;

import butterknife.Bind;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.UserCenterRecycleAdapter;
import cn.lenovo.microreadpro.base.MRActivity;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.MUFeture;
import cn.lenovo.microreadpro.presenter.UserInfoPresenter;
import cn.lenovo.microreadpro.ui.fragment.EditSexDialogFragment;
import cn.lenovo.microreadpro.ui.fragment.EditSignDialogFragment;
import cn.lenovo.microreadpro.utils.EventUtil;
import cn.lenovo.microreadpro.utils.SystermParams;

import cn.lenovo.microreadpro.view.UserInfoView;

/**
 * Created by Aaron on 2017/4/29.
 */

public class UserInfoActivity extends MRActivity<UserInfoPresenter> implements UserInfoView {

    @Bind(R.id.toolbar_for_c_u)
    Toolbar toolbar;
    @Bind(R.id.fg_usercenter_recycleView)
    EasyRecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;
    private UserCenterRecycleAdapter mUserCenterRecycleAdapter;
    private MyApplication mApp;
    public static final int REQUEST_CODE_PICK_CITY = 0;
    private String head_path;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_usercenter);

        initView();
    }

    private void initView(){

        mApp= (MyApplication) MyApplication.getInstance();
        head_path=getIntent().getStringExtra("head_path")
        ;
        toolbar.setTitle("个人信息");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLinearLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mUserCenterRecycleAdapter=new UserCenterRecycleAdapter(this);
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
                        new EditSexDialogFragment().show(getSupportFragmentManager(),"edit_sex_fragment");
                        break;
                    case 2:
                        //启动
                        startActivityForResult(new Intent(UserInfoActivity.this, CityPickerActivity.class),
                                REQUEST_CODE_PICK_CITY);
                        break;
                    case 3:
                        new EditSignDialogFragment().show(getSupportFragmentManager(),"edit_sign_fragment");
                        break;
                    case 4:
                        Intent intent=new Intent(SystermParams.action);
                        intent.putExtra("user","logout");
                        sendBroadcast(intent);
                        finish();
                        break;
                }

            }
        });

        mPresenter.getUserInfo(head_path);
    }

    @BusReceiver
    public void onStringEvent(String event) {
        // handle your event
        if (event.equals("chansex")){
            mPresenter.chan_sex();
        }else if (event.equals("chancity")){
            mPresenter.chan_city();
        }else if (event.equals("chansign")){
            mPresenter.chan_sign();
        }
    }


    //重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK){
            if (data != null){
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
//                resultTV.setText("当前选择：" + city);
                mApp.currentUser.setDistrict(city);
                Bus.getDefault().post("chancity");
            }
        }
    }

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter(this);
    }

    @Override
    public void getUserInfoSuccess(List<MUFeture.UFeture> fetureList) {
        mUserCenterRecycleAdapter.clear();
        mUserCenterRecycleAdapter.addAll(fetureList);
    }

    @Override
    public void chanInfoSuccess() {
//        Intent intent=new Intent(SystermParams.action);
//        intent.putExtra("user","change");
//        sendBroadcast(intent);
        mPresenter.getUserInfo(head_path);
    }

    @Override
    public void chanInfoFail(String msg) {
        EventUtil.showToast(this,msg);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bus.getDefault().register(this);
        MobclickAgent.onResume(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Bus.getDefault().unregister(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}

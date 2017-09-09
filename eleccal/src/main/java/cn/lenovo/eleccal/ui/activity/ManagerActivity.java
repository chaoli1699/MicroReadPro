package cn.lenovo.eleccal.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;

import java.util.List;

import butterknife.Bind;
import cn.lenovo.eleccal.R;
import cn.lenovo.eleccal.adapter.ManagerRecycleAdapter;
import cn.lenovo.eleccal.base.MRActivity;
import cn.lenovo.eleccal.base.MyApplication;
import cn.lenovo.eleccal.model.User;
import cn.lenovo.eleccal.presenter.ManagerPresenter;
import cn.lenovo.eleccal.ui.fragment.RemoveUserDialogFragment;
import cn.lenovo.eleccal.utils.EventUtil;
import cn.lenovo.eleccal.view.ManagerView;

public class ManagerActivity extends MRActivity<ManagerPresenter> implements ManagerView,View.OnClickListener {

    @Bind(R.id.toolbar_normal)
    Toolbar mToolbar;
    @Bind(R.id.manager_recycle)
    EasyRecyclerView mRecyclerView;
    @Bind(R.id.manager_insert_container)
    LinearLayout container;
    @Bind(R.id.manager_input_user)
    AppCompatEditText inputUser;
    @Bind(R.id.manager_complate)
    AppCompatButton complate;

    private ManagerRecycleAdapter mManagerRecycleAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<User> users;
    private User removeUser;

    private int input_type;
    //private MyApplication mApp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        initView();
    }

    private void initView(){
        //mApp= (MyApplication) MyApplication.getInstance();

        mToolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLinearLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mManagerRecycleAdapter =new ManagerRecycleAdapter(this);
        mRecyclerView.setAdapter(mManagerRecycleAdapter);

//        mManagerRecycleAdapter.setMore(R.layout.view_more,this);
        mManagerRecycleAdapter.setError(R.layout.view_error);
//        mRecyclerView.setRefreshListener(this);
        mRecyclerView.setEmptyView(R.layout.view_empty);
        mManagerRecycleAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent();
                intent.setClass(ManagerActivity.this,MainActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("user",users.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mManagerRecycleAdapter.setOnItemLongClickListener(new RecyclerArrayAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position) {
                removeUser=users.get(position);
                new RemoveUserDialogFragment().show(getSupportFragmentManager(),"remove_user_dialog");
                return false;
            }
        });

        complate.setOnClickListener(this);

        mPresenter.getUsers();
    }

    @BusReceiver
    public void onStringEvent(String event) {
        // handle your event
        if (event.equals("positive")){
            mPresenter.removeUser(removeUser);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_manager_menu,menu);
//        return super.onCreateOptionsMenu(menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        inputUser.setText("");
        if (id==R.id.insert){
            input_type=0;
            //inputUser.setHint("输入用户名");
            //inputUser.setInputType(InputType.TYPE_CLASS_TEXT);
            //container.setVisibility(View.VISIBLE);
        }else if (id==R.id.calculate){
            input_type=1;
            //inputUser.setHint("输入总电费");
            //inputUser.setInputType(InputType.TYPE_CLASS_NUMBER);
            //container.setVisibility(View.VISIBLE);
        }else if (id==R.id.setting){
            input_type=2;
            //inputUser.setHint("输入用户数量上限");
            //inputUser.setInputType(InputType.TYPE_CLASS_NUMBER);
            //container.setVisibility(View.VISIBLE);
        }
        Intent intent=new Intent(ManagerActivity.this,InputActivity.class);
        intent.putExtra("type",input_type);
        startActivityForResult(intent,input_type);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==0){
            switch (requestCode){
                case 0:
                    User user=new User();
                    user.setName(data.getStringExtra("result"));
                    mPresenter.inseretUser(user);
                    break;
                case 1:
                    mPresenter.calculate(Float.valueOf(data.getStringExtra("result")));
                    break;
                case 2:
                    mPresenter.resetUserMax(data.getStringExtra("result"));
                    break;
            }
        }

    }

    @Override
    protected ManagerPresenter createPresenter() {
        return new ManagerPresenter(this);
    }

    @Override
    public void getUsersSuccess(List<User> users) {
        this.users=users;
        mManagerRecycleAdapter.clear();
        mManagerRecycleAdapter.addAll(users);
    }

    @Override
    public void insertUserSuccess() {
        EventUtil.showToast(this,"新增用户成功");
        container.setVisibility(View.GONE);
        inputUser.setText("");
        mPresenter.getUsers();
    }

    @Override
    public void removeUserSuccess() {
        EventUtil.showToast(this,"移除用户成功");
        mPresenter.getUsers();
    }

    @Override
    public void calculaeSuccess() {
        EventUtil.showToast(this,"应付电费计算成功!");
        container.setVisibility(View.GONE);
        mPresenter.getUsers();
    }

    @Override
    public void resetUserMacSuccess() {
        EventUtil.showToast(this,"重置用户上限成功！");
        container.setVisibility(View.GONE);
    }

    @Override
    public void getUsersFail(String msg) {
        EventUtil.showToast(this,msg);
    }

    @Override
    public void insertUserFail(String msg) {
        EventUtil.showToast(this,msg);
    }

    @Override
    public void removeUserFail(String msg) {
        EventUtil.showToast(this,msg);
    }

    @Override
    public void calculateFail(String msg) {
        EventUtil.showToast(this,msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if (id==R.id.manager_complate){
            String string=inputUser.getText().toString();

            if (input_type==0){
                if (string.length()<1){
                    inputUser.setError("用户名为空");
                }else {
                    User user=new User();
                    user.setName(string);
                    mPresenter.inseretUser(user);
                }
            }else if (input_type==1){
                if (string.length()<1){
                    inputUser.setError("总电费为空");
                }else {
                    mPresenter.calculate(Float.valueOf(string));
                }
            }else if (input_type==2){
                if (string.length()<1){
                    inputUser.setError("用户上限为空");
                }else {
                    mPresenter.resetUserMax(string);
                }
            }
        }
    }
}

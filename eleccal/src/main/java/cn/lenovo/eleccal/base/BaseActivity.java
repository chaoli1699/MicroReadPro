package cn.lenovo.eleccal.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Call;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lenovo on 2016/11/28.
 */

public class BaseActivity extends AppCompatActivity {

    private Activity mActivity;
    private CompositeSubscription mCompositeSubscription;//Rxjava
    private List<Call> calls;//okHttp
    public ProgressDialog progressDialog;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        ButterKnife.bind(this);
        mActivity=this;
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);

        ButterKnife.bind(this);
        mActivity=this;
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);

        ButterKnife.bind(this);
        mActivity=this;
    }

    @Override
    protected void onDestroy() {

        callCancel();
        onUnsubscribe();
        super.onDestroy();

    }

    public void addCalls(Call call){
        if (call==null){
            calls=new ArrayList<>();
        }

        calls.add(call);
    }

    private void callCancel(){
        if (calls!=null&&calls.size()>0){
            for (Call call : calls){
                if (!call.isCanceled()){
                    call.cancel();
                }
            }
            calls.clear();
        }
    }

    public void addSubscription(Subscription subscription){
        if (mCompositeSubscription==null){
            mCompositeSubscription=new CompositeSubscription();
        }

        mCompositeSubscription.add(subscription);
    }

    public void onUnsubscribe(){

        //unregist rxjava, avoiding OOM.
        if (mCompositeSubscription!=null&&mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }

//    public Toolbar initToolBar(String title){
//
//        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar_normal);
//        if (toolbar!=null){
//            setSupportActionBar(toolbar);
////            toolbar_title.setText(title);
//            toolbar.setTitle(title);
//        }
//        ActionBar actionBar=getSupportActionBar();
//        if (actionBar!=null){
//            actionBar.setDisplayHomeAsUpEnabled(false);
//        }
//
//        return toolbar;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();//back
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    public void toastShow(int resId){
//        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
//    }
//
//    public void toastShow(String resId){
//        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
//    }

    public ProgressDialog showProgressDialog(){
        progressDialog=new ProgressDialog(mActivity);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        return progressDialog;
    }

    public ProgressDialog showProgressDialog(CharSequence message){
        progressDialog=new ProgressDialog(mActivity);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    public void dismissProgressDialog(){
        if (progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}

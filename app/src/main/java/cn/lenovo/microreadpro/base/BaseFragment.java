package cn.lenovo.microreadpro.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by lenovo on 2016/11/28.
 */

public class BaseFragment extends Fragment {

    public Activity mActivity;
    private CompositeSubscription mCompositeSubscription;
    public ProgressDialog progressDialog;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        mActivity=getActivity();

    }

    @Override
    public void onDestroy() {
        onUnsubscribe();
        super.onDestroy();
    }

    public void addSubscription(Subscription subscription){

        if (mCompositeSubscription==null){
            mCompositeSubscription=new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    private void onUnsubscribe(){

        if (mCompositeSubscription!=null&&mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }

//    public Toolbar initToolBar(View view, String title){
//
//        Toolbar toolbar= (Toolbar) view.findViewById(R.id.toolbar_normal);
////        toolbar_title.setText(title);
//        toolbar.setTitle(title);
//        return toolbar;
//    }

//    public void toastShow(int resId) {
//        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
//    }
//
//    public void toastShow(String resId) {
//        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
//    }

    public ProgressDialog showProgressDialog() {
        if(progressDialog==null){
            progressDialog = new ProgressDialog(mActivity);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }
        return progressDialog;
    }

    public ProgressDialog showProgressDialog(CharSequence message) {
        if (progressDialog==null){
            progressDialog = new ProgressDialog(mActivity);
            progressDialog.setMessage(message);
            progressDialog.show();
        }
        return progressDialog;
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            // progressDialog.hide();会导致android.view.WindowLeaked
            progressDialog.dismiss();
        }
    }
}

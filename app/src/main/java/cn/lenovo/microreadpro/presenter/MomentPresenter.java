package cn.lenovo.microreadpro.presenter;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.MComment;
import cn.lenovo.microreadpro.net.ApiCallback;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.MomentView;

/**
 * Created by Aaron on 2017/4/9.
 */

public class MomentPresenter extends BasePresenter<MomentView> {

    private MyApplication mApp;

    public MomentPresenter(MomentView view){
        attachView(view);
        mApp= (MyApplication) MyApplication.getInstance();
    }

    public void getMoments(){

        view.showLoading();
        addSubscription(SystermParams.microReadApiStores.get_moments("querym"), new ApiCallback<MComment>() {
            @Override
            public void onSuccess(MComment model) {
                if (model.getCode().equals("0")){
                    view.getMomentsSuccess(model.getComments());
                }else {
                    view.getMomentsFail(model.getInfo());
                }
                view.hideLoading();
            }

            @Override
            public void onFailure(String msg) {
                view.getMomentsFail(msg);
            }

            @Override
            public void onFinish() {
                view.hideLoading();
            }
        });
    }

    public void addMoment(String comment){

        addSubscription(SystermParams.microReadApiStores.add_moment("addm", mApp.currentUser.getUid(), comment), new ApiCallback<MComment>() {
            @Override
            public void onSuccess(MComment model) {
                if (model.getCode().equals("0")){
                    view.getMomentsSuccess(model.getComments());
                }else {
                    view.getMomentsFail(model.getInfo());
                }
            }

            @Override
            public void onFailure(String msg) {
                view.getMomentsFail(msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    public void addChildcom(int acid,String comment){

        addSubscription(SystermParams.microReadApiStores.add_childcom("addc", mApp.currentUser.getUid(), acid, comment), new ApiCallback<MComment>() {
            @Override
            public void onSuccess(MComment model) {
                if (model.getCode().equals("0")){
                    view.getMomentsSuccess(model.getComments());
                }else {
                    view.getMomentsFail(model.getInfo());
                }
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    public void removeMoment(int acid){
        addSubscription(SystermParams.microReadApiStores.remove_moment("mmtot", acid , 1), new ApiCallback<MComment>() {
            @Override
            public void onSuccess(MComment model) {
                if (model.getCode().equals("0")||model.getCode().equals("10010")){
                    view.getMomentsSuccess(model.getComments());
                }else {
                    view.getMomentsFail(model.getInfo());
                }
            }

            @Override
            public void onFailure(String msg) {
                view.getMomentsFail(msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}

package cn.lenovo.microreadpro.presenter;

import com.google.gson.Gson;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.MCollection;
import cn.lenovo.microreadpro.model.MComment;
import cn.lenovo.microreadpro.net.ApiCallback;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.CommentView;

/**
 * Created by Aaron on 2017/4/9.
 */

public class CommentPresenter extends BasePresenter<CommentView> {

    private MyApplication mApp;

    public CommentPresenter(CommentView view){
        attachView(view);
        mApp= (MyApplication) MyApplication.getInstance();
    }

    public void getComments(String detail_path){

        view.showLoading();
        addSubscription(SystermParams.microReadApiStores.get_comments("query", detail_path), new ApiCallback<MComment>() {
            @Override
            public void onSuccess(MComment model) {
                if (model.getCode().equals("0")){
                    view.getCommentsSuccess(model.getCom_count(), model.getComments());
                }else {
                    view.getCommentsFail(model.getInfo());
                }
                view.hideLoading();
            }

            @Override
            public void onFailure(String msg) {
                view.getCommentsFail(msg);
            }

            @Override
            public void onFinish() {
                view.hideLoading();
            }
        });
    }

    public void addComment(MCollection.Artical artical,String comment){

        String artJson=new Gson().toJson(artical);
        addSubscription(SystermParams.microReadApiStores.add_comment("add", mApp.currentUser.getUid(), artJson, comment), new ApiCallback<MComment>() {
            @Override
            public void onSuccess(MComment model) {
                if (model.getCode().equals("0")){
                    view.getCommentsSuccess(model.getCom_count(), model.getComments());
                }else {
                    view.getCommentsFail(model.getInfo());
                }
            }

            @Override
            public void onFailure(String msg) {
                view.getCommentsFail(msg);
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
                    view.getCommentsSuccess(model.getCom_count(), model.getComments());
                }else {
                    view.getCommentsFail(model.getInfo());
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
                    view.getCommentsSuccess(model.getCom_count(),model.getComments());
                }else {
                    view.getCommentsFail(model.getInfo());
                }
            }

            @Override
            public void onFailure(String msg) {
                view.getCommentsFail(msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}

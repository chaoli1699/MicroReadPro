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

        addSubscription(SystermParams.microReadApiStores.get_comments("query", detail_path), new ApiCallback<MComment>() {
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
}

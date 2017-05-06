package cn.lenovo.microreadpro.presenter;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.MMessage;
import cn.lenovo.microreadpro.net.ApiCallback;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.MessageView;

/**
 * Created by Aaron on 2017/5/5.
 */

public class MessagePresenter extends BasePresenter<MessageView> {

    private MyApplication mApp;

    public MessagePresenter(MessageView view){
        attachView(view);
        mApp= (MyApplication) MyApplication.getInstance();

    }

    public void getMessages(){
        view.showLoading();
        addSubscription(SystermParams.microReadApiStores.get_messages("query", mApp.currentUser.getUid()), new ApiCallback<MMessage>() {
            @Override
            public void onSuccess(MMessage model) {

                view.hideLoading();
                if (model.getCode().equals("0")){
                    view.getMessageSuccess(model.getMessages());
                }

            }

            @Override
            public void onFailure(String msg) {
                view.hideLoading();
                view.getMessageFail(msg);
            }

            @Override
            public void onFinish() {
                view.hideLoading();
            }
        });
    }
}

package cn.lenovo.microreadpro.presenter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.CArticalBean;
import cn.lenovo.microreadpro.model.MCollection;
import cn.lenovo.microreadpro.net.ApiCallback;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.ArticalCollectionView;

/**
 * Created by Aaron on 2016/12/31.
 */

public class ArticalCollectionPresenter extends BasePresenter<ArticalCollectionView> {

    private MyApplication mApp;
    private List<CArticalBean> collection;
    private List<CArticalBean> uCollection;

    public ArticalCollectionPresenter(ArticalCollectionView view){
        attachView(view);
        mApp= (MyApplication) MyApplication.getInstance();
    }

    /**
     * 获取收藏列表
     * @return
     */
    public void getCollection() {

////        if (collection==null){
//            collection= SystermParams.getTotalArticalCollection();
////        }
//
//        if (uCollection==null){
//            uCollection=new ArrayList<>();
//        }else {
//            uCollection.clear();
//        }
//
//        if (collection.size()>0){
//            for (CArticalBean s:collection){
//                if (s.getBelongs().getUsername().equals(mApp.currentUser.getUsername())){
//                    uCollection.add(s);
//                }
//            }
//            view.getCollectionSuccess(uCollection);
//        }else {
//            view.getCollectionFail("您还未收藏任何文章");
//        }

        addSubscription(SystermParams.microReadApiStores.query_collection("query", mApp.currentUser.getUid(), -2), new ApiCallback<MCollection>() {
            @Override
            public void onSuccess(MCollection model) {

                if (model.getCode().equals("0")){
                    String collectionJson=new Gson().toJson(model.getCollections());
                    mApp.aCache.put("artical",collectionJson);
//                    view.getCollectionSuccess(model.getCollections());
                }else {
                    view.getCollectionFail(model.getInfo());
                }


            }

            @Override
            public void onFailure(String msg) {
                view.getCollectionFail(msg);
            }

            @Override
            public void onFinish() {

            }
        });

        view.getCollectionSuccess(SystermParams.getTotalCollection("artical"));
    }

    /**
     * 移除所选收藏
     * @param artical
     */
    public void removeCollection(String artical){

        addSubscription(SystermParams.microReadApiStores.remove_collection("remove", mApp.currentUser.getUid(), artical), new ApiCallback<MCollection>() {
            @Override
            public void onSuccess(MCollection model) {
                if (model.getCode().equals("0")){
                    String collectionJson=new Gson().toJson(model.getCollections());
                    mApp.aCache.put("artical",collectionJson);
                }else {
                    view.getCollectionFail(model.getInfo());
                }
                view.getCollectionSuccess(model.getCollections());
//                view.getCollectionSuccess(SystermParams.getTotalCollection("artical"));
            }

            @Override
            public void onFailure(String msg) {
                view.getCollectionFail(msg);
            }

            @Override
            public void onFinish() {

            }
        });

//        view.getCollectionSuccess(SystermParams.getTotalCollection("artical"));
    }
}

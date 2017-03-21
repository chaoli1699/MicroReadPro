package cn.lenovo.microreadpro.presenter;

import java.util.ArrayList;
import java.util.List;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.CArticalBean;
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

//        if (collection==null){
            collection= SystermParams.getTotalArticalCollection();
//        }

        if (uCollection==null){
            uCollection=new ArrayList<>();
        }else {
            uCollection.clear();
        }

        if (collection.size()>0){
            for (CArticalBean s:collection){
                if (s.getBelongs().getUsername().equals(mApp.currentUser.getUsername())){
                    uCollection.add(s);
                }
            }
            view.getCollectionSuccess(uCollection);
        }else {
            view.getCollectionFail("您还未收藏任何文章");
        }
    }

//    /**
//     * 移除所选收藏
//     * @param removeS
//     */
//    public void removeCollection(List<CArticalBean> removeS){
//
//        for(CArticalBean rs: removeS){
//            for (int i=0;i<collection.size();i++){
//                 CArticalBean cs=collection.get(i);
//                if (rs.getUrl()==cs.getUrl()
//                        && rs.getBelongs().getUsername().equals(cs.getBelongs().getUsername())){
//                    collection.remove(i);
//                }
//            }
//        }
//
//        mApp.aCache.put("colletcion_artical",new Gson().toJson(collection));
////        view.removeCollectionSuccess();
//    }
}

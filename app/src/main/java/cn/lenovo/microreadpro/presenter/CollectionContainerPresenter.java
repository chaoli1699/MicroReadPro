package cn.lenovo.microreadpro.presenter;

import com.google.gson.Gson;

import java.util.List;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.CArticalBean;
import cn.lenovo.microreadpro.model.CStoriedBean;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.CollectionContainerView;

/**
 * Created by Aaron on 2017/2/11.
 */

public class CollectionContainerPresenter extends BasePresenter<CollectionContainerView> {

    private MyApplication mApp;
    private List<CStoriedBean> collection;
    private List<CArticalBean> collection_art;

    public CollectionContainerPresenter(CollectionContainerView view){
        attachView(view);
    }

    public void removeStorieBeans(List<CStoriedBean> removeS){

        collection= SystermParams.getTotalNewsCollection();

        for(CStoriedBean rs: removeS){
            for (int i=0;i<collection.size();i++){
                CStoriedBean cs=collection.get(i);
                if (rs.getId()==cs.getId()
                        && rs.getBelongs().getUsername().equals(cs.getBelongs().getUsername())){
                    collection.remove(i);
                }
            }
        }

        mApp.aCache.put("colletcion",new Gson().toJson(collection));
        view.removeNewsCollectionSuccess();

    }

    public void removeArticalBeans(List<CArticalBean> removeS){

        collection_art=SystermParams.getTotalArticalCollection();

        for(CArticalBean rs: removeS){
            for (int i=0;i<collection_art.size();i++){
                CArticalBean cs=collection_art.get(i);
                if (rs.getDetailPath()==cs.getDetailPath()
                        && rs.getBelongs().getUsername().equals(cs.getBelongs().getUsername())){
                    collection_art.remove(i);
                }
            }
        }

        mApp.aCache.put("colletcion_artical",new Gson().toJson(collection));
        view.removeArticalCollectionSuccess();
    }
}

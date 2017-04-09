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

    public CollectionContainerPresenter(CollectionContainerView view){
        attachView(view);
    }


}

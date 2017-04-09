package cn.lenovo.microreadpro.view;

import java.util.List;
import java.util.Set;

import cn.lenovo.microreadpro.base.BaseView;
import cn.lenovo.microreadpro.model.CStoriedBean;
import cn.lenovo.microreadpro.model.MCollection;
import cn.lenovo.microreadpro.model.NewsEntity;

/**
 * Created by Aaron on 2016/12/31.
 */

public interface NewsCollectionView extends BaseView {

    void getCollectionSuccess(List<MCollection.Artical> mCArtical);
    void getCollectionFail(String msg);
//    void removeCollectionSuccess();
}

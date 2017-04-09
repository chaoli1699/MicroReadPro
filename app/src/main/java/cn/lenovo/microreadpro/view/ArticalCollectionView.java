package cn.lenovo.microreadpro.view;

import java.util.List;

import cn.lenovo.microreadpro.base.BaseView;
import cn.lenovo.microreadpro.model.CArticalBean;
import cn.lenovo.microreadpro.model.CStoriedBean;
import cn.lenovo.microreadpro.model.MCollection;

/**
 * Created by Aaron on 2016/12/31.
 */

public interface ArticalCollectionView extends BaseView {

    void getCollectionSuccess(List<MCollection.Artical> articalList);
    void getCollectionFail(String msg);
//    void removeCollectionSuccess(List<MCollection.Artical> articalList);
//    void removeCollectionFail(String msg);
}

package cn.lenovo.microreadpro.view;

import cn.lenovo.microreadpro.base.BaseView;
import cn.lenovo.microreadpro.model.ArticalBox;

/**
 * Created by Aaron on 2017/2/7.
 */

public interface ArticalDetailView extends BaseView{
    void getArticalDetailSuccess(ArticalBox.Artical artical);
    void getArticalDetailFail(String msg);
    void addArticalCollectionSuccess();
    void addArticalCollectionFail(String msg);
    void removeArticalCollectionSuccess();
}

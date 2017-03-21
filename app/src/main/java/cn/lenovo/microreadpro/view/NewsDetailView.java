package cn.lenovo.microreadpro.view;

import cn.lenovo.microreadpro.base.BaseView;
import cn.lenovo.microreadpro.model.NewsDetailEntity;

/**
 * Created by lenovo on 2016/12/1.
 */

public interface NewsDetailView extends BaseView {

    void getNewsDetailSuccess(NewsDetailEntity newsDetailEntity);

    void getNewsDetailFail(String msg);

    void addCollectionSuccess();

    void addCollectionFail(String msg);

    void removeCollectionSuccess();

}

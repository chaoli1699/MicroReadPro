package cn.lenovo.microreadpro.view;

import cn.lenovo.microreadpro.base.BaseView;
import cn.lenovo.microreadpro.model.NewsEntity;

/**
 * Created by lenovo on 2016/11/30.
 */

public interface NewsView extends BaseView {

    void getNewsSuccess(NewsEntity newsEntity);

    void getNewsFail(String msg);
}

package cn.lenovo.microreadpro.view;

import java.util.List;

import cn.lenovo.microreadpro.base.BaseView;
import cn.lenovo.microreadpro.model.ArticalBox;

/**
 * Created by Aaron on 2017/2/6.
 */

public interface ArticalView extends BaseView{

    void getArticalFail(String msg);
    void getAarticalMWSuccess(List<ArticalBox> articalBoxes);
}

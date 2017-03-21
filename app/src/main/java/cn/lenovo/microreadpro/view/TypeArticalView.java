package cn.lenovo.microreadpro.view;

import java.util.List;

import cn.lenovo.microreadpro.base.BaseView;
import cn.lenovo.microreadpro.model.ArticalBox;

/**
 * Created by Aaron on 2017/2/6.
 */

public interface TypeArticalView extends BaseView{

    void getTypeArticalFail(String msg);
    void getTypeAarticalMWSuccess(List<ArticalBox> articalBoxes,List<String> pagePath);
}

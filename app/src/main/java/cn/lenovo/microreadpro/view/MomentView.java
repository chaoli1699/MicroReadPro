package cn.lenovo.microreadpro.view;

import java.util.List;

import cn.lenovo.microreadpro.base.BaseView;
import cn.lenovo.microreadpro.model.MComment;

/**
 * Created by Aaron on 2017/4/9.
 */

public interface MomentView extends BaseView {

    void getMomentsSuccess(List<MComment.PComment> momentList);
    void getMomentsFail(String msg);

}

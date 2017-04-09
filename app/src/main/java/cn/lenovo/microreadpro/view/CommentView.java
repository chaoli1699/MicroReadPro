package cn.lenovo.microreadpro.view;

import cn.lenovo.microreadpro.base.BaseView;

/**
 * Created by Aaron on 2017/4/9.
 */

public interface CommentView extends BaseView {

    void getCommentsSuccess();
    void getCommentsFail();

    void addCommentSuccess();
    void addCommentFail();
}

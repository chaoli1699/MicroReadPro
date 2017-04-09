package cn.lenovo.microreadpro.view;

import java.util.List;

import cn.lenovo.microreadpro.base.BaseView;
import cn.lenovo.microreadpro.model.MComment;

/**
 * Created by Aaron on 2017/4/9.
 */

public interface CommentView extends BaseView {

    void getCommentsSuccess(List<MComment.Comment> commentList);
    void getCommentsFail(String msg);

//    void addCommentSuccess();
//    void addCommentFail(String msg);
}

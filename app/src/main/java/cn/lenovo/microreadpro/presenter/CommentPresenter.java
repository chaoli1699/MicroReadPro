package cn.lenovo.microreadpro.presenter;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.model.MCollection;
import cn.lenovo.microreadpro.view.CommentView;

/**
 * Created by Aaron on 2017/4/9.
 */

public class CommentPresenter extends BasePresenter<CommentView> {

    public CommentPresenter(CommentView view){
        attachView(view);
    }

    public void getComments(MCollection.Artical artical){}
}

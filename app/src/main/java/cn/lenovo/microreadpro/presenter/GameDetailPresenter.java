package cn.lenovo.microreadpro.presenter;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.view.GameDetailView;

/**
 * Created by Aaron on 2017/2/26.
 */

public class GameDetailPresenter extends BasePresenter<GameDetailView> {

    public GameDetailPresenter(GameDetailView view){
        attachView(view);
    }

}

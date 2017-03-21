package cn.lenovo.microreadpro.view;

import java.util.List;

import cn.lenovo.microreadpro.base.BaseView;
import cn.lenovo.microreadpro.model.GameBean;

/**
 * Created by Aaron on 2017/2/26.
 */

public interface GameView extends BaseView {

    void getGamesSuccess(List<GameBean> gameBeans);
}

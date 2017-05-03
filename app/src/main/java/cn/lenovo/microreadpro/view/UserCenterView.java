package cn.lenovo.microreadpro.view;

import java.util.List;

import cn.lenovo.microreadpro.model.MUFeture;

/**
 * Created by Aaron on 2017/1/2.
 */

public interface UserCenterView {

    void getUFeturesSuccess(List<MUFeture.UFeture> fetureList);
}

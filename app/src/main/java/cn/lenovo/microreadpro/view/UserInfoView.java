package cn.lenovo.microreadpro.view;

import java.util.List;

import cn.lenovo.microreadpro.model.MUFeture;

/**
 * Created by Aaron on 2017/1/2.
 */

public interface UserInfoView {

    void getUserInfoSuccess(List<MUFeture.UFeture> fetureList);

    void chanInfoSuccess();
    void chanInfoFail(String msg);
}

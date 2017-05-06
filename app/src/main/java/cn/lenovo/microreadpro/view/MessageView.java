package cn.lenovo.microreadpro.view;

import java.util.List;

import cn.lenovo.microreadpro.base.BaseView;
import cn.lenovo.microreadpro.model.MMessage;

/**
 * Created by Aaron on 2017/5/5.
 */

public interface MessageView extends BaseView{
    void getMessageSuccess(List<MMessage.Message> messageList);
    void getMessageFail(String msg);
}

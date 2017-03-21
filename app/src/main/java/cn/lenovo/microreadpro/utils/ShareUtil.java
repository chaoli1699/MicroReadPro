package cn.lenovo.microreadpro.utils;

import android.content.Context;

import java.util.Map;

import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.model.ShareBean;
import cn.lenovo.microreadpro.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Aaron on 2017/2/9.
 */

public class ShareUtil {

    public static void showShare(Context context, ShareBean shareBean){
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(shareBean.getTitle());
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(shareBean.getUrl());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shareBean.getText());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(shareBean.getImage_url());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shareBean.getUrl());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getResources().getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(shareBean.getUrl());

// 启动分享GUI
        oks.show(context);
    }
}

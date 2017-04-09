package cn.lenovo.microreadpro.presenter;

import com.google.gson.Gson;
import java.util.List;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.CStoriedBean;
import cn.lenovo.microreadpro.model.MCollection;
import cn.lenovo.microreadpro.model.NewsDetailEntity;
import cn.lenovo.microreadpro.net.ApiCallback;
import cn.lenovo.microreadpro.utils.LogUtil;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.NewsDetailView;

/**
 * Created by lenovo on 2016/12/1.
 */

public class NewsDetailPresenter extends BasePresenter<NewsDetailView> {

    private MyApplication mApp;
    private List<MCollection.Artical> collection;

    public NewsDetailPresenter(NewsDetailView view){
        attachView(view);
        mApp= (MyApplication) MyApplication.getInstance();
    }

    public void getNewsDetail(String id){
        view.showLoading();
        addSubscription(SystermParams.nwesApiStores.getNewsDetail(id), new ApiCallback<NewsDetailEntity>() {
            @Override
            public void onSuccess(NewsDetailEntity model) {

                String head = "<head>\n" +
                        "\t<link rel=\"stylesheet\" href=\"" + model.getCss()[0] + "\"/>\n" +
                        "</head>";
                String img = "<div class=\"headline\">";

                String body=model.getBody()
                        .replace(img, "")
                        .replace("*","---")
                        .replace("查看知乎讨论","");

                String content = head+"<body> \n" +
                        "<span style=\"font-size:"+mApp.font.getFont_size()+"px;color:"+
                        mApp.font.getFont_color()+";family:"+mApp.font.getFont_family()+";\"> \n" +
                        "<style type=\"text/css\"> \na:link,\na:visited{color:"+mApp.font.getFont_color()+";" +
                        "text-decoration:none;} \n</style> \n"+
                        body+"</span> \n" + "</body>";

                model.setBody(content);

                view.getNewsDetailSuccess(model);
            }

            @Override
            public void onFailure(String msg) {
                view.getNewsDetailFail(msg);
            }

            @Override
            public void onFinish() {
                view.hideLoading();
            }
        });
    }

    /**
     * 判断是否已被收藏
     * @param artical
     * @return
     */
    public boolean isCollected(MCollection.Artical artical){

        if(collection==null){
            collection=SystermParams.getTotalCollection("news");
        }

        if (collection.size()>0){
            for (MCollection.Artical a:collection) {
                if (a.getDetail_path().equals(artical.getDetail_path())){
//                        && s.getBelongs().getUsername().equals(mStoriedBean.getBelongs().getUsername())){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 加入收藏
     * @param artical
     */
    public void addCollection(MCollection.Artical artical){

//        if (mApp.isLogin){
//            if (!isCollected(storiesBean)){
//                collection.add(storiesBean);
//                mApp.aCache.put("colletcion",new Gson().toJson(collection));
//                view.addCollectionSuccess();
//            }else {
//                view.addCollectionFail("您已收藏过该文章");
//            }
//
//        }else {
//            view.addCollectionFail("登录后方能添加收藏");
//        }
        String articalJson=new Gson().toJson(artical);
        addSubscription(SystermParams.microReadApiStores.add_collection("add", mApp.currentUser.getUid(), articalJson), new ApiCallback<MCollection>() {
            @Override
            public void onSuccess(MCollection model) {

                if (model.getCode().equals("0")){
                    String collectionJson=new Gson().toJson(model.getCollections());
                    mApp.aCache.put("news",collectionJson);
                    view.addCollectionSuccess();
                }else {
                    view.addCollectionFail(model.getInfo());
                }
            }

            @Override
            public void onFailure(String msg) {
                view.addCollectionFail(msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 移除所选收藏
     * @param rs
     */
    public void removeCollection(CStoriedBean rs){

////        for(CStoriedBean rs: removeS){
//            for (int i=0;i<collection.size();i++){
//                 CStoriedBean cs=collection.get(i);
//                if (rs.getId()==cs.getId()
//                        && rs.getBelongs().getUsername().equals(cs.getBelongs().getUsername())){
//                    collection.remove(i);
//                }
//            }
////        }
//
//        mApp.aCache.put("colletcion",new Gson().toJson(collection));
//        view.removeCollectionSuccess();
    }

}

package cn.lenovo.microreadpro.presenter;

import android.util.Log;

import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.ArticalBox;
import cn.lenovo.microreadpro.model.CArticalBean;
import cn.lenovo.microreadpro.model.MCollection;
import cn.lenovo.microreadpro.net.ApiCallback;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.ArticalDetailView;

/**
 * Created by Aaron on 2017/2/7.
 */

public class ArticalDetailPresenter extends BasePresenter<ArticalDetailView> {

    private MyApplication mApp;
    private List<MCollection.Artical> collection;

    public ArticalDetailPresenter(ArticalDetailView view){
        attachView(view);
        mApp= (MyApplication) MyApplication.getInstance();
    }

    public void getArticalDetail(final ArticalBox.Artical artical){

        view.showLoading();
        addSubscription(SystermParams.articalMWApiStores.getHtmlString(artical.getDetailPath()), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                Document doc=Jsoup.parse(model);
                Elements info=doc.getElementsByClass("info");
                String publish_date=info.get(0).childNode(1).childNode(0)+"";
                String author=info.get(0).childNode(3).childNode(1).childNode(0)+"";
                String article=doc.getElementsByTag("article").toString()
                        .replace("font-size:"," ");
//                        .replace("<img alt=\"\" src=","");

                String content = "<head>\n"+ "</head> \n<body> \n" +
                        "<span style=\"font-size:"+mApp.font.getFont_size()+"px;color:"+
                        mApp.font.getFont_color()+";family:"+mApp.font.getFont_family()+";\"> \n" +
                        "<style type=\"text/css\"> \na:link,\na:visited{color:"+mApp.font.getFont_color()+";" +
                        "text-decoration:none;} \n</style> \n"+
                        article+"</span> \n" + "</body>";

//                String content="<head></head><body>" + article + "</body></head>";
                artical.setAuthor(author);
                artical.setPublishDate(publish_date);
                artical.setContent(content);
                System.out.println(""+article);
                view.getArticalDetailSuccess(artical);
            }

            @Override
            public void onFailure(String msg) {
                view.getArticalDetailFail(msg);
            }

            @Override
            public void onFinish() {
                view.hideLoading();
            }
        });

    }

//    /**
//     * 判断是否已被收藏
//     * @param mArticalBean
//     * @return
//     */
//    public boolean isCollected(CArticalBean mArticalBean){
//
//        if(collection==null){
//            collection=SystermParams.getTotalArticalCollection();
//        }
//
//        if (collection.size()>0){
//            for (CArticalBean s:collection) {
//                String str1=s.getDetailPath()+";"+mArticalBean.getDetailPath();
//                String str2=s.getBelongs().getUsername()+","+mArticalBean.getBelongs().getUsername();
//                Log.i("s.url vs art.url",str1);
//                Log.i("s.name vs art.name",str2);
//                if (s.getDetailPath().equals(mArticalBean.getDetailPath())
//                        && s.getBelongs().getUsername().equals(mArticalBean.getBelongs().getUsername())){
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    /**
     * 判断是否已被收藏
     * @param artical
     * @return
     */
    public boolean isCollected(MCollection.Artical artical){

        if(collection==null){
            collection=SystermParams.getTotalCollection("artical");
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
//            if (!isCollected(articalBean)){
//                collection.add(articalBean);
//                mApp.aCache.put("colletcion_artical",new Gson().toJson(collection));
//                view.addArticalCollectionSuccess();
//            }else {
//                view.addArticalCollectionFail("您已收藏过该文章");
//            }
//
//        }else {
//            view.addArticalCollectionFail("登录后方能添加收藏");
//        }

        String articalJson=new Gson().toJson(artical);
        addSubscription(SystermParams.microReadApiStores.add_collection("add", mApp.currentUser.getUid(), articalJson), new ApiCallback<MCollection>() {
            @Override
            public void onSuccess(MCollection model) {

                if (model.getCode().equals("0")){
                    String collectionJson=new Gson().toJson(model.getCollections());
                    mApp.aCache.put("artical",collectionJson);
                    view.addArticalCollectionSuccess();
                }else {
                    view.addArticalCollectionFail(model.getInfo());
                }
            }

            @Override
            public void onFailure(String msg) {
                view.addArticalCollectionFail(msg);
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
    public void removeCollection(CArticalBean rs){

////        for(CArticalBean rs: removeS){
//            for (int i=0;i<collection.size();i++){
//                 CArticalBean cs=collection.get(i);
//                if (rs.getDetailPath().equals(cs.getDetailPath())
//                        && rs.getBelongs().getUsername().equals(cs.getBelongs().getUsername())){
//                    collection.remove(i);
//                }
//            }
////        }
//
//        mApp.aCache.put("colletcion_artical",new Gson().toJson(collection));
//        view.removeArticalCollectionSuccess();
    }
}

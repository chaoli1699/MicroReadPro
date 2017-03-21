package cn.lenovo.microreadpro.presenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.model.ArticalBox;
import cn.lenovo.microreadpro.net.ApiCallback;
import cn.lenovo.microreadpro.utils.JsoupHelperForMW;
import cn.lenovo.microreadpro.utils.LogUtil;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.ArticalView;
import cn.lenovo.microreadpro.view.TypeArticalView;

/**
 * Created by Aaron on 2017/2/6.
 */

public class TypeArticalPresenter extends BasePresenter<TypeArticalView> {


    public TypeArticalPresenter(TypeArticalView view){
        attachView(view);
    }

    public void getMVTypeArticals(final String path){

        view.showLoading();
        addSubscription(SystermParams.articalMWApiStores.getHtmlString(path), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                Document doc=Jsoup.parse(model);
                List<ArticalBox> articalBoxes=new ArrayList<>();
                //首栏-banner
                Elements lm_box_w300_fl=doc.getElementsByClass("lm_box w300 fl");
                //首栏-artical
                Elements hdWrap_lm_box_fl=doc.getElementsByClass("hdWrap lm_box fl");
                ArticalBox index_box=JsoupHelperForMW.getIndexArticalBox(lm_box_w300_fl,hdWrap_lm_box_fl);
                articalBoxes.add(index_box);

                Elements lm_box_lm_zjbang_w240_fr=doc.getElementsByClass("lm_box lm_zjbang w240 fr");
                ArticalBox latest_box=JsoupHelperForMW.getLatestArticalBox(lm_box_lm_zjbang_w240_fr);
                articalBoxes.add(latest_box);
                System.out.println(""+latest_box);

                Elements lm_box_lm_pdbox=doc.getElementsByClass("lm_box lm_pdbox");
                List<ArticalBox> articalBoxs=JsoupHelperForMW.get3TypeArticalBox(lm_box_lm_pdbox);
                articalBoxes.addAll(articalBoxs);
                System.out.println(""+articalBoxs);

                view.getTypeAarticalMWSuccess(articalBoxes,null);
            }

            @Override
            public void onFailure(String msg) {
                view.getTypeArticalFail(msg);
            }

            @Override
            public void onFinish() {
                view.hideLoading();
            }
        });
    }

    public void getMWChildrenTypeArtical(String path){

//        view.showLoading();
        addSubscription(SystermParams.articalMWApiStores.getHtmlString(path), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                Document doc=Jsoup.parse(model);
                List<ArticalBox> articalBoxes=new ArrayList<>();
                Elements picAtc_pr=doc.getElementsByClass("info");

                Elements options=doc.getElementsByTag("option");
                List<String> pagePath=JsoupHelperForMW.getPagePath(options);

                ArticalBox articalBox=JsoupHelperForMW.getSimpleArticalBox(picAtc_pr);
                articalBoxes.add(articalBox);

                view.getTypeAarticalMWSuccess(articalBoxes,pagePath);
            }

            @Override
            public void onFailure(String msg) {
                view.getTypeArticalFail(msg);
            }

            @Override
            public void onFinish() {
//                view.hideLoading();
            }
        });
    }
}

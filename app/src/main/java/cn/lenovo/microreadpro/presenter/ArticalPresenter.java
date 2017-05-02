package cn.lenovo.microreadpro.presenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.model.ArticalBox;
import cn.lenovo.microreadpro.net.ApiCallback;
import cn.lenovo.microreadpro.utils.JsoupHelperForMW;
import cn.lenovo.microreadpro.utils.LogUtil;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.ArticalView;

/**
 * Created by Aaron on 2017/2/6.
 */

public class ArticalPresenter extends BasePresenter<ArticalView> {


    public ArticalPresenter(ArticalView view){
        attachView(view);
    }

    public void getMVArticals(final String path){

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
//                ArticalBox index_box=JsoupHelperForMW.getIndexArticalBox(lm_box_w300_fl,hdWrap_lm_box_fl);
//                articalBoxes.add(index_box);

                //精美图文
                Elements lm_box_lm_meitu=doc.getElementsByClass("lm_box lm_meitu");
                ArticalBox meitu_box=JsoupHelperForMW.getMeituArticalBox(lm_box_lm_meitu);
                articalBoxes.add(meitu_box);

                //美文类
                Elements lm_box_w370_lm_index_fr=doc.getElementsByClass("lm_box w370 lm_index fr");
                List<ArticalBox> meiwen_boxs= JsoupHelperForMW.get2TypeArticalBox(lm_box_w370_lm_index_fr);
                articalBoxes.addAll(meiwen_boxs);

                //文章类
                Elements lm_box_w370_lm_index_fl=doc.getElementsByClass("lm_box w370 lm_index fl");
                List<ArticalBox> wenzhang_boxs=JsoupHelperForMW.get2TypeArticalBox(lm_box_w370_lm_index_fl);
                articalBoxes.addAll(wenzhang_boxs);
                LogUtil.d("articalBox",articalBoxes);

                view.getAarticalMWSuccess(articalBoxes);
            }

            @Override
            public void onFailure(String msg) {
                view.getArticalFail(msg);
            }

            @Override
            public void onFinish() {
                view.hideLoading();
            }
        });
    }
}

package cn.lenovo.microreadpro.presenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.model.GameBean;
import cn.lenovo.microreadpro.net.ApiCallback;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.GameView;

/**
 * Created by Aaron on 2017/2/26.
 */

public class GamePresenter extends BasePresenter<GameView> {

    public GamePresenter(GameView view){
        attachView(view);
    }

    public void getGames(){

        view.showLoading();
        addSubscription(SystermParams.gameApiStores.getGames(), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {

                ArrayList<GameBean> games=new ArrayList<>();
                Document doc= Jsoup.parse(model);
                Elements panel=doc.getElementsByClass("panel");
                for (int i=0;i<panel.size();i++){
                    GameBean game=new GameBean();

                    Node node=panel.get(i);
                    while (node.childNodeSize()>0){
                        node=node.childNode(0);
                    }
                    String title=node.attr("title");
                    String img_path=node.absUrl("src");
                    String url=panel.get(i).getElementsByTag("a").get(1).attr("href");
                    game.setName(title);
                    game.setImg_path(img_path);
                    game.setUrl(url.replace("yx8.com","yx8.com/game"));
                    games.add(game);

                }
                view.hideLoading();
                view.getGamesSuccess(games);
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onFinish() {

            }
        });

    }
}

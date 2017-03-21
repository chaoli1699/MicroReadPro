package cn.lenovo.microreadpro.presenter;

import cn.lenovo.microreadpro.base.BasePresenter;
import cn.lenovo.microreadpro.model.NewsEntity;
import cn.lenovo.microreadpro.net.ApiCallback;
import cn.lenovo.microreadpro.utils.SystermParams;
import cn.lenovo.microreadpro.view.NewsView;

/**
 * Created by lenovo on 2016/11/30.
 */

public class NewsPresenter extends BasePresenter<NewsView> {

    public NewsPresenter(NewsView view){
        attachView(view);
    }

    public void getLatestNews(){
        view.showLoading();
        addSubscription(SystermParams.nwesApiStores.getLatestNews(), new ApiCallback<NewsEntity>() {
            @Override
            public void onSuccess(NewsEntity model) {

                /**
                 * 移除和top_stories中的重复项
                 */
                NewsEntity.StoriesBean storiesBean;

                for (NewsEntity.TopStoriesBean topStoriesBean:model.getTop_stories()){
                    for (int i=0;i<model.getStories().size();i++){
                        storiesBean=model.getStories().get(i);
                        if (topStoriesBean.getId()==storiesBean.getId()){
                            model.getStories().remove(i);
                        }
                    }
                }

                view.getNewsSuccess(model);
            }

            @Override
            public void onFailure(String msg) {
                view.getNewsFail(msg);
            }

            @Override
            public void onFinish() {
                view.hideLoading();
            }
        });
    }

    public void getBeforeNews(String date){

        addSubscription(SystermParams.nwesApiStores.getBeforetNews(date), new ApiCallback<NewsEntity>() {
            @Override
            public void onSuccess(NewsEntity model) {
                view.getNewsSuccess(model);
            }

            @Override
            public void onFailure(String msg) {
                view.getNewsFail(msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }

}

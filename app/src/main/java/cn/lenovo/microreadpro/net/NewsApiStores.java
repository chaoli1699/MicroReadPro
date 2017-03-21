package cn.lenovo.microreadpro.net;

import cn.lenovo.microreadpro.model.NewsDetailEntity;
import cn.lenovo.microreadpro.model.NewsEntity;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by lenovo on 2016/11/28.
 */

public interface NewsApiStores {

    String API_ZHIHU_URL = "http://news-at.zhihu.com/api/4/";

    @GET("news/latest")
    Observable<NewsEntity> getLatestNews();

    @GET("news/before/{time}")
    Observable<NewsEntity> getBeforetNews(@Path("time") String time);

    @GET("news/{id}")
    Observable<NewsDetailEntity> getNewsDetail(@Path("id") String id);
}

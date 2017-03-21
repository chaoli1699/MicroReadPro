package cn.lenovo.microreadpro.net;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Aaron on 2017/3/2.
 */

public interface ArticalMWApiStores {

    String API_MEIWEN_URL="http://www.lookmw.cn/";

    @GET("{item}")
    Observable<String> getHtmlString(@Path("item") String item);
}

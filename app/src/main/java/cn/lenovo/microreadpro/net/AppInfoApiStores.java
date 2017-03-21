package cn.lenovo.microreadpro.net;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Aaron on 2017/3/20.
 */

public interface AppInfoApiStores {

    String API_APPINFO_URL = "http://sj.qq.com/";

    @GET("/myapp/detail.htm")
    Observable<String> getHtmlForUpdate(@Query("apkName") String apkName);
}

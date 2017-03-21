package cn.lenovo.microreadpro.net;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.lenovo.microreadpro.BuildConfig;
import cn.lenovo.microreadpro.base.MyApplication;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by lenovo on 2016/11/28.
 */

public class AppClientGB2312 {

    public static Retrofit mRetrofit;
    private static OkHttpClient okHttpClient;

    public static Retrofit getRetrofitForGB2312HTML(String url){
//        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(url)
//                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
//        }

        return mRetrofit;
    }

    public static OkHttpClient getOkHttpClient(){
        if (okHttpClient == null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
            }
            //cache url
            File httpCacheDirectory = new File(MyApplication.mContext.getExternalCacheDir(), "responses");
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(httpCacheDirectory, cacheSize);
            builder.cache(cache);
            builder.addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    //cache
    static Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(365, TimeUnit.DAYS);
            CacheControl cacheControl = cacheBuilder.build();

            Request request = chain.request();
            if (!MyApplication.isNetworkAvailable(MyApplication.getInstance())) {
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();

            }
            Response originalResponse = chain.proceed(request);

            okhttp3.MediaType mediaType = originalResponse.body().contentType().parse("application/x-www-form-urlencoded; charset=gb2312");
            byte[] b = originalResponse.body().bytes(); //获取数据的bytes
            String content = new String(b, "GB2312"); //然后将其转为gb2312

            if (MyApplication.isNetworkAvailable(MyApplication.getInstance())) {
                int maxAge = 0; // read from cache
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .body(originalResponse.body().create(mediaType,content))
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-xcached, max-stale=" + maxStale)
                        .body(originalResponse.body().create(mediaType,content))
                        .build();
            }
        }
    };
}

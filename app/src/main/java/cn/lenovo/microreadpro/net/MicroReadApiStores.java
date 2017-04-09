package cn.lenovo.microreadpro.net;

import cn.lenovo.microreadpro.model.MCollection;
import cn.lenovo.microreadpro.model.MUser;
import cn.lenovo.microreadpro.model.MVersion;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;

/**
 * Created by Aaron on 2017/4/8.
 */

public interface MicroReadApiStores {

    String API_MICROREAD_URL="http://192.168.1.104:80/MicroRead/";

    @GET("version.php")
    Observable<MVersion> check_version(@Query("action") String action);

    @GET("user.php")
    Observable<MUser> user_manager(@Query("action") String action, @Query("username") String username, @Query("password") String password);

    @GET("user.php")
    Observable<MUser> chan_sex(@Query("action") String action, @Query("uid") int uid, @Query("sex") int sex);

    @GET("user.php")
    Observable<MUser> chan_city(@Query("action") String action, @Query("uid") int uid, @Query("city") String sex);

    @GET("user.php")
    Observable<MUser> chan_sign(@Query("action") String action, @Query("uid") int uid, @Query("sign") String sex);

    @GET("collection.php")
    Observable<MCollection> add_collection(@Query("action") String action, @Query("uid") int uid, @Query("artical") String articalJson);

    @GET("collection.php")
    Observable<MCollection> query_collection(@Query("action") String action, @Query("uid") int uid, @Query("atid") int atid);

    @GET("collection.php")
    Observable<MCollection> remove_collection(@Query("action") String action, @Query("uid") int uid, @Query("artical") String artical);



}

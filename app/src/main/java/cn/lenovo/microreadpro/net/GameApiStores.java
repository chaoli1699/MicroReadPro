package cn.lenovo.microreadpro.net;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Aaron on 2017/2/6.
 */

public interface GameApiStores {

    String API_GAME_URL = "http://yx8.com/";

    @GET(" ")
    Observable<String> getGames();

}

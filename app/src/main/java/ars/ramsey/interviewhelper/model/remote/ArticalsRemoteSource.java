package ars.ramsey.interviewhelper.model.remote;

import android.util.Log;

import ars.ramsey.interviewhelper.model.ArticalsSource;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Ramsey on 2017/5/9.
 */

public class ArticalsRemoteSource implements ArticalsSource {

    private static ArticalsRemoteSource INSTANCE;
    private static ExploreListService SERVICE;

    private ArticalsRemoteSource()
    {
        SERVICE = RetrofitUtils.createApi(ExploreListService.class);
    }

    public static ArticalsRemoteSource getInstacne()
    {
        if(INSTANCE == null)
        {
            INSTANCE = new ArticalsRemoteSource();
        }
        return INSTANCE;
    }


    @Override
    public Observable getTasks(String id, int limit, int offset) {
        return SERVICE.getArticals(id,limit,offset).subscribeOn(Schedulers.io());
    }
}

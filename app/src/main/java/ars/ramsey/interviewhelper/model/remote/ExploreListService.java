package ars.ramsey.interviewhelper.model.remote;

import java.util.List;

import ars.ramsey.interviewhelper.model.bean.Article;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ramsey on 2017/5/9.
 */

public interface ExploreListService {

    @GET("/api/columns/{id}/posts")
    Observable<List<Article>> getArticals(@Path("id") String id, @Query("limit") int limit, @Query("offset") int offset);
}

package ars.ramsey.interviewhelper.presenter;

import android.util.Log;

import java.util.List;

import ars.ramsey.interviewhelper.model.ArticalsSource;
import ars.ramsey.interviewhelper.model.bean.Article;
import ars.ramsey.interviewhelper.view.BaseView;
import ars.ramsey.interviewhelper.view.ExploreListView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Ramsey on 2017/5/9.
 */

public class ExplorePresenter implements BasePresenter<ExploreListView> {

    private ExploreListView mView;
    private ArticalsSource mSource;

    private static int LIMIT = 10;
    private static final String ID = "lishichao";

    public ExplorePresenter(ArticalsSource source)
    {
        this.mSource = source;
    }

    @Override
    public void attachView(ExploreListView view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void detachView(ExploreListView view) {
        mView = null;
        mView.setPresenter(null);
    }

    public void loadArticals(final int page)
    {

        int offset = (page - 1) * LIMIT;
        mSource.getTasks(ID,LIMIT,offset).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<Article>>() {
            @Override
            public void accept(@NonNull List<Article> articles) throws Exception {
                Log.i("RAMSEY","RESULT"+articles.size());
                if (page == 1) {
                    mView.refresh(articles);
                } else {
                    mView.loadMore(articles);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.i("RAMSEY",throwable.toString());
            }
        });
    }
}

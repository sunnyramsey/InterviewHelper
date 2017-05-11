package ars.ramsey.interviewhelper.view;

import java.util.List;

import ars.ramsey.interviewhelper.model.bean.Article;
import ars.ramsey.interviewhelper.presenter.ExplorePresenter;

/**
 * Created by Ramsey on 2017/5/9.
 */

public interface ExploreListView<T> extends BaseView<T> {
    void refresh(List<Article> data);
    void loadMore(List<Article> data);
}

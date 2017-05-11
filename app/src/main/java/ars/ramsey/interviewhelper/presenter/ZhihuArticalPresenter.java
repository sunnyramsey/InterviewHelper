package ars.ramsey.interviewhelper.presenter;

import ars.ramsey.interviewhelper.view.BaseView;
import ars.ramsey.interviewhelper.view.ZhihuArticalView;

/**
 * Created by Ramsey on 2017/5/11.
 */

public class ZhihuArticalPresenter implements BasePresenter<ZhihuArticalView> {

    private ZhihuArticalView mView;

    @Override
    public void attachView(ZhihuArticalView view) {
        mView = view;
        view.setPresenter(this);
    }

    @Override
    public void detachView(ZhihuArticalView view) {
        mView = null;
        view.setPresenter(null);
    }
}

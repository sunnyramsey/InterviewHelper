package ars.ramsey.interviewhelper.presenter;

import ars.ramsey.interviewhelper.view.BaseView;

/**
 * Created by Ramsey on 2017/4/6.
 */

public interface BasePresenter <T extends BaseView>{
    void attachView(T view);
    void detachView(T view);

}

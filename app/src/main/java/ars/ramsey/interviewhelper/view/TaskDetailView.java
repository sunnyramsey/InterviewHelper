package ars.ramsey.interviewhelper.view;

import ars.ramsey.interviewhelper.model.bean.Task;
import ars.ramsey.interviewhelper.presenter.TaskPresenter;

/**
 * Created by Ramsey on 2017/4/11.
 */

public interface TaskDetailView<T> extends BaseView<T> {
    public void getTask(Task task);
}

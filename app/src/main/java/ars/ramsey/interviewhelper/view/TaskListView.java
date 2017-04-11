package ars.ramsey.interviewhelper.view;

import java.util.List;

import ars.ramsey.interviewhelper.model.bean.Task;

/**
 * Created by Ramsey on 2017/4/6.
 */

public interface TaskListView<T> extends BaseView<T> {
    void refresh(List<Task> data);
    void loadMore(List<Task> data);
}

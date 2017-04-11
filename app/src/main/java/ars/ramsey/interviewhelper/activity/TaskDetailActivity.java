package ars.ramsey.interviewhelper.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import ars.ramsey.interviewhelper.R;
import ars.ramsey.interviewhelper.fragment.TaskDetailFragment;
import ars.ramsey.interviewhelper.model.bean.Task;
import ars.ramsey.interviewhelper.model.local.TasksLocalSource;
import ars.ramsey.interviewhelper.presenter.TaskDetailPresenter;
import ars.ramsey.interviewhelper.view.TaskDetailView;

/**
 * Created by Ramsey on 2017/4/11.
 */

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Task task = getIntent().getParcelableExtra("TASK");
        TaskDetailFragment fragment = new TaskDetailFragment();
        TaskDetailPresenter taskDetailPresenter = new TaskDetailPresenter(TasksLocalSource.getInstance(getApplicationContext()),(TaskDetailView)fragment);
        Bundle args = new Bundle();
        args.putParcelable("TASK",task);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.task_detail_content, fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

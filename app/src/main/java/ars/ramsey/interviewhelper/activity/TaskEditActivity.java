package ars.ramsey.interviewhelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ars.ramsey.interviewhelper.R;
import ars.ramsey.interviewhelper.adapter.TaskDetailAdapter;
import ars.ramsey.interviewhelper.model.bean.Task;
import ars.ramsey.interviewhelper.model.local.TasksLocalSource;

/**
 * Created by Ramsey on 2017/4/12.
 */

public class TaskEditActivity extends AppCompatActivity {

    private Button cancleBtn;
    private Button saveBtn;
    private RecyclerView recyclerView;
    private TaskDetailAdapter mAdapter;
    private Task mTask;
    private TasksLocalSource mLocalSource;
    private TextView txtTitle;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(0);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        cancleBtn = (Button)findViewById(R.id.btnCancle);
        saveBtn = (Button)findViewById(R.id.btnSave);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        txtTitle = (TextView)findViewById(R.id.tvTitle);

        //Init recycler view
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        mTask = getIntent().getParcelableExtra("TASK");

        mAdapter = new TaskDetailAdapter(mTask,true);
        recyclerView.setAdapter(mAdapter);

        txtTitle.setText(mTask.getCompanyName());


        //Init cancle button
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });

        //Init save button
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = mAdapter.getNewTask();
                Intent intent = new Intent();
                intent.putExtra("TASK",task);
                setResult(1,intent);
                finish();
            }
        });

    }
}

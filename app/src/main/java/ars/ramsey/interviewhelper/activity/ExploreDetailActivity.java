package ars.ramsey.interviewhelper.activity;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ars.ramsey.interviewhelper.R;
import ars.ramsey.interviewhelper.fragment.ZhihuArticalFragment;
import ars.ramsey.interviewhelper.model.bean.Article;

/**
 * Created by Ramsey on 2017/5/11.
 */

public class ExploreDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_detail);
        Bundle bundle = getIntent().getExtras();
        ZhihuArticalFragment fragment = new ZhihuArticalFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.explore_content,fragment).commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}

package ars.ramsey.interviewhelper.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jpardogo.android.googleprogressbar.library.NexusRotationCrossDrawable;

import java.util.ArrayList;

import ars.ramsey.interviewhelper.R;
import ars.ramsey.interviewhelper.adapter.NavDrawerListAdapter;
import ars.ramsey.interviewhelper.adapter.TaskListAdapter;
import ars.ramsey.interviewhelper.fragment.TaskDetailFragment;
import ars.ramsey.interviewhelper.fragment.TaskListFragment;
import ars.ramsey.interviewhelper.model.bean.NavDrawerItem;
import ars.ramsey.interviewhelper.model.local.TasksLocalSource;
import ars.ramsey.interviewhelper.presenter.TaskDetailPresenter;
import ars.ramsey.interviewhelper.presenter.TaskPresenter;
import ars.ramsey.interviewhelper.view.LoadMoreRecyclerView;
import ars.ramsey.interviewhelper.view.TaskDetailView;
import ars.ramsey.interviewhelper.view.TaskListView;

/**
 * Created by Ramsey on 2017/4/7.
 */

public class HomeActivity extends AppCompatActivity {

    private ListView mDrawerMenu;
    private String[] mNavMenuTitles;
    private TypedArray mNavMenuIconsTypeArray;
    private ArrayList<NavDrawerItem> mNavDrawerItems;
    private NavDrawerListAdapter mAdapter;
    private Toolbar actionBarToolbar;
    private DrawerLayout drawerLayout;
    private int position;

    private FrameLayout frameLayout;

    //for ui testing
    private LoadMoreRecyclerView recyclerView;
    private ArrayList<Integer> listData = new ArrayList<>();
    private TaskListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Fresco.initialize(getApplicationContext());
        frameLayout = (FrameLayout)findViewById(R.id.content);


        setupToolbar();
        setupDrawer();

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout == null)
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                openDrawer();
                return true;
            case R.id.action_settings:
                break;

        }
        return super.onOptionsItemSelected(item);
    }



    public void setupToolbar(){
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private ActionBar getActionBarToolbar() {
        if (actionBarToolbar == null) {
            actionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (actionBarToolbar != null) {
                setSupportActionBar(actionBarToolbar);
            }
        }
        return getSupportActionBar();
    }

    private void setupDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout == null) {
            // current activity does not have a drawer.
            return;
        }
        mDrawerMenu = (ListView) findViewById(R.id.left_menu);
        mNavMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        // nav drawer icons from resources
        mNavMenuIconsTypeArray = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
        mNavDrawerItems = new ArrayList<>();
        for (int i = 0; i < mNavMenuTitles.length; i++) {
            mNavDrawerItems.add(new NavDrawerItem(mNavMenuTitles[i], mNavMenuIconsTypeArray
                    .getResourceId(i, -1)));
        }
        mNavMenuIconsTypeArray.recycle();
        // setting the nav drawer list adapter
        mAdapter = new NavDrawerListAdapter(this,
                mNavDrawerItems);
        mDrawerMenu.setAdapter(mAdapter);
        mDrawerMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NavDrawerItem navDrawerItem = mNavDrawerItems.get(i);
                if (navDrawerItem != null) {
                    selectItem(i, navDrawerItem.getTitle());
                }

            }
        });


        selectItem(0, mNavDrawerItems.get(0).getTitle());
    }

    private void openDrawer() {
        if (drawerLayout == null)
            return;
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void closeDrawer() {
        if (drawerLayout == null)
            return;
        drawerLayout.closeDrawer(GravityCompat.START);
    }


    public void selectItem(int position, String title) {
        Fragment fragment = null;
        this.position = position;
        switch (position) {
            case 0:
                //首页
                fragment = new TaskListFragment();
                TaskPresenter presenter = new TaskPresenter(TasksLocalSource.getInstance(getApplicationContext()), (TaskListView) fragment);
                break;
            case 1:
                //发现
                fragment = new TaskDetailFragment();

                break;
            case 2:
                //关注
                //fragment = new FollowFragment();
                break;
            case 3:
                //收藏
                //fragment = new CollectFragment();
                break;
            case 4:
                //草稿
                //fragment = new DraftFragment();
                break;
            case 5:
                //提问
                //fragment = new QuestionFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content, fragment).commit();
            setTitle(title);
            closeDrawer();
        } else {
            Log.e("HomeActivity", "Error in creating fragment");
        }
    }





}

package ars.ramsey.interviewhelper.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import ars.ramsey.interviewhelper.R;
import ars.ramsey.interviewhelper.adapter.NavDrawerListAdapter;
import ars.ramsey.interviewhelper.adapter.TaskListAdapter;
import ars.ramsey.interviewhelper.model.bean.NavDrawerItem;
import ars.ramsey.interviewhelper.view.LoadMoreRecyclerView;

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

    //for ui testing
    private LoadMoreRecyclerView recyclerView;
    private ArrayList<Integer> listData = new ArrayList<>();
    private TaskListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        setupToolbar();
        setupDrawer();

        recyclerView = (LoadMoreRecyclerView)findViewById(R.id.load_recycler_view);
        for(int i = 0;i<50;i++)
            listData.add(i);
        adapter = new TaskListAdapter(this,listData);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                for(int i = 50;i<100;i++)
                    listData.add(i);
                adapter.notifyDataSetChanged();
                recyclerView.loadMoreComplete();
            }
        });

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
//        mDrawerMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                if (!BaseUtil.isEmpty(mNavDrawerItems, i)) {
////                    NavDrawerItem navDrawerItem = mNavDrawerItems.get(i);
////                    if (navDrawerItem != null) {
////                        selectItem(i, navDrawerItem.getTitle());
////                    }
////                }
//            }
//        });


        //selectItem(0, mNavDrawerItems.get(0).getTitle());
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





}

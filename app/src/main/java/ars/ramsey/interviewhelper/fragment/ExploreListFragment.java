package ars.ramsey.interviewhelper.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jpardogo.android.googleprogressbar.library.NexusRotationCrossDrawable;

import java.util.ArrayList;
import java.util.List;

import ars.ramsey.interviewhelper.R;
import ars.ramsey.interviewhelper.adapter.ExploreListAdapter;
import ars.ramsey.interviewhelper.model.bean.Article;
import ars.ramsey.interviewhelper.presenter.ExplorePresenter;
import ars.ramsey.interviewhelper.view.ExploreListView;
import ars.ramsey.interviewhelper.view.LoadMoreRecyclerView;

/**
 * Created by Ramsey on 2017/5/10.
 */

public class ExploreListFragment extends Fragment implements ExploreListView<ExplorePresenter> {

    private SwipeRefreshLayout mRefreshLayout;
    private LoadMoreRecyclerView mRecyclerView;
    private List<Article> mDatas = new ArrayList<>();
    private int mPage = 1;
    private ExploreListAdapter mAdapter;
    private ExplorePresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.explore_list_layout,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (LoadMoreRecyclerView)view.findViewById(R.id.load_more_recycleview);
        mRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);


        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                mPresenter.loadArticals(mPage);
            }
        });

        ProgressBar progressBar = new ProgressBar(getContext());
        progressBar.setIndeterminateDrawable(new NexusRotationCrossDrawable.Builder(getContext()).colors(getResources().getIntArray(R.array.progress_bar_colors)).build());
        mRecyclerView.setLoadingFooterView(progressBar);

        TextView textView = new TextView(getContext());
        textView.setText("已经没有啦╮(╯_╰)╭");
        mRecyclerView.setEndFooterView(textView);

        mAdapter = new ExploreListAdapter(getContext(),mDatas);
        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPage++;
                mPresenter.loadArticals(mPage);
            }
        });

        mPresenter.loadArticals(mPage);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @Override
    public void refresh(List<Article> data) {
        mRefreshLayout.setRefreshing(false);
        mRecyclerView.refreshComplete();
        mDatas.clear();
        Log.i("RAMSEY",data.get(0).getContent());
        if(data != null)
            mDatas.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore(List<Article> data) {
        if(data == null || data.size() == 0)
        {
            mRecyclerView.loadMoreEnd();
        }else{
            mRecyclerView.loadMoreComplete();
            if(data!=null)
                mDatas.addAll(data);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void setPresenter(ExplorePresenter presenter) {
        this.mPresenter = presenter;
    }
}

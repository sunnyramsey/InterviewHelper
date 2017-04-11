package ars.ramsey.interviewhelper.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import ars.ramsey.interviewhelper.adapter.LoadMoreAdapter;

/**
 * Created by dell on 2017/4/8.
 */

public class LoadMoreRecyclerView extends RecyclerView {

    private Context mContext;
    private Adapter mInnerAdapter;
    private Adapter mLoadMoreAdapter;
    private LoadingMoreFooter mLoadingMoreFooter;

    private LoadMoreListener mloadMoreListener;
    private boolean canLoadMore = true;
    private boolean isLoading = false;


    public LoadMoreRecyclerView(Context context) {
        this(context,null);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mLoadingMoreFooter = new LoadingMoreFooter(context);
        mLoadingMoreFooter.setGone();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mInnerAdapter = adapter;
        mLoadMoreAdapter = new LoadMoreAdapter(this,adapter,mLoadingMoreFooter);
        super.setAdapter(mLoadMoreAdapter);
        mInnerAdapter.registerAdapterDataObserver(mDataObserver);
    }


    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);

        if (state == RecyclerView.SCROLL_STATE_IDLE && mloadMoreListener != null && !isLoading && canLoadMore) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                //There are more than one value get from Func(findLast...),find max value that is last visible item.
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();

            if (layoutManager.getChildCount() > 1 && lastVisibleItemPosition >= layoutManager.getItemCount() - 1 && totalItemCount > visibleItemCount) {
                mLoadingMoreFooter.setVisible();
                isLoading = true;
                mloadMoreListener.onLoadMore();
            }
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }


    public void refreshComplete() {
        mLoadingMoreFooter.setGone();
        isLoading = false;
    }

    public void loadMoreComplete() {
        mLoadingMoreFooter.setGone();
        isLoading = false;
    }


    //到底了
    public void loadMoreEnd() {
        mLoadingMoreFooter.setEnd();
    }

    //replace default footer view.
    public void setLoadMoreFooter(LoadingMoreFooter view)
    {
        mLoadingMoreFooter = view;
    }

    //set the footer view for loading.
    public void setLoadingFooterView(View view)
    {
        mLoadingMoreFooter.addFootLoadingView(view);
    }

    //set the footer view for no more content
    public void setEndFooterView(View view)
    {
        mLoadingMoreFooter.addFootEndView(view);
    }

    // Load more toggle.
    public void setCanLoadMore(boolean flag)
    {
        canLoadMore = flag;
    }

    //Set listener for loading more.
    public void setLoadMoreListener(LoadMoreListener listener)
    {
        mloadMoreListener = listener;
    }


    public interface LoadMoreListener{
        void onLoadMore();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        if (mLoadMoreAdapter != null && mLoadMoreAdapter instanceof LoadMoreAdapter) {
            ((LoadMoreAdapter) mLoadMoreAdapter).setOnItemClickListener(onItemClickListener);
        }
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener) {
        if (mLoadMoreAdapter != null && mLoadMoreAdapter instanceof LoadMoreAdapter) {
            ((LoadMoreAdapter) mLoadMoreAdapter).setOnItemLongClickListener(listener);
        }
    }


    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            mLoadMoreAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mLoadMoreAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mLoadMoreAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mLoadMoreAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mLoadMoreAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mLoadMoreAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    };

}

package ars.ramsey.interviewhelper.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import ars.ramsey.interviewhelper.view.LoadingMoreFooter;

/**
 * Created by Ramsey on 2017/4/8.
 */

public class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mInnerAdapter;
    private LoadingMoreFooter mLoadingMoreFooter;

    private static final int FOOTER_TYPE = -1;
    private static final int DEFAULT_TYPE = 0;

    public LoadMoreAdapter(RecyclerView view,RecyclerView.Adapter adapter,LoadingMoreFooter footer) {
        this.mRecyclerView = view;
        this.mInnerAdapter = adapter;
        this.mLoadingMoreFooter = footer;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    private boolean isFooter(int position)
    {
        return mLoadingMoreFooter != null && mInnerAdapter != null && position >= mInnerAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if(isFooter(position))
            return FOOTER_TYPE;
        else if(mInnerAdapter != null)
            return mInnerAdapter.getItemViewType(position);
        else
            return DEFAULT_TYPE;

    }


    @Override
    public int getItemCount() {
        if(mInnerAdapter != null)
            return 1 + mInnerAdapter.getItemCount();
        else
            return 1;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private class SimpleViewHolder extends RecyclerView.ViewHolder{

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }
}

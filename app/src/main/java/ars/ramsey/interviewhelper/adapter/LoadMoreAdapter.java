package ars.ramsey.interviewhelper.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import ars.ramsey.interviewhelper.view.LoadingMoreFooter;

/**
 * Created by Ramsey on 2017/4/8.
 */

public class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener,View.OnLongClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mInnerAdapter;
    private LoadingMoreFooter mLoadingMoreFooter;

    private static final int FOOTER_TYPE = -1;
    private static final int DEFAULT_TYPE = 0;

    private AdapterView.OnItemClickListener onItemClickListener;
    private AdapterView.OnItemLongClickListener onItemLongClickListener;

    public LoadMoreAdapter(RecyclerView view, RecyclerView.Adapter adapter, LoadingMoreFooter footer) {
        this.mRecyclerView = view;
        this.mInnerAdapter = adapter;
        this.mLoadingMoreFooter = footer;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == FOOTER_TYPE)
            return new LoadingViewHolder(mLoadingMoreFooter);
        else
            return mInnerAdapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i("RAMSEY",String.valueOf(position));
        holder.itemView.setOnClickListener(this);
        holder.itemView.setOnLongClickListener(this);
        if(!isFooter(position)){
            mInnerAdapter.onBindViewHolder(holder, position);
        }
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
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if(layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams
                && isFooter(holder.getLayoutPosition()))
        {
            ((StaggeredGridLayoutManager.LayoutParams)layoutParams).setFullSpan(true);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mInnerAdapter.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager != null && manager instanceof GridLayoutManager)
        {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            final GridLayoutManager.SpanSizeLookup originLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(isFooter(position))
                        return gridLayoutManager.getSpanCount();
                    else if(originLookup != null)
                        return originLookup.getSpanSize(position);
                    else
                        return 1;
                }
            });
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener)
    {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener)
    {
        this.onItemLongClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if(onItemClickListener != null)
        {
            onItemClickListener.onItemClick(null,view,mRecyclerView.getChildAdapterPosition(view),0);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if(onItemLongClickListener != null)
        {
            onItemLongClickListener.onItemLongClick(null,view,mRecyclerView.getChildAdapterPosition(view),0);
            return true;
        }
        return false;
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder{

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }
}

package ars.ramsey.interviewhelper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ars.ramsey.interviewhelper.R;
import ars.ramsey.interviewhelper.activity.ExploreDetailActivity;
import ars.ramsey.interviewhelper.model.bean.Article;

/**
 * Created by Ramsey on 2017/5/10.
 */

public class ExploreListAdapter extends RecyclerView.Adapter<ExploreListAdapter.ViewHolder> {

    public List<Article> mDatas = null;
    public Context mContext;

    public ExploreListAdapter(Context context,List<Article> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_item_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Article article = mDatas.get(position);
        holder.content_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ExploreDetailActivity.class);
                intent.putExtra("ARTICAL", article);
                mContext.startActivity(intent);
            }
        });
        holder.title_text.setText(article.getTitle());
        holder.tv_summary.setText(article.getSummary());
        holder.tv_likecount.setText(article.getLikesCount() > 1000 ? (float) (article.getLikesCount() / 1000) * 10 / 10 + "k" : article.getLikesCount() + "");
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_summary;
        public TextView tv_likecount;
        public TextView title_text;
        public LinearLayout content_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_summary = (TextView) itemView.findViewById(R.id.tv_summary);
            tv_likecount = (TextView) itemView.findViewById(R.id.tv_likecount);
            title_text = (TextView) itemView.findViewById(R.id.title_text);
            content_layout = (LinearLayout) itemView.findViewById(R.id.content_layout);
        }
    }
}

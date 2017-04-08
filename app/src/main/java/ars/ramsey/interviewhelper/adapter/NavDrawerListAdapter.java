package ars.ramsey.interviewhelper.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ars.ramsey.interviewhelper.R;
import ars.ramsey.interviewhelper.model.bean.NavDrawerItem;

/**
 * Created by Ramsey on 2017/4/7.
 */


public class NavDrawerListAdapter extends BaseAdapter {

    private Context context;
    private List<NavDrawerItem> navDrawerItems;

    public NavDrawerListAdapter(Context context, List<NavDrawerItem> navDrawerItems) {
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
            holder = new ViewHolder();
            holder.imgIcon = (ImageView) convertView.findViewById(R.id.drawer_icon);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.drawer_title);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
        holder.txtTitle.setText(navDrawerItems.get(position).getTitle());

        return convertView;
    }

    private static class ViewHolder{
        ImageView imgIcon;
        TextView txtTitle;

    }

}

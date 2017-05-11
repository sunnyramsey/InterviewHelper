package ars.ramsey.interviewhelper.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import ars.ramsey.interviewhelper.fragment.ExploreListFragment;
import ars.ramsey.interviewhelper.model.remote.ArticalsRemoteSource;
import ars.ramsey.interviewhelper.presenter.ExplorePresenter;

/**
 * Created by Ramsey on 2017/5/10.
 */

public class ExplorePagerAdapter extends FragmentStatePagerAdapter {

    private static int FRAGMENT_COUNT = 3;
    private static String[] PAGE_TITLE = {"知乎","果壳","不知道"};
    private static String[] ARGS_TAG = {"zhihu","guoke","no"};

    public ExplorePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        ExploreListFragment newFragment = new ExploreListFragment();
        ExplorePresenter presenter = new ExplorePresenter(ArticalsRemoteSource.getInstacne());
        presenter.attachView(newFragment);
        bundle.putString("TAG", ARGS_TAG[position]);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return PAGE_TITLE[position];
    }
}

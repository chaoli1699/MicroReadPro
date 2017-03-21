package cn.lenovo.microreadpro.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 2017/2/11.
 */

public class CollectionFragmentPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> tabList;


    public CollectionFragmentPageAdapter(FragmentManager fm,List<String> tabList,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;
        this.tabList=tabList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);
        return tabList.get(position);
    }
}

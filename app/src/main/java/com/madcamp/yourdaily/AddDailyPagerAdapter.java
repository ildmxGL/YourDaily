package com.madcamp.yourdaily;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

public class AddDailyPagerAdapter extends FragmentStatePagerAdapter {

    int mNumofTabs;

    public AddDailyPagerAdapter(FragmentManager fm, int NumofTabs) {
        super(fm);
        this.mNumofTabs = NumofTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AddDailyCompleteFragment tab1 = new AddDailyCompleteFragment();
                return tab1;
            case 1:
                AddDailyNotYetFragment tab2 = new AddDailyNotYetFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumofTabs;
    }
}

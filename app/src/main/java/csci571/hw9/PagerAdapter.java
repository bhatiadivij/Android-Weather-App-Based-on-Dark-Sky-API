package csci571.hw9;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;
    ArrayList<ArrayList<String>> favList;
    public PagerAdapter(FragmentManager fm, int tabNum, ArrayList<ArrayList<String>> favList) {
        super(fm);
        numOfTabs = tabNum;
        this.favList = favList;
    }
    public PagerAdapter(FragmentManager fm) {
        super(fm);
        numOfTabs = 0;
    }

    @Override
    public Fragment getItem(int position) {
        return new saved_locations(favList.get(position));
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}

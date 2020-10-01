package csci571.hw9.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Map;

import csci571.hw9.R;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;
    private Map<String, String> data;
    private ArrayList<String> fav;

    public SectionsPagerAdapter(Context context, FragmentManager fm, Map<String, String> data, ArrayList<String> fav ) {
        super(fm);
        mContext = context;
        this.data = data;
        this.fav = fav;
    }

    @Override
    public Fragment getItem(int position) {
        return new PlaceholderFragment(position, data, fav);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
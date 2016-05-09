package brymian.bubbles.bryant.search;

/**
 * Created by almanza1112 on 5/8/16.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SearchPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public SearchPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                SearchTabFragmentUsers tab1 = new SearchTabFragmentUsers();
                return tab1;
            case 1:
                SearchTabFragmentEpisodes tab2 = new SearchTabFragmentEpisodes();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
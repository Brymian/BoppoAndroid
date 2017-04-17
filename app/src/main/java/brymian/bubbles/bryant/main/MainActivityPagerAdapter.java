package brymian.bubbles.bryant.main;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MainActivityPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public MainActivityPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new MainTabNewsFeed();
            case 1:
                return new MainTabEpisodes();
            case 2:
                return new MainTabPersonal();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
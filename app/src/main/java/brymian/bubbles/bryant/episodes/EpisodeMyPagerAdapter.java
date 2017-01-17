package brymian.bubbles.bryant.episodes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class EpisodeMyPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public EpisodeMyPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new EpisodeMyHosting();
            case 1:
                return new EpisodeMyAttending();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
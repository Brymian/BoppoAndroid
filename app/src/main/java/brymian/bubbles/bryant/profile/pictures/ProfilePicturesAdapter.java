package brymian.bubbles.bryant.profile.pictures;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import brymian.bubbles.bryant.Tabs.TabFragment1;
import brymian.bubbles.bryant.Tabs.TabFragment2;
import brymian.bubbles.bryant.Tabs.TabFragment3;

/**
 * Created by Almanza on 3/17/2016.
 */
public class ProfilePicturesAdapter extends FragmentStatePagerAdapter{

    int mNumOfTabs;

    public ProfilePicturesAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ProfilePicture1 picture1 = new ProfilePicture1();
                return picture1;
            case 1:
                ProfilePicture2 picture2 = new ProfilePicture2();
                return picture2;
            case 2:
                ProfilePicture3 picture3 = new ProfilePicture3();
                return picture3;
            case 3:
                ProfilePicture4 picture4 = new ProfilePicture4();
                return picture4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

package com.brymian.boppo.bryant.pictures;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.brymian.boppo.bryant.pictures.pictureFragments.ProfilePicture1;
import com.brymian.boppo.bryant.pictures.pictureFragments.ProfilePicture2;
import com.brymian.boppo.bryant.pictures.pictureFragments.ProfilePicture3;
import com.brymian.boppo.bryant.pictures.pictureFragments.ProfilePicture4;


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
                return new ProfilePicture1();
            case 1:
                return new ProfilePicture2();
            case 2:
                return new ProfilePicture3();
            case 3:
                return new ProfilePicture4();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

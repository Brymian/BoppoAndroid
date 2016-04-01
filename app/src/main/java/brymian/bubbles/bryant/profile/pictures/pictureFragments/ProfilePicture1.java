package brymian.bubbles.bryant.profile.pictures.pictureFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import brymian.bubbles.R;
import brymian.bubbles.bryant.profile.pictures.GetImage;

/**
 * Created by Almanza on 3/17/2016.
 */
public class ProfilePicture1 extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.profile_picture1, container, false);
        View rootView = inflater.inflate(R.layout.profile_picture1, container, false);

        return rootView;
    }

}

package brymian.bubbles.bryant.profile;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import brymian.bubbles.R;

public class ProfileEditViewImage extends Fragment {
    ImageView ivProfilePicture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_edit_view_image, container, false);
        ivProfilePicture = (ImageView) rootView.findViewById(R.id.ivProfilePicture);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

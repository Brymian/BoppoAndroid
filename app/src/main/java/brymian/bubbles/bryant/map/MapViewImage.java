package brymian.bubbles.bryant.map;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import brymian.bubbles.R;

public class MapViewImage extends Fragment {
    ImageView ivImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_view_image, container, false);
        ivImage = (ImageView) rootView.findViewById(R.id.ivImage);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        getImage();
        return rootView;
    }

    private void getImage(){
        Picasso.with(getActivity()).load(MapActivity.getImagePath()).into(ivImage);
    }
}

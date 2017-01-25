package brymian.bubbles.bryant.main;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import brymian.bubbles.R;
import brymian.bubbles.bryant.camera.CameraActivity;
import brymian.bubbles.bryant.map.MapsActivity;

public class MainActivityFab extends Fragment implements View.OnClickListener {


    FloatingActionButton fabClose;
    RelativeLayout rlFabMap, rlFabCamera;
    LinearLayout llTransparent;
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_activity_fab, container, false);

        fabClose = (FloatingActionButton) rootView.findViewById(R.id.fabClose);
        fabClose.setOnClickListener(this);

        rlFabMap = (RelativeLayout) rootView.findViewById(R.id.rlFabMap);
        rlFabMap.setOnClickListener(this);

        rlFabCamera = (RelativeLayout) rootView.findViewById(R.id.rlFabCamera);
        rlFabCamera.setOnClickListener(this);

        llTransparent = (LinearLayout) rootView.findViewById(R.id.llTransparent);
        llTransparent.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabClose:
                getFragmentManager().popBackStack();
                break;

            case R.id.llTransparent:
                getFragmentManager().popBackStack();
                break;

            case R.id.rlFabMap:
                Log.e("llMap", "working");
                getActivity().startActivity(new Intent(getActivity(), MapsActivity.class).putExtra("profile", "all"));
                break;

            case R.id.rlFabCamera:
                Log.e("llCamera", "working");
                getActivity().startActivity(new Intent(getActivity(), CameraActivity.class).putExtra("imagePurpose", "Regular"));
                break;
        }
    }

}

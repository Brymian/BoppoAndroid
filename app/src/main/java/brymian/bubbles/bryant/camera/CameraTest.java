package brymian.bubbles.bryant.camera;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import brymian.bubbles.R;


public class CameraTest extends Fragment {
    ImageView ivImage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.camera_test, container, false);
        ivImage = (ImageView) rootView.findViewById(R.id.ivImage);
        getImage();
        return rootView;
    }

    private void getImage(){
        Bitmap bmp = BitmapFactory.decodeByteArray(CameraActivity.getImageDataByte(), 0, CameraActivity.getImageDataByte().length);
        ivImage.setImageBitmap(bmp);
    }
}

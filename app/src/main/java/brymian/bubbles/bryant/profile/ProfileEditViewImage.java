package brymian.bubbles.bryant.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import brymian.bubbles.R;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;

public class ProfileEditViewImage extends AppCompatActivity {
    ImageView ivProfilePicture;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit_view_image);
        ivProfilePicture = (ImageView) findViewById(R.id.ivProfilePicture);

        supportPostponeEnterTransition();

        Picasso.with(this)
                .load(SaveSharedPreference.getUserProfileImagePath(this))
                .fit()
                .noFade()
                .into(ivProfilePicture, new Callback() {
                    @Override
                    public void onSuccess() {
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError() {
                        supportStartPostponedEnterTransition();
                    }
                });
    }
}

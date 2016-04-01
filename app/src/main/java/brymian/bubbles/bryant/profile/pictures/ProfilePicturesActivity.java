package brymian.bubbles.bryant.profile.pictures;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.view.View;
import android.widget.ImageView;


import brymian.bubbles.R;
import brymian.bubbles.bryant.camera.CameraActivity;
import brymian.bubbles.bryant.profile.pictures.pictureFragments.ProfilePicture1;

/**
 * Created by Almanza on 3/17/2016.
 */
public class ProfilePicturesActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    ImageButton ibGallery, ibCamera;
    final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_pictures_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.Profile_Pictures);
        setSupportActionBar(mToolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final ProfilePicturesAdapter profilePicturesAdapter = new ProfilePicturesAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(profilePicturesAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) { /* positions 0-3 for this */
                    /* do something here */
                }
            }
        });
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                try {
                    viewPager.setCurrentItem(index);
                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                } catch (IllegalStateException ise) {
                    ise.printStackTrace();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ibGallery = (ImageButton) findViewById(R.id.ibGallery);
        ibGallery.setOnClickListener(this);

        ibCamera = (ImageButton) findViewById(R.id.ibCamera);
        ibCamera.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibCamera:
                startActivityForResult(new Intent(this, CameraActivity.class).putExtra("imagePurpose", "Profile"), REQUEST_CODE);
                break;

            case R.id.ibGallery:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
                byte[] byteArrayImage =  data.getExtras().getByteArray("encodedImage");
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayImage, 0, byteArrayImage.length);
                ProfilePicture1 profilePicture1 = new ProfilePicture1();
                ImageView iv = (ImageView) profilePicture1.getActivity().findViewById(R.id.ivProfilePicture1);
                //iv.setImageBitmap(bitmap);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }
}

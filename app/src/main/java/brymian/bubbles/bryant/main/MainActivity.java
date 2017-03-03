package brymian.bubbles.bryant.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import brymian.bubbles.R;
import brymian.bubbles.bryant.camera.CameraActivity;
import brymian.bubbles.bryant.search.SearchActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar mToolbar;
    TabLayout tabLayout;
    FloatingActionButton fabCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle(R.string.News_Feed);
        mToolbar.setTitleTextColor(Color.WHITE);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_people_white_24dp), 0, true);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_whatshot_black_24dp), 1, false);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_public_black_24dp), 2, false);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_account_circle_black_24dp), 3, false);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(3);
        final MainActivityPagerAdapter pagerAdapter= new MainActivityPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
                //viewPager.setCurrentItem(position);
                if (position == 0) {
                    mToolbar.setTitle(R.string.News_Feed);
                    tabLayout.getTabAt(0).setIcon(R.mipmap.ic_people_white_24dp);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.ic_whatshot_black_24dp);
                    tabLayout.getTabAt(2).setIcon(R.mipmap.ic_public_black_24dp);
                    tabLayout.getTabAt(3).setIcon(R.mipmap.ic_account_circle_black_24dp);

                } else if (position == 1) {
                    mToolbar.setTitle(R.string.Episodes);
                    tabLayout.getTabAt(0).setIcon(R.mipmap.ic_people_black_24dp);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.ic_whatshot_white_24dp);
                    tabLayout.getTabAt(2).setIcon(R.mipmap.ic_public_black_24dp);
                    tabLayout.getTabAt(3).setIcon(R.mipmap.ic_account_circle_black_24dp);
                } else if (position == 2) {
                    mToolbar.setTitle(R.string.Map);
                    tabLayout.getTabAt(0).setIcon(R.mipmap.ic_people_black_24dp);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.ic_whatshot_black_24dp);
                    tabLayout.getTabAt(2).setIcon(R.mipmap.ic_public_white_24dp);
                    tabLayout.getTabAt(3).setIcon(R.mipmap.ic_account_circle_black_24dp);
                } else if (position == 3) {
                    mToolbar.setTitle(R.string.Personal);
                    tabLayout.getTabAt(0).setIcon(R.mipmap.ic_people_black_24dp);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.ic_whatshot_black_24dp);
                    tabLayout.getTabAt(2).setIcon(R.mipmap.ic_public_black_24dp);
                    tabLayout.getTabAt(3).setIcon(R.mipmap.ic_account_circle_white_24dp);
                }
            }
        });
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                //tabLayout.getTabAt(index).select();
                viewPager.setCurrentItem(tab.getPosition());
                try {
                    if(index == 0){
                        tab.setIcon(R.mipmap.ic_people_white_24dp);
                    }else if(index == 1){
                        tab.setIcon(R.mipmap.ic_whatshot_white_24dp);
                    }else if (index == 2){
                        tab.setIcon(R.mipmap.ic_public_white_24dp);
                    }
                    else if(index == 3){
                        tab.setIcon(R.mipmap.ic_account_circle_white_24dp);
                    }
                } catch (NullPointerException | IllegalStateException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                if(index == 0){
                    tab.setIcon(R.mipmap.ic_people_black_24dp);
                }else if(index == 1){
                    tab.setIcon(R.mipmap.ic_whatshot_black_24dp);
                }else if(index == 2){
                    tab.setIcon(R.mipmap.ic_public_black_24dp);
                }else if(index == 3){
                    tab.setIcon(R.mipmap.ic_account_circle_black_24dp);
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        fabCamera = (FloatingActionButton) findViewById(R.id.fabCamera);
        fabCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /* FloatingActionButton */
            case R.id.fabCamera:
                startActivity(new Intent(this, CameraActivity.class).putExtra("imagePurpose", "Regular"));
                break;
        }
    }

    @Override
    public void onStart(){
        super.onStart();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu_inflater, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
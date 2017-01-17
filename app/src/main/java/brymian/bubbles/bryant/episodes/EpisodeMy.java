package brymian.bubbles.bryant.episodes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import brymian.bubbles.R;

public class EpisodeMy extends AppCompatActivity {
    Toolbar mToolbar;
    ViewPager viewPager;
    TabLayout tabLayout;

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.episode_my);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.My_Episodes);
        mToolbar.setTitleTextColor(Color.WHITE);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_star_white_24dp), 0, true);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_run_black_24dp), 1, false);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        EpisodeMyPagerAdapter pagerAdapter = new EpisodeMyPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mToolbar.setTitle(R.string.Hosting);
                    tabLayout.getTabAt(0).setIcon(R.mipmap.ic_star_white_24dp);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.ic_run_black_24dp);
                }
                else if(position == 1){
                    mToolbar.setTitle(R.string.Attending);
                    tabLayout.getTabAt(0).setIcon(R.mipmap.ic_star_black_24dp);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.ic_run_white_24dp);
                }

            }

        });
        viewPager.setCurrentItem(0);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                try {
                    viewPager.setCurrentItem(index);
                    if(index == 0){
                        tab.setIcon(R.mipmap.ic_star_white_24dp);
                    }else if(index == 1) {
                        tab.setIcon(R.mipmap.ic_run_white_24dp);
                    }
                } catch (NullPointerException | IllegalStateException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                if(index == 0){
                    tab.setIcon(R.mipmap.ic_star_black_24dp);
                }else if(index == 1){
                    tab.setIcon(R.mipmap.ic_run_black_24dp);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

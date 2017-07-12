package com.brymian.boppo.bryant.search;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.brymian.boppo.R;

public class SearchActivity extends AppCompatActivity{
    Toolbar mToolbar;
    public static EditText etSearch;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.setHint(R.string.Search_Users);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_people_white_24dp), 0, true);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_my_location_black_24dp), 1, false);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final SearchActivityPagerAdapter pagerAdapter= new SearchActivityPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount() ) ;
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
                if (position == 0) {
                    etSearch.setHint(R.string.Search_Users);
                    tabLayout.getTabAt(0).setIcon(R.mipmap.ic_people_white_24dp);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.ic_my_location_black_24dp);

                }
                else if (position == 1) {
                    etSearch.setHint(R.string.Search_Episodes);
                    tabLayout.getTabAt(0).setIcon(R.mipmap.ic_people_black_24dp);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.ic_my_location_white_24dp);
                }
            }

        });
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                viewPager.setCurrentItem(tab.getPosition());
                try {
                    if(index == 0){
                        tab.setIcon(R.mipmap.ic_people_white_24dp);
                    }
                    else if(index == 1){
                        tab.setIcon(R.mipmap.ic_my_location_white_24dp);
                    }
                } catch (NullPointerException | IllegalStateException npe) {
                    npe.printStackTrace();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                if(index == 0){
                    tab.setIcon(R.mipmap.ic_people_black_24dp);
                }
                else if(index == 1){
                    tab.setIcon(R.mipmap.ic_my_location_black_24dp);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu_inflater, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.cancel:
                etSearch.setText("");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

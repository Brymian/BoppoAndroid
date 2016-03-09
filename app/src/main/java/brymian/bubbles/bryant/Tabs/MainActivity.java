package brymian.bubbles.bryant.Tabs;

/**
 * Created by Almanza on 3/3/2016.
 */
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.support.v7.widget.SearchView;
import android.widget.Toast;

import brymian.bubbles.R;
import brymian.bubbles.bryant.MapsActivity;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.ChangePassword;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.SyncFacebook;
import brymian.bubbles.bryant.MenuButtons.AccountButtons.VerifyEmail;
import brymian.bubbles.bryant.MenuButtons.ProfileButtons.Blocking;
import brymian.bubbles.bryant.MenuButtons.ProfileButtons.Privacy;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.bryant.profile.ProfileActivity;
import brymian.bubbles.bryant.MenuButtons.ProfileButtons.ProfileBackground;
import brymian.bubbles.bryant.MenuButtons.SettingsButtons.About;
import brymian.bubbles.bryant.MenuButtons.SettingsButtons.Notifications;
import brymian.bubbles.bryant.MenuButtons.SocialButtons.FriendsList;
import brymian.bubbles.damian.activity.AuthenticateActivity;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    DrawerLayout drawerLayout;
    ListView listView;
    String[] menu_items;
    ActionBarDrawerToggle drawerListener;
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        menu_items = getResources().getStringArray(R.array.menu_items);
        listView = (ListView) findViewById(R.id.drawerList);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menu_items));
        listView.setOnItemClickListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.string.save, R.string.cancel){
            @Override
            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView){
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerListener);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.globeblackwhite_nopadding), 0, false);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.news_feed_black_and_white_no_padding), 1, true);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.friendslist_nopadding), 2, false);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final brymian.bubbles.bryant.Tabs.PagerAdapter adapter= new brymian.bubbles.bryant.Tabs.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount() ) ;
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {

                /*
             @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

             }
             */

             @Override public void onPageSelected(int position) {
                 if (position == 0){
                    setTitle("Explore");
                 }
                 else if (position == 1){
                    setTitle("News Feed");
                 }
                 else if(position == 2){
                    setTitle("Events");
                 }
                 mToolbar.setTitleTextColor(Color.BLACK);
             }
        });

        viewPager.setCurrentItem(1);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                viewPager.setCurrentItem(index);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @Override
    public void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        switch (position){
            /* My Profile */
            case 0:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            /* Profile Pictures */
            case 1:
                startActivity(new Intent(this, ProfileBackground.class));
                break;
            /* My Map */
            case 2:
                startActivity(new Intent(this, MapsActivity.class));
                break;
            /* Friends */
            case 3:
                startActivity(new Intent(this, FriendsList.class));
                break;
            /* Privacy */
            case 4:
                startActivity(new Intent(this, Privacy.class));
                break;
            /* Change Password */
            case 5:
                startActivity(new Intent(this, ChangePassword.class));
                break;
            /* Email */
            case 6:
                startActivity(new Intent(this, VerifyEmail.class));
                break;
            /* Sync With Facebook */
            case 7:
                startActivity(new Intent(this, SyncFacebook.class));
                break;
            /* Blocking */
            case 8:
                startActivity(new Intent(this, Blocking.class));
                break;
            /* Notifications */
            case 9:
                startActivity(new Intent(this, Notifications.class));
                break;
            /* About */
            case 10:
                startActivity(new Intent(this, About.class));
                break;
            case 11:
                displayAlertDialog();
                break;
            default:
                Toast.makeText(MainActivity.this, "Something is wrong.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inflater, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Perform the final search here
                Toast.makeText(MainActivity.this, "text submit", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Text has changed; apply filter here
                Toast.makeText(MainActivity.this, "text change", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //Can be replaced with getComponentName()

        //if this searchable activity is the current activity
        ComponentName componentName = new ComponentName(this, MainActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if(drawerListener.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()){
            case R.id.search:

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void displayAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.logout, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(MainActivity.this, AuthenticateActivity.class));
                SaveSharedPreference.clearUsername(getApplicationContext());
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}

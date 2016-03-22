package brymian.bubbles.bryant.Tabs;

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

import android.support.v7.widget.SearchView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.ArrayList;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.account.Blocking;
import brymian.bubbles.bryant.account.Notifications;
import brymian.bubbles.bryant.account.ChangePassword;
import brymian.bubbles.bryant.account.SyncFacebook;
import brymian.bubbles.bryant.account.VerifyEmail;
import brymian.bubbles.bryant.camera.CameraActivity;
import brymian.bubbles.bryant.events.EventsCreate;
import brymian.bubbles.bryant.profile.Privacy;
import brymian.bubbles.bryant.events.EventsCurrent;
import brymian.bubbles.bryant.events.EventsTop;
import brymian.bubbles.bryant.events.EventsYours;
import brymian.bubbles.bryant.navigationDrawer.CustomDrawerAdapter;
import brymian.bubbles.bryant.navigationDrawer.DrawerItem;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.bryant.profile.FriendsList;
import brymian.bubbles.bryant.profile.ProfileActivity;
import brymian.bubbles.bryant.profile.pictures.ProfilePicturesActivity;
import brymian.bubbles.bryant.profile.pictures.ProfilePicturesAdapter;
import brymian.bubbles.damian.activity.AuthenticateActivity;
import brymian.bubbles.damian.nonactivity.ServerRequest.Callback.UserListCallback;
import brymian.bubbles.damian.nonactivity.ServerRequestMethods;
import brymian.bubbles.damian.nonactivity.User;


public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    CustomDrawerAdapter adapter;

    List<DrawerItem> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        /** -------------------------------------------------------------------------------------**/
        /** ------------------ Initializing all drawer layouts and ListView -------------------- **/
        /** -------------------------------------------------------------------------------------**/
        /* Following the tutorial from this website for custom navigation drawer:   http://www.tutecentral.com/android-custom-navigation-drawer/    */
        /* YouTube video associated with the link above:                            https://www.youtube.com/watch?v=zia_vSgYw8s                     */
        dataList = new ArrayList<DrawerItem>();
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        /* not implementing setDrawerLayout() yet */
        //mDrawerLayout.setDrawerShadow();

        /* Add drawer items to dataList here */
        dataList.add(new DrawerItem(true));// adding a spinner to the list

        /* Profile */
        dataList.add(new DrawerItem(this.getString(R.string.Profile)/* you can also use getResources.getString(R.string.profile_activity)*/)); // adding a header to the list
        dataList.add(new DrawerItem(this.getString(R.string.My_Profile), R.mipmap.friendslist_nopadding));
        dataList.add(new DrawerItem(this.getString(R.string.Profile_Pictures), R.mipmap.friendslist_nopadding));
        dataList.add(new DrawerItem(this.getString(R.string.Privacy), R.mipmap.friendslist_nopadding));
        dataList.add(new DrawerItem(this.getString(R.string.Friends), R.mipmap.friendslist_nopadding));

        /* Events */
        dataList.add(new DrawerItem(this.getString(R.string.Episodes)));// adding a header to the list
        dataList.add(new DrawerItem(this.getString(R.string.My_Episodes), R.mipmap.friendslist_nopadding));
        dataList.add(new DrawerItem(this.getString(R.string.Top_Episodes), R.mipmap.friendslist_nopadding));
        dataList.add(new DrawerItem(this.getString(R.string.Live_Episodes), R.mipmap.friendslist_nopadding));
        dataList.add(new DrawerItem(this.getString(R.string.Create_Episode), R.mipmap.friendslist_nopadding));

        /* Settings */

        /* Account */
        dataList.add(new DrawerItem(this.getString(R.string.Account))); // adding a header to the list
        dataList.add(new DrawerItem(this.getString(R.string.Password), R.mipmap.friendslist_nopadding));
        dataList.add(new DrawerItem(this.getString(R.string.Email), R.mipmap.friendslist_nopadding));
        dataList.add(new DrawerItem(this.getString(R.string.Notifications), R.mipmap.friendslist_nopadding));
        dataList.add(new DrawerItem(this.getString(R.string.Blocking), R.mipmap.friendslist_nopadding));
        dataList.add(new DrawerItem(this.getString(R.string.Sync_With_Facebook), R.mipmap.friendslist_nopadding));
        dataList.add(new DrawerItem(this.getString(R.string.Log_Out), R.mipmap.friendslist_nopadding));

        /* End of adding items to dataList */

        adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item, dataList);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.drawer_open,
                R.string.drawer_closed) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);//gonna be deprecated, change to addDrawerListener

        /**--------------------------------------------------------------------------------------**/
        /**------------------------------------------------------------ -------------------------**/
        /**--------------------------------------------------------------------------------------**/

        if (savedInstanceState == null) {
            if (dataList.get(0).isSpinner() & dataList.get(1).getTitle() != null) {
                /* found error here: every time MainActivity starts, this if statement becomes true, commenting it out for now */
                //SelectItem(2);
            }
            else if (dataList.get(0).getTitle() != null) {
                SelectItem(1);
            }
            else {
                SelectItem(0);
            }
        }
        /**--------------------------------------------------------------------------------------**/
        /**--------------------------- Initializing TabLayout and Tabs --------------------------**/
        /**--------------------------------------------------------------------------------------**/
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.globeblackwhite_nopadding), 0, false);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.news_feed_black_and_white_no_padding), 1, true);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.friendslist_nopadding), 2, false);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final brymian.bubbles.bryant.Tabs.PagerAdapter pagerAdapter= new brymian.bubbles.bryant.Tabs.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount() ) ;
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    setTitle(R.string.Explore);
                } else if (position == 1) {
                    setTitle(R.string.News_Feed);
                } else if (position == 2) {
                    setTitle(R.string.Episodes);
                }
                mToolbar.setTitleTextColor(Color.BLACK);
            }
        });
        viewPager.setCurrentItem(0); //setCurrentItem(position) sets which tab MainActivity starts in
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
        /**--------------------------------------------------------------------------------------**/
        /**--------------------------------------------------------------------------------------**/
        /**--------------------------------------------------------------------------------------**/

        ImageView icon = new ImageView(this);
        icon.setImageResource(R.mipmap.camera_nopadding);
        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
            }
        });
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onStart(){
        super.onStart();
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
                Toast.makeText(MainActivity.this, "text submit: " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Text has changed; apply filter here
                new ServerRequestMethods(MainActivity.this).getUsers(newText, new UserListCallback() {
                    @Override
                    public void done(List<User> users) {
                        try {
                            Toast.makeText(MainActivity.this, "users.size(): " + users.size(), Toast.LENGTH_SHORT).show();
                            View view = getLayoutInflater().inflate(R.layout.searched_results, )
                        }
                        catch (NullPointerException npe){
                            npe.printStackTrace();
                        }
                    }
                });

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
        if(mDrawerToggle.onOptionsItemSelected(item)){
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
        mDrawerToggle.onConfigurationChanged(newConfig);
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
        super.onBackPressed();
    }

    public void SelectItem(int position) {
        switch (position) {
            /* Profile */
            case 2:
                startActivity(new Intent(this, ProfileActivity.class).putExtra("profile", "logged in user"));
                break;
            case 3:
                startActivity(new Intent(this, ProfilePicturesActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, Privacy.class));
                break;
            case 5:
                startActivity(new Intent(this, FriendsList.class));
                break;
            /* Events */
            case 6:
                /* This is the position of Events title. */
                break;
            case 7:
                startActivity(new Intent(this, EventsYours.class));
                break;
            case 8:
                startActivity(new Intent(this, EventsTop.class));
                break;
            case 9:
                startActivity(new Intent(this, EventsCurrent.class));
                break;
            case 10:
                startActivity(new Intent(this, EventsCreate.class));
                break;
            case 11:
                /* this is the position of Account title */
                break;
            case 12:
                startActivity(new Intent(this, ChangePassword.class));
                break;
            case 13:
                startActivity(new Intent(this, VerifyEmail.class));
                break;
            case 14:
                startActivity(new Intent(this, Notifications.class));
                break;
            case 15:
                startActivity(new Intent(this, Blocking.class));
                break;
            case 16:
                startActivity(new Intent(this, SyncFacebook.class));
                break;
            case 17:
                logOut();
                break;
            default:
                Toast.makeText(MainActivity.this, "default toast boom", Toast.LENGTH_SHORT).show();
                break;
        }
        mDrawerList.setItemChecked(position, true);
        //setTitle(dataList.get(position).getItemName());
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (dataList.get(position).getTitle() == null) {
                SelectItem(position);
            }
        }
    }

    public void logOut() {
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
                SaveSharedPreference.clearAll(getApplicationContext());
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}
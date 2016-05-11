package brymian.bubbles.bryant.main;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
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

import android.widget.ListView;
import android.widget.Toast;


import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import java.util.List;

import brymian.bubbles.R;
import brymian.bubbles.bryant.account.Blocking;
import brymian.bubbles.bryant.account.Notifications;
import brymian.bubbles.bryant.account.ChangePassword;
import brymian.bubbles.bryant.account.SyncWithOtherMedia;
import brymian.bubbles.bryant.account.VerifyEmail;
import brymian.bubbles.bryant.camera.CameraActivity;
import brymian.bubbles.bryant.episodes.EpisodeCreate;
import brymian.bubbles.bryant.pictures.ProfilePicturesActivity2;
import brymian.bubbles.bryant.map.MapsActivity;
import brymian.bubbles.bryant.profile.Privacy;
import brymian.bubbles.bryant.episodes.EpisodeCurrent;
import brymian.bubbles.bryant.episodes.EpisodeTop;
import brymian.bubbles.bryant.episodes.EpisodeMy;
import brymian.bubbles.bryant.navigationDrawer.CustomDrawerAdapter;
import brymian.bubbles.bryant.navigationDrawer.DrawerItem;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.bryant.friends.FriendsList;
import brymian.bubbles.bryant.profile.ProfileActivity;
import brymian.bubbles.bryant.search.SearchActivity;
import brymian.bubbles.damian.activity.AuthenticateActivity;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.startFragment;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    CustomDrawerAdapter adapter;
    ViewPager viewPager;
    TabLayout tabLayout;

    List<DrawerItem> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
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
        dataList.add(new DrawerItem(this.getString(R.string.My_Map), R.mipmap.friendslist_nopadding));
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
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        //mDrawerLayout.setDrawerListener(mDrawerToggle);

        /**--------------------------------------------------------------------------------------**/
        /**------------------------------------------------------------ -------------------------**/
        /**--------------------------------------------------------------------------------------**/

        if (savedInstanceState == null) {
            if (dataList.get(0).isSpinner() & dataList.get(1).getTitle() != null) {
                /* found error here: every time MainActivity starts, this if statement becomes true, commenting it out for now */
                SelectItem(0);
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
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_people_black_24dp), 0, true);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_whatshot_black_24dp), 1, false);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_account_circle_black_24dp), 2, false);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        final MainActivityPagerAdapter pagerAdapter= new MainActivityPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount() ) ;
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    setTitle(R.string.News_Feed);
                    tabLayout.getTabAt(0).setIcon(R.mipmap.ic_people_white_24dp);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.ic_whatshot_black_24dp);
                    tabLayout.getTabAt(2).setIcon(R.mipmap.ic_account_circle_black_24dp);

                } else if (position == 1) {
                    setTitle(R.string.Episodes);
                    tabLayout.getTabAt(0).setIcon(R.mipmap.ic_people_black_24dp);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.ic_whatshot_white_24dp);
                    tabLayout.getTabAt(2).setIcon(R.mipmap.ic_account_circle_black_24dp);
                } else if (position == 2) {
                    setTitle(R.string.Account);
                    tabLayout.getTabAt(0).setIcon(R.mipmap.ic_people_black_24dp);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.ic_whatshot_black_24dp);
                    tabLayout.getTabAt(2).setIcon(R.mipmap.ic_account_circle_white_24dp);
                }
                mToolbar.setTitleTextColor(Color.BLACK);
                /* Profile */
                MainTabAccount.tvMyProfile.setOnClickListener(MainActivity.this);
                MainTabAccount.tvMyEpisodes.setOnClickListener(MainActivity.this);
                MainTabAccount.tvMyMap.setOnClickListener(MainActivity.this);
                MainTabAccount.tvProfilePictures.setOnClickListener(MainActivity.this);
                MainTabAccount.tvFriends.setOnClickListener(MainActivity.this);

                /* Settings */
                MainTabAccount.tvNotifications.setOnClickListener(MainActivity.this);
                MainTabAccount.tvPrivacy.setOnClickListener(MainActivity.this);

                /* Account */
                MainTabAccount.tvPassword.setOnClickListener(MainActivity.this);
                MainTabAccount.tvEmail.setOnClickListener(MainActivity.this);
                MainTabAccount.tvPhoneNumber.setOnClickListener(MainActivity.this);
                MainTabAccount.tvBlocking.setOnClickListener(MainActivity.this);
                MainTabAccount.tvLogOut.setOnClickListener(MainActivity.this);
            }

        });
        viewPager.setCurrentItem(0); //setCurrentItem(position) sets which tab MainActivity starts in
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                try {
                    viewPager.setCurrentItem(index);
                    if(index == 0){
                        tab.setIcon(R.mipmap.ic_people_white_24dp);
                    }else if(index == 1){
                        tab.setIcon(R.mipmap.ic_whatshot_white_24dp);
                    }else if(index == 2){
                        tab.setIcon(R.mipmap.ic_account_circle_white_24dp);
                    }
                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                } catch (IllegalStateException ise) {
                    ise.printStackTrace();
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
                    tab.setIcon(R.mipmap.ic_account_circle_black_24dp);
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        /**--------------------------------------------------------------------------------------**/
        /**--------------------------------------------------------------------------------------**/
        /**--------------------------------------------------------------------------------------**/

        FloatingActionMenu famMenu = (FloatingActionMenu) findViewById(R.id.famMenu);
        famMenu.showMenuButton(true);

        FloatingActionButton fabCamera = (FloatingActionButton) findViewById(R.id.fabCamera);
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CameraActivity.class).putExtra("imagePurpose", "Regular"));
            }
        });

        FloatingActionButton fabMap = (FloatingActionButton) findViewById(R.id.fabMap);
        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });

        FloatingActionButton fabMyEpisodes = (FloatingActionButton) findViewById(R.id.fabMyEpisodes);
        fabMyEpisodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EpisodeMy.class));
            }
        });

    }

    @Override
    public void onClick(View view) {
        FragmentManager fm = getFragmentManager();
        switch (view.getId()){
            /*---------------------------------MainTabAccount-------------------------------------*/
            /* Profile */
            case R.id.tvMyProfile:

                break;

            case R.id.tvMyEpisodes:

                break;

            case R.id.tvMyMap:

                break;

            case R.id.tvProfilePictures:

                break;

            case R.id.tvFriends:
                //startFragment(fm, R.id.main_activity, new FriendsList());
                break;

            /* Settings */
            case R.id.tvNotifications:
                startFragment(fm, R.id.main_activity, new Notifications());
                break;

            case R.id.tvPrivacy:

                break;

            /* Account */
            case R.id.tvPassword:
                startFragment(fm, R.id.main_activity, new ChangePassword());
                break;

            case R.id.tvEmail:
                startFragment(fm, R.id.main_activity, new VerifyEmail());
                break;

            case R.id.tvPhoneNumber:

                break;

            case R.id.tvBlocking:

                break;

            case R.id.tvSyncWithOtherMedia:
                startFragment(fm, R.id.main_activity, new SyncWithOtherMedia());
                break;

            case R.id.tvLogOut:
                logOut();
                break;


        }
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
        getMenuInflater().inflate(R.menu.main_activity_menu_inflater, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()){
            case R.id.search:
                startActivity(new Intent(this, SearchActivity.class));
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
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
            Toast.makeText(MainActivity.this, "doesnt work", Toast.LENGTH_SHORT).show();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    public void SelectItem(int position) {
        switch (position) {
            /* Profile */
            case 2:
                startActivity(new Intent(this, ProfileActivity.class).putExtra("profile", "logged in user"));
                break;
            case 3:
                startActivity(new Intent(this, MapsActivity.class).putExtra("profile", "logged in user"));
                break;
            case 4:
                startActivity(new Intent(this, ProfilePicturesActivity2.class));
                break;
            case 5:
                startActivity(new Intent(this, Privacy.class));
                break;
            case 6:
                startActivity(new Intent(this, FriendsList.class)
                        .putExtra("uid", SaveSharedPreference.getUserUID(this))
                        .putExtra("profile", "logged in user"));
                break;
            /* Events */
            case 7:
                /* This is the position of Events title. */
                break;
            case 8:
                startActivity(new Intent(this, EpisodeMy.class));
                break;
            case 9:
                startActivity(new Intent(this, EpisodeTop.class));
                break;
            case 10:
                startActivity(new Intent(this, EpisodeCurrent.class));
                break;
            case 11:
                startActivity(new Intent(this, EpisodeCreate.class));
                break;
            case 12:
                /* this is the position of Account title */
                break;
            case 13:
                startActivity(new Intent(this, ChangePassword.class));
                break;
            case 14:
                startActivity(new Intent(this, VerifyEmail.class));
                break;
            case 15:
                startActivity(new Intent(this, Notifications.class));
                break;
            case 16:
                startActivity(new Intent(this, Blocking.class));
                break;
            case 17:
                startActivity(new Intent(this, SyncWithOtherMedia.class));
                break;
            case 18:
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

    private void logOut() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alert_dialog_log_out, null);
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
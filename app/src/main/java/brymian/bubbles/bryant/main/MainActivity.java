package brymian.bubbles.bryant.main;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import brymian.bubbles.R;
import brymian.bubbles.bryant.account.PhoneNumber;
import brymian.bubbles.bryant.search.SearchActivity;
import brymian.bubbles.bryant.settings.About;
import brymian.bubbles.bryant.settings.blocking.Blocking;
import brymian.bubbles.bryant.settings.Notifications;
import brymian.bubbles.bryant.account.Password;
import brymian.bubbles.bryant.account.SyncWithOtherMedia;
import brymian.bubbles.bryant.account.Email;
import brymian.bubbles.bryant.camera.CameraActivity;
import brymian.bubbles.bryant.pictures.ProfilePicturesActivity2;
import brymian.bubbles.bryant.map.MapsActivity;
import brymian.bubbles.bryant.settings.Privacy;
import brymian.bubbles.bryant.episodes.EpisodeMy;
import brymian.bubbles.bryant.nonactivity.SaveSharedPreference;
import brymian.bubbles.damian.activity.AuthenticateActivity;

import static brymian.bubbles.damian.nonactivity.Miscellaneous.startFragment;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar mToolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

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
                    mToolbar.setTitle(R.string.News_Feed);
                    tabLayout.getTabAt(0).setIcon(R.mipmap.ic_people_white_24dp);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.ic_whatshot_black_24dp);
                    tabLayout.getTabAt(2).setIcon(R.mipmap.ic_account_circle_black_24dp);

                } else if (position == 1) {
                    mToolbar.setTitle(R.string.Episodes);
                    tabLayout.getTabAt(0).setIcon(R.mipmap.ic_people_black_24dp);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.ic_whatshot_white_24dp);
                    tabLayout.getTabAt(2).setIcon(R.mipmap.ic_account_circle_black_24dp);
                } else if (position == 2) {
                    mToolbar.setTitle(R.string.Personal);
                    tabLayout.getTabAt(0).setIcon(R.mipmap.ic_people_black_24dp);
                    tabLayout.getTabAt(1).setIcon(R.mipmap.ic_whatshot_black_24dp);
                    tabLayout.getTabAt(2).setIcon(R.mipmap.ic_account_circle_white_24dp);
                }
                mToolbar.setTitleTextColor(Color.BLACK);
                /* Profile */
                MainTabPersonal.tvMyProfile.setOnClickListener(MainActivity.this);
                MainTabPersonal.tvMyEpisodes.setOnClickListener(MainActivity.this);
                MainTabPersonal.tvMyMap.setOnClickListener(MainActivity.this);
                MainTabPersonal.tvProfilePictures.setOnClickListener(MainActivity.this);
                MainTabPersonal.tvFriends.setOnClickListener(MainActivity.this);

                /* Settings */
                MainTabPersonal.tvNotifications.setOnClickListener(MainActivity.this);
                MainTabPersonal.tvPrivacy.setOnClickListener(MainActivity.this);
                //MainTabPersonal.tvMedia.setOnClickListener(MainActivity.this);
                MainTabPersonal.tvBlocking.setOnClickListener(MainActivity.this);
                MainTabPersonal.tvAbout.setOnClickListener(MainActivity.this);
                /* Account */
                MainTabPersonal.tvPassword.setOnClickListener(MainActivity.this);
                MainTabPersonal.tvEmail.setOnClickListener(MainActivity.this);
                MainTabPersonal.tvPhoneNumber.setOnClickListener(MainActivity.this);
                MainTabPersonal.tvSyncWithOtherMedia.setOnClickListener(MainActivity.this);
                MainTabPersonal.tvLogOut.setOnClickListener(MainActivity.this);
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
                    tab.setIcon(R.mipmap.ic_account_circle_black_24dp);
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
            /*---------------------------------MainTabPersonal-------------------------------------*/
            /* Profile */
            case R.id.tvMyProfile:

                break;

            case R.id.tvMyEpisodes:

                break;

            case R.id.tvMyMap:

                break;

            case R.id.tvProfilePictures:
                startActivity(new Intent(this, ProfilePicturesActivity2.class));
                break;

            case R.id.tvFriends:
                //startFragment(fm, R.id.main_activity, new FriendsList());
                break;

            /* Settings */
            case R.id.tvNotifications:
                startFragment(fm, R.id.main_activity, new Notifications());
                break;

            case R.id.tvPrivacy:
                startFragment(fm, R.id.main_activity, new Privacy());
                break;

            case R.id.tvBlocking:
                startFragment(fm, R.id.main_activity, new Blocking());
                break;

            case R.id.tvAbout:
                startFragment(fm, R.id.main_activity, new About());
                break;

            /* Account */
            case R.id.tvPassword:
                startFragment(fm, R.id.main_activity, new Password());
                break;

            case R.id.tvEmail:

                startFragment(fm, R.id.main_activity, new Email());
                break;

            case R.id.tvPhoneNumber:
                startFragment(fm, R.id.main_activity, new PhoneNumber());
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
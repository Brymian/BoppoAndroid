package brymian.bubbles.bryant.Tabs;

/**
 * Created by Almanza on 3/3/2016.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import brymian.bubbles.R;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    TextView tvTitle;
    DrawerLayout drawerLayout;
    ListView listView;
    String[] planets, random;
    ActionBarDrawerToggle drawerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //tvTitle = (TextView) findViewById(R.id.tvTitle);
        //tvTitle.setText("Explore");

        planets = getResources().getStringArray(R.array.planets);
        random = getResources().getStringArray(R.array.random);
        listView = (ListView) findViewById(R.id.drawerList);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, planets));
        //listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, random));
        listView.setOnItemClickListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.mipmap.menu_icon_black_nopadd, R.string.save, R.string.cancel){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Toast.makeText(MainActivity.this, "open", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Toast.makeText(MainActivity.this, "close", Toast.LENGTH_SHORT).show();

            }
        };
        drawerLayout.setDrawerListener(drawerListener);
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.globeblackwhite_nopadding), 0, false);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.news_feed_black_and_white_no_padding), 1, true);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.friendslist_nopadding), 2, false);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        //final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        final brymian.bubbles.bryant.Tabs.PagerAdapter adapter= new brymian.bubbles.bryant.Tabs.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount() ) ;
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();
                viewPager.setCurrentItem(index);
                /**

                if(index == 1){
                    tvTitle = (TextView) findViewById(R.id.tvTitle);
                    tvTitle.setText("News Feed");
                }
                else if(index == 2){
                    tvTitle = (TextView) findViewById(R.id.tvTitle);
                    tvTitle.setText("Events");
                }
                else{
                    tvTitle = (TextView) findViewById(R.id.tvTitle);
                    tvTitle.setText("Explore");
                }
                 **/
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
    public void onStart(){
        super.onStart();
        //setTitle("Explore");

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        Toast.makeText(MainActivity.this, planets[position]+ " was selected", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
        int id = item.getItemId();
        if (id == R.id.action_settings) {
             return true;
        }
         **/

        return super.onOptionsItemSelected(item);
    }
}

package com.eightbit.fott.ui;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.eightbit.fott.R;
import com.eightbit.fott.data.database.AppDatabase;
import com.eightbit.fott.ui.fragment.FOTTFeedFragment;
import com.eightbit.fott.ui.fragment.PeopleFragment;
import com.eightbit.fott.ui.fragment.TripsFragment;

public class MainActivity extends AppCompatActivity {

    private static final int LAYOUT_ID = R.layout.activity_main;
    private Activity activity;

    //Database
    private AppDatabase appDatabase;

    //Main layout properties
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT_ID);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity = this;

        //Init database
        appDatabase = AppDatabase.getAppDatabase(this);

        //Init main layout properties
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menu = navigationView.getMenu();

        // Initializing Toolbar and setting it as the actionbar
        toolbar = drawerLayout.findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(2.0f);
        }

        String title = drawerLayout.getContext().getString(R.string.fott_feed_title);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);

        initEvent();

        loadDefaultScreen();
    }

    private void initEvent() {

        //Toggle drawer
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
                activity.invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
                activity.invalidateOptionsMenu();
            }
        };
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    DrawerLayout drawer = findViewById(R.id.drawer_layout);
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        drawer.openDrawer(GravityCompat.START);
                    }
            }
        });

        //Setting the actionbarToggle to drawer layout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        //menu item click
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.fott_feed:
                        //Show FOTT feed fragment
                        FOTTFeedFragment fottFeedFragment = new FOTTFeedFragment();
                        replaceFragment(getString(R.string.fott_feed_title), fottFeedFragment);
                        break;
                    case R.id.trips:
                        //Show trips fragment
                        TripsFragment tripsFragment = new TripsFragment();
                        replaceFragment(getString(R.string.trips_title), tripsFragment);
                        break;
                    case R.id.people:
                        //Show people fragment
                        PeopleFragment peopleFragment = new PeopleFragment();
                        replaceFragment(getString(R.string.people_title), peopleFragment);
                        break;
                    default:
                        break;

                }
                return true;
            }
        });
    }


    private void loadDefaultScreen() {
        //FOTT feed fragment as defualt screen
        FOTTFeedFragment fottFeedFragment = new FOTTFeedFragment();
        replaceFragment(getString(R.string.fott_feed_title), fottFeedFragment);
    }

    private void replaceFragment(String title, Fragment fragment) {
        toolbar.setTitle(title);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}

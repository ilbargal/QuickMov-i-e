package com.finalandroidproject.quickmovie;
import android.app.Activity;
import android.app.ActionBar;
import android.app.FragmentTransaction;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.finalandroidproject.quickmovie.Activities.NavigationActivity;
import com.finalandroidproject.quickmovie.Fragments.ContactsFragment;
import com.finalandroidproject.quickmovie.Fragments.FriendFragment;
import com.finalandroidproject.quickmovie.Fragments.InvitationFragment;
import com.finalandroidproject.quickmovie.Fragments.MovieFragment;
import com.finalandroidproject.quickmovie.Fragments.SelectMovieFragment;
import com.finalandroidproject.quickmovie.Model.Movie;

public class MainActivity extends Activity implements ActionBar.TabListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab()
                            .setIcon(mSectionsPagerAdapter.getTabIcon(i))
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FriendFragment();
                case 1:
                    return new InvitationFragment();
                case 2:
                    return new MovieFragment();
                case 3:
                    return new ContactsFragment();
                default:
                    return new MovieFragment();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "חברים";
                case 1:
                    return "הזמנות";
                case 2:
                    return "סרטים";
                case 3:
                    return "אנשי קשר";
            }
            return null;
        }

        public int getTabIcon(int position) {
            switch (position) {
                case 0:
                    return R.mipmap.ic_face_black_24dp;
                case 1:
                    return R.mipmap.ic_chat_bubble_black_24dp;
                case 2:
                    return R.mipmap.ic_movie_black_24dp;
                case 3:
                    return R.mipmap.ic_account_circle_black_24dp;
            }
            return R.mipmap.ic_launcher;
        }
    }
}

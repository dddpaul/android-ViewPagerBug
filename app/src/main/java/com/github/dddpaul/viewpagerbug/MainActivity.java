package com.github.dddpaul.viewpagerbug;

import java.util.Locale;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener
{
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_TABS );

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager() );

        // Set up the ViewPager with the sections adapter in portrait mode
        if( getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ) {
            mViewPager = (ViewPager) findViewById( R.id.pager );
            mViewPager.setAdapter( mSectionsPagerAdapter );
            mViewPager.setOnPageChangeListener( new ViewPager.SimpleOnPageChangeListener()
            {
                @Override
                public void onPageSelected( int position )
                {
                    actionBar.setSelectedNavigationItem( position );
                }
            } );

            // For each of the sections in the app, add a tab to the action bar.
            for( int i = 0; i < mSectionsPagerAdapter.getCount(); i++ ) {
                actionBar.addTab(
                        actionBar.newTab()
                                .setText( mSectionsPagerAdapter.getPageTitle( i ) )
                                .setTabListener( this )
                );
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        if( mViewPager != null ) {
            mViewPager.removeAllViews();
            mViewPager.setAdapter( null );
            mViewPager = null;
        }
        super.onDestroy();
    }

    @Override
    public void onTabSelected( ActionBar.Tab tab, FragmentTransaction fragmentTransaction )
    {
        if( mViewPager != null ) {
            mViewPager.setCurrentItem( tab.getPosition() );
        }
    }

    @Override
    public void onTabUnselected( ActionBar.Tab tab, FragmentTransaction fragmentTransaction )
    {
    }

    @Override
    public void onTabReselected( ActionBar.Tab tab, FragmentTransaction fragmentTransaction )
    {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        public SectionsPagerAdapter( FragmentManager fm )
        {
            super( fm );
        }

        @Override
        public Fragment getItem( int position )
        {
            return PlaceholderFragment.newInstance();
        }

        @Override
        public int getCount()
        {
            return 2;
        }

        @Override
        public CharSequence getPageTitle( int position )
        {
            Locale l = Locale.getDefault();
            switch( position ) {
                case 0:
                    return getString( R.string.title_section1 ).toUpperCase( l );
                case 1:
                    return getString( R.string.title_section2 ).toUpperCase( l );
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment
    {
        public static PlaceholderFragment newInstance()
        {
            return new PlaceholderFragment();
        }

        public PlaceholderFragment()
        {
        }

        @Override
        public View onCreateView( LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState )
        {
            View rootView = inflater.inflate( R.layout.fragment_main, container, false );
            TextView view = (TextView) rootView.findViewById( R.id.section_label );
            view.setText( "Number of fragments = " + getFragmentManager().getFragments().size());
            return rootView;
        }
    }
}

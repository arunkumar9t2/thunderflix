/*
 * Copyright 2015.  Arunkumar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package arun.com.popularmovies;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import arun.com.popularmovies.fragments.MovieGridFragment;
import arun.com.popularmovies.tasks.MovieFetcherTask;

public class MainActivity extends AppCompatActivity {

    public final static String API = BuildConfig.MOVIEDB_API_KEY;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private final String[] mSections = new String[]{
                getString(R.string.most_popular),
                getString(R.string.higly_rated),
                getString(R.string.highest_grossing)
        };

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MovieGridFragment.newInstance(MovieFetcherTask.SORT_TYPE_POPULAR);
                case 1:
                    return MovieGridFragment.newInstance(MovieFetcherTask.SORT_TYPE_RATED);
                case 2:
                    return MovieGridFragment.newInstance(MovieFetcherTask.SORT_TYPE_GROSSING);
            }
            return null;
        }

        @Override
        public int getCount() {
            return mSections.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mSections[position];
        }
    }
}

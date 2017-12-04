package com.smartysoft.receipeapp;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smartysoft.receipeapp.fragment.LatestMovieFragment;

public class MainActivity extends BaseActivity implements  LatestMovieFragment.fragmentActivityCommunicator{

    private static final String TAG_USER_CATEGORY_FRAGMENT = "user_category_fragment";
    private LatestMovieFragment fragmentUserCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();


        if (savedInstanceState == null) {
            // The Activity is NOT being re-created so we can instantiate a new Fragment
            // and add it to the Activity
            fragmentUserCategory = new LatestMovieFragment();

            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction
                    // It's almost always a good idea to use .replace instead of .add so that
                    // you never accidentally layer multiple Fragments on top of each other
                    // unless of course that's your intention
                    .replace(R.id.content_layout, fragmentUserCategory, TAG_USER_CATEGORY_FRAGMENT)
                    .commit();
        } else {
            // The Activity IS being re-created so we don't need to instantiate the Fragment or add it,
            // but if we need a reference to it, we can use the tag we passed to .replace
            fragmentUserCategory = (LatestMovieFragment) getSupportFragmentManager().findFragmentByTag(TAG_USER_CATEGORY_FRAGMENT);
        }
    }





    @Override
    public void changeMenuIcon(int id) {

    }
}

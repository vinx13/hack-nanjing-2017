package me.hacknanjing.activity;

import android.graphics.Color;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import butterknife.BindView;

import me.hacknanjing.fragment.FriendsFragment;
import me.hacknanjing.fragment.MeFragment;
import me.hacknanjing.fragment.NowFragment;
import me.hacknanjing.R;
/**
 * Created by Vincent on 2017/5/13.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.container)
    ViewPager viewPager;

    SectionsPagerAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        initTabs();
    }

    private void initTabs() {
        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int currentPage = tab.getPosition();

                //updateToolbar(currentPage);
                viewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        final int PageCount = 3;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MeFragment.newInstance();
                case 2:
                    return FriendsFragment.newInstance();
                case 1:
                    return NowFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return PageCount;
        }
    }
}

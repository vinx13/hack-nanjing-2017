package me.hacknanjing.activity;

import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;


import butterknife.BindView;
import me.hacknanjing.R;
import me.hacknanjing.fragment.FriendsFragment;
import me.hacknanjing.fragment.MeFragment;
import me.hacknanjing.fragment.NowFragment;


/**
 * Created by Vincent on 2017/5/13.
 */

public class MainActivity extends BaseActivity {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.container)
    ViewPager viewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TabLayout.Tab tab = tabLayout.getTabAt(position);
                if (tab != null) tab.select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initTabs();

        MenuItem menu = toolbar.getMenu().add(null);
        menu.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menu.setIcon(R.drawable.message);
        menu.setOnMenuItemClickListener((MenuItem menuItem) -> {
            Intent intent = new Intent(this, MessageActivity.class);
            startActivity(intent);
            return false;
        });
    }

    private void initTabs() {
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));

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


    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        final int PageCount = 3;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MeFragment.newInstance();
                case 1:
                    return NowFragment.newInstance();
                case 2:
                    return FriendsFragment.newInstance();
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

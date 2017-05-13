package me.hacknanjing.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import me.hacknanjing.R;

/**
 * Created by Vincent on 2017/5/13.
 */

public class MainActivity extends BaseActivity {
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    private void initTabs() {

        //Indicator
        View indicatorMe = getLayoutInflater().inflate(R.layout.tab_host, null);
        View indicatorNow = getLayoutInflater().inflate(R.layout.tab_host, null);
        View indicatorFriends = getLayoutInflater().inflate(R.layout.tab_host, null);

        TextView textViewHome = (TextView) indicatorMe.findViewById(R.id.text_tab_title);
        TextView textViewDiscovery = (TextView) indicatorNow.findViewById(R.id.text_tab_title);
        TextView textViewMessage = (TextView) indicatorFriends.findViewById(R.id.text_tab_title);

        textViewHome.setText(R.string.tab_title_me);
        textViewDiscovery.setText(R.string.tab_title_now);
        textViewMessage.setText(R.string.tab_title_friends);

        tabLayout.addTab(tabLayout.newTab().setCustomView(indicatorMe));
        tabLayout.addTab(tabLayout.newTab().setCustomView(indicatorNow));
        tabLayout.addTab(tabLayout.newTab().setCustomView(indicatorFriends));

        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int currentPage = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}

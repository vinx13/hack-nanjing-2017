package me.hacknanjing.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Toast;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.Projection;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

import butterknife.BindView;

import butterknife.ButterKnife;
import me.hacknanjing.R;
import me.hacknanjing.activity.DetailActivity;
import me.hacknanjing.api.model.Post;
import me.hacknanjing.util.Factory;

import static me.hacknanjing.activity.DetailActivity.EXTRA_IS_FRIENDS;
import static me.hacknanjing.activity.DetailActivity.EXTRA_POST_INDEX;

/**
 * Created by Vincent on 2017/5/13.
 */

public class MeFragment extends BaseFragment implements AMap.OnMarkerClickListener {
    @BindView(R.id.map)
    MapView mapView = null;

    private AMap aMap;

    public static MeFragment newInstance() {

        Bundle args = new Bundle();

        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);

        mapView.onCreate(savedInstanceState);
        initMap();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        Log.d("START DETAIL","2");
        Post post = (Post)marker.getObject();
        int index = mockPosts().indexOf(post);
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(EXTRA_POST_INDEX, index);
        intent.putExtra(EXTRA_IS_FRIENDS, false);
        startActivity(intent);
        Toast.makeText(getContext(), "您点击了Marker", Toast.LENGTH_LONG).show();
        return true;
    }

    public void initMap(){
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setOnMarkerClickListener(this);
            addMarkersToMap();
            changeCamera(
                    CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            new LatLng(32.012354, 119.106243), 10, 30, 30)));
        }
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update) {

        aMap.moveCamera(update);

    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap() {
        List<Post> posts = mockPosts();
        for(Post post:posts){
            MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .position(post.getPosition())
                    .draggable(true);
            aMap.addMarker(markerOption).setObject(post);
        }
    }

    private List<Post> mockPosts() {
        return Factory.getInstance().getAllPosts();
    }
}

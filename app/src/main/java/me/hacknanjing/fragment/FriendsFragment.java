package me.hacknanjing.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.hacknanjing.R;
import me.hacknanjing.activity.CameraActivity;
import me.hacknanjing.activity.CardAdapter;
import me.hacknanjing.api.model.Post;
import me.hacknanjing.util.Factory;

/**
 * Created by Vincent on 2017/5/13.
 */

public class FriendsFragment extends BaseFragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;


    public static NowFragment newInstance() {

        Bundle args = new Bundle();

        NowFragment fragment = new NowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        ButterKnife.bind(this, view);


        List<Post> posts = mockPosts();
        //rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(new CardAdapter(getContext(), posts));

        return view;
    }

    private List<Post> mockPosts() {
        return Factory.getInstance().getAllFriendPosts();
    }

}


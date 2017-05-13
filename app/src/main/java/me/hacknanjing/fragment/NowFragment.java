package me.hacknanjing.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import me.hacknanjing.R;

/**
 * Created by Vincent on 2017/5/13.
 */

public class NowFragment extends BaseFragment {
    public static NowFragment newInstance() {

        Bundle args = new Bundle();

        NowFragment fragment = new NowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}

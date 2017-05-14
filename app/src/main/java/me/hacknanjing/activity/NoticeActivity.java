package me.hacknanjing.activity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.hacknanjing.R;
import me.hacknanjing.api.model.Post;
import me.hacknanjing.util.Factory;
import me.hacknanjing.util.MessageListAdapter;
import me.hacknanjing.util.NoticeListAdapter;

/**
 * Created by dynamicheart on 5/14/2017.
 */

public class NoticeActivity extends BaseActivity {
    @BindView(R.id.notice_list_view)
    ListView noticeListView;

    private NoticeListAdapter adapter;
    private List<Post> postList = new ArrayList<Post>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_notice;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPosts();
        adapter = new NoticeListAdapter(this, R.layout.item_notice, postList);
        noticeListView.setAdapter(adapter);
    }

    public void initPosts(){
        postList = Factory.getInstance().getAllPosts();
    }
}

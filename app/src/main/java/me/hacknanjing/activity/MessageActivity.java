package me.hacknanjing.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.hacknanjing.R;
import me.hacknanjing.api.model.Message;
import me.hacknanjing.util.Factory;
import me.hacknanjing.util.MessageListAdapter;

/**
 * Created by Vincent on 2017/5/14.
 */

public class MessageActivity extends BaseActivity {
    @BindView(R.id.message_list_view)
    ListView messageListView;
    @BindView(R.id.text_editor)
    EditText inputText;
    @BindView(R.id.send_button)
    Button send;

    private MessageListAdapter adapter;
    private List<Message> messageList = new ArrayList<Message>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMessage();
        adapter = new MessageListAdapter(this, R.layout.item_message, messageList);
        messageListView.setAdapter(adapter);
    }

    public void initMessage(){
        messageList = Factory.getInstance().getAllMessages();
    }
}

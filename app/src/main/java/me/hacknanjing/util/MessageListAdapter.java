package me.hacknanjing.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.hacknanjing.R;
import me.hacknanjing.api.model.Message;

/**
 * Created by dynamicheart on 5/14/2017.
 */

public class MessageListAdapter extends ArrayAdapter<Message>{
    private int resourceId;

    public MessageListAdapter(Context context, int textViewResourceId, List<Message> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
            viewHolder.rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
            viewHolder.leftAvatar = (ImageView)view.findViewById(R.id.left_avatar);
            viewHolder.leftMessage = (TextView) view.findViewById(R.id.left_message);
            viewHolder.leftPhoto = (ImageView) view.findViewById(R.id.left_photo);
            viewHolder.rightAvatar = (ImageView) view.findViewById(R.id.right_avatar);
            viewHolder.rightMessage = (TextView) view.findViewById(R.id.right_message);
            viewHolder.rightPhoto = (ImageView)view.findViewById(R.id.right_photo);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if (message.getType() == Message.TYPE_RECEIVED) {
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.leftAvatar.setImageResource(message.getUser().getAvatar());
            if(message.getContentType() == Message.CONTENT_PHOTO){
                Picasso.with(getContext()).load(message.getPhoto()).resize(300,0).into(viewHolder.leftPhoto);
                viewHolder.leftPhoto.setVisibility(View.VISIBLE);
                viewHolder.leftMessage.setVisibility(View.GONE);
            }else{
                viewHolder.leftMessage.setText(message.getContent());
                viewHolder.leftPhoto.setVisibility(View.GONE);
                viewHolder.leftMessage.setVisibility(View.VISIBLE);
            }
        } else if(message.getType() == Message.TYPE_SENT) {
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.rightAvatar.setImageResource(message.getUser().getAvatar());
            if(message.getContentType() == Message.CONTENT_PHOTO){
                Picasso.with(getContext()).load(message.getPhoto()).resize(300,0).into(viewHolder.rightPhoto);
                viewHolder.rightPhoto.setVisibility(View.VISIBLE);
                viewHolder.rightMessage.setVisibility(View.GONE);
            }else{
                viewHolder.rightMessage.setText(message.getContent());
                viewHolder.rightPhoto.setVisibility(View.GONE);
                viewHolder.rightMessage.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }

    class ViewHolder {

        LinearLayout leftLayout;

        LinearLayout rightLayout;

        ImageView leftAvatar;

        TextView leftMessage;

        ImageView leftPhoto;

        ImageView rightAvatar;

        TextView rightMessage;

        ImageView rightPhoto;

    }
}

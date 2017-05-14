package me.hacknanjing.util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.hacknanjing.R;
import me.hacknanjing.activity.DetailActivity;
import me.hacknanjing.activity.MessageActivity;
import me.hacknanjing.api.model.Post;

/**
 * Created by dynamicheart on 5/14/2017.
 */

public class NoticeListAdapter extends ArrayAdapter<Post> {
    private int resourceId;

    public NoticeListAdapter(Context context, int textViewResourceId, List<Post> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Post post = getItem(position);
        View view;
        NoticeListAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new NoticeListAdapter.ViewHolder();
            viewHolder.imageViewAvartar = (CircleImageView) view.findViewById(R.id.message_avatar);
            viewHolder.textUsername = (TextView) view.findViewById(R.id.message_username);
            viewHolder.imageViewPicture = (ImageView) view.findViewById(R.id.message_picture);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (NoticeListAdapter.ViewHolder) view.getTag();
        }
        Picasso.with(getContext()).load(post.getUser().getAvatar()).into(viewHolder.imageViewAvartar);
        viewHolder.textUsername.setText(post.getUser().getUsername());
        Picasso.with(getContext()).load(post.getImage()).into(viewHolder.imageViewPicture);

        view.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MessageActivity.class);
            getContext().startActivity(intent);
        });

        return view;
    }

    class ViewHolder {
        CircleImageView imageViewAvartar;

        TextView textUsername;

        ImageView imageViewPicture;

    }
}

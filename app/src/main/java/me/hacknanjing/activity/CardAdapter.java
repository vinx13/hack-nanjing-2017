package me.hacknanjing.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.hacknanjing.R;
import me.hacknanjing.api.model.Post;

/**
 * Created by Vincent on 2017/5/13.
 */

public class CardAdapter extends RecyclerView.Adapter {
    private List<Post> posts;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;
        @BindView(R.id.iv_body)
        ImageView ivBody;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.iv_like)
        ImageView ivLike;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void updateView(Post post, Context context) {
            tvUsername.setText(post.getUser().getUsername());
            tvContent.setText(post.getContent());
            Picasso.with(context).load(post.getUser().getAvatar()).into(ivAvatar);
            Picasso.with(context).load(post.getImage()).resize(400, 0).into(ivBody);

            ivLike.setOnClickListener(v -> {
                post.setLiked(!post.getLiked());
                updateLike(post, context);
            });
        }

        void updateLike(Post post, Context context) {
            Picasso.with(context).load(
                    post.getLiked() ? R.drawable.liked : R.drawable.like
            ).into(ivLike);
        }
    }

    public CardAdapter(Context context, List<Post> posts) {
        this.posts = posts;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        // TODO

        RecyclerView.ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.updateView(posts.get(position), context);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}

package me.hacknanjing.activity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.hacknanjing.R;
import me.hacknanjing.api.model.Post;

/**
 * Created by Vincent on 2017/5/13.
 */

class CardAdapter extends RecyclerView.Adapter {
    private List<Post> posts;

    public static class ViewHolder extends RecyclerView.ViewHolder {
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

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void updateView(Post post) {
            tvUsername.setText(post.getUser().getUsername());
            tvContent.setText(post.getContent());
        }
    }

    public CardAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        // TODO
        RecyclerView.ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder)holder;
        vh.updateView(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

package me.hacknanjing.util;

import com.amap.api.maps2d.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import me.hacknanjing.R;
import me.hacknanjing.api.model.Post;
import me.hacknanjing.api.model.User;

/**
 * Created by Vincent on 2017/5/13.
 */

public class Factory {
    ArrayList<User> users;
    ArrayList<Post> posts;
    ArrayList<Post> friendPosts;

    private static Factory instance;

    public static Factory getInstance() {
        if (instance == null) instance = new Factory();
        return instance;
    }

    public Factory() {
        users = new ArrayList<>(7);
        users.add(new User("Jason", R.drawable.av1));
        users.add(new User("JB", R.drawable.av2));
        users.add(new User("Hsg", R.drawable.av3));
        users.add(new User("王子尧", R.drawable.av4));
        users.add(new User("马幸颖", R.drawable.av5));
        users.add(new User("Vincent", R.drawable.av6));
        users.add(new User("小姐姐", R.drawable.av7));
        posts = new ArrayList<>(8);
        friendPosts = new ArrayList<>(6);


        posts.add(new Post(randomUser(), R.drawable.p1, "给咱一个地铁站 法师可以玩一天", new LatLng(32.02411, 118.79087)));
        posts.add(new Post(randomUser(), R.drawable.p3, "糖果小屋", new LatLng(32.022964, 118.791986)));
        posts.add(new Post(randomUser(), R.drawable.p4, "书如画", new LatLng(32.079255,118.816898)));
        posts.add(new Post(randomUser(), R.drawable.p5, "梦想在，哪里都是舞台", new LatLng(32.025567, 118.85844)));
        posts.add(new Post(randomUser(), R.drawable.p6, "好耐冇逛深水埗", new LatLng(32.155003, 118.848141)));
        posts.add(new Post(randomUser(), R.drawable.p7, "海边 等一个人和我牵手", new LatLng(31.985099, 118.672703)));
        posts.add(new Post(randomUser(), R.drawable.p2, "Quebec！", new LatLng(32.054379, 118.842647)));

        friendPosts.add(new Post(randomUser(), R.drawable.fp1, "南山南 北海北 南山有墓碑", new LatLng(32.174766, 119.433527)));
        friendPosts.add(new Post(randomUser(), R.drawable.fp2, "女人出门果然就是自拍 预祝白白参加中国新歌声成功", new LatLng(32.087097, 118.778995)));
        friendPosts.add(new Post(randomUser(), R.drawable.fp3, "斯里兰卡", new LatLng(31.912853, 118.771601)));
        friendPosts.add(new Post(randomUser(), R.drawable.fp4, "心里的郁结一下子被风吹走了",  new LatLng(32.005033, 118.787235)));
        friendPosts.add(new Post(randomUser(), R.drawable.fp5, "永远收不住的放荡不羁", new LatLng(32.042873, 118.763889)));
        friendPosts.add(new Post(randomUser(), R.drawable.fp6, "Hey!China!", new LatLng(32.060333, 118.599781)));

    }

    public User randomUser() {
        return randomElement(users);
    }

    public List<User> randomUsers(int n) {
        return randomElements(users, n);
    }

    public Post randomPost() {
        return randomElement(posts);
    }

    public List<Post> randomPosts(int n) {
        return randomElements(posts, n);
    }

    public List<Post> getAllPosts() {
        return posts;
    }

    public List<Post> getAllFriendPosts() {
        return friendPosts;
    }

    private static <T> T randomElement(List<T> elements) {
        int index = new Random().nextInt(elements.size());
        return elements.get(index);
    }

    private static <T> List<T> randomElements(List<T> elements, int n) {
        List<T> subset = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int index = new Random().nextInt(elements.size());
            subset.add(elements.get(index));
        }
        return subset;
    }
}

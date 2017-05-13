package me.hacknanjing.util;

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
    private static Factory instance;

    public static Factory getInstance() {
        if (instance == null) instance = new Factory();
        return instance;
    }

    public Factory() {
        users = new ArrayList<>(7);
        users.add(new User("合照", R.drawable.av1));
        users.add(new User("合照", R.drawable.av2));
        users.add(new User("合照", R.drawable.av3));
        users.add(new User("合照", R.drawable.av4));
        users.add(new User("合照", R.drawable.av5));
        users.add(new User("合照", R.drawable.av6));
        users.add(new User("合照", R.drawable.av7));
        posts = new ArrayList<>(8);
        posts.add(new Post(randomUser(), R.drawable.p1, "给咱一个地铁站 法师可以玩一天"));
        posts.add(new Post(randomUser(), R.drawable.p3, "糖果小屋"));
        posts.add(new Post(randomUser(), R.drawable.p4, "书如画"));
        posts.add(new Post(randomUser(), R.drawable.p5, "梦想在，哪里都是舞台"));
        posts.add(new Post(randomUser(), R.drawable.p6, "好耐冇逛深水埗"));
        posts.add(new Post(randomUser(), R.drawable.p7, "海边 等一个人和我牵手"));
        posts.add(new Post(randomUser(), R.drawable.p2, "Quebec！"));
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

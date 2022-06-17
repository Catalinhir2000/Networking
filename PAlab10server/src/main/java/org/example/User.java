package org.example;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<User> friends = new ArrayList<>();
    private String message;

    public User(String name) {
        this.name = name;
    }

    public void addFriend(User user) {
        friends.add(user);
        user.getFriends().add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public void setLastMessage(String message) {
        this.message = message;
    }

    public String getLastMessage() {
        return this.message;
    }
}

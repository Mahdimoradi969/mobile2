package com.example.dialogs;

public class User {

    private final int imageResourceId;
    private final String userName;
    private final int userAge;

    public User(int imageResourceId, String userName, int userAge) {
        this.imageResourceId = imageResourceId;
        this.userName = userName;
        this.userAge = userAge;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserAge() {
        return userAge;
    }
}

package com.example.demo.entity;

import java.time.Instant;

public final class UserBuilder {
    private String id;
    private String username;
    private String encryptedPassword;
    private Instant createdAt;
    private Instant updatedAt;
    private String avatar;

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
        return this;
    }

    public UserBuilder withCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserBuilder withUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public UserBuilder withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEncryptedPassword(encryptedPassword);
        user.setCreatedAt(createdAt);
        user.setUpdatedAt(updatedAt);
        user.setAvatar(avatar);
        return user;
    }
}

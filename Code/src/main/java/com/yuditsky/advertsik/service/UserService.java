package com.yuditsky.advertsik.service;

import com.yuditsky.advertsik.bean.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    boolean addUser(User user);

    boolean activateUser(String code);

    List<User> findAll();

    void saveUser(User user, String username, Map<String, String> form);

    boolean updateProfile(User user, String password, String email);
}

package com.fabware.testtask.service;

import com.fabware.testtask.entity.User;

public interface UserService {
    User registerUser(String email, String password);

    User findUser(String email, String password);
}


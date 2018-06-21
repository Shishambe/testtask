package com.fabware.testtask.dao;

import com.fabware.testtask.entity.User;

public interface UserDao {
    User findByEmail(String email);

    User save(User user);
}

package com.fabware.testtask.dao;

import com.fabware.testtask.entity.UserCred;

import java.util.List;

public interface UserCredDao {
    UserCred save(UserCred userCred);

    void delete(Long userId, String name);

    UserCred findByNameAndUserId(Long userId, String name);

    List<UserCred> findByUserId(Long userId);
}

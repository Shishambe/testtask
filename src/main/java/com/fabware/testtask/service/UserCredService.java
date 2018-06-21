package com.fabware.testtask.service;

import com.fabware.testtask.entity.User;
import com.fabware.testtask.entity.UserCred;

import java.util.List;
import java.util.Set;

public interface UserCredService {
    UserCred create(User user, String name, String login, String password, Set<String> tags);

    UserCred update(User user, String name, String login, String password, Set<String> tags);

    void delete(User user, String name);

    List<UserCred> getCredsForUser(User user);

}

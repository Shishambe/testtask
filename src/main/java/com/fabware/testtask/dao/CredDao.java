package com.fabware.testtask.dao;

import com.fabware.testtask.entity.UserCred;

import java.util.List;
import java.util.Set;

public interface CredDao {
    List<UserCred> findAll();

    List<UserCred> findByNameAndTags(String name, Set<String> incTags, Set<String> excTags);
}

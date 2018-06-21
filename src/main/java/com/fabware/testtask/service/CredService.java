package com.fabware.testtask.service;

import com.fabware.testtask.entity.UserCred;

import java.util.List;
import java.util.Set;

public interface CredService {
    List<UserCred> getList();

    List<UserCred> getListByNameAndTags(String name, Set<String> incTags, Set<String> excTags);
}

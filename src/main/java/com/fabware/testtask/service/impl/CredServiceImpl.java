package com.fabware.testtask.service.impl;

import com.fabware.testtask.dao.CredDao;
import com.fabware.testtask.entity.UserCred;
import com.fabware.testtask.service.CredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class CredServiceImpl implements CredService {
    private static final Logger logger = Logger.getLogger(CredServiceImpl.class.getName());

    @Autowired
    private CredDao credDao;

    @Override
    public List<UserCred> getList() {
        logger.info("get Creds list");
        return credDao.findAll();
    }

    @Override
    public List<UserCred> getListByNameAndTags(String name, Set<String> incTags, Set<String> excTags) {
        logger.info("search userCred with name(" + name +") incTags(" + incTags + ") excTags(" + excTags + ")");
        return credDao.findByNameAndTags(name, incTags, excTags);
    }
}

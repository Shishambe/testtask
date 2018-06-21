package com.fabware.testtask.service.impl;

import com.fabware.testtask.dao.UserCredDao;
import com.fabware.testtask.entity.User;
import com.fabware.testtask.entity.UserCred;
import com.fabware.testtask.service.UserCredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class UserCredServiceImpl implements UserCredService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    private UserCredDao userCredDao;

    @Override
    public UserCred create(User user, String name, String login, String password, Set<String> tags) {
        logger.info("create userCred(" + name + ") for user(" + user.getEmail() + ")");
        UserCred userCred = userCredDao.findByNameAndUserId(user.getId(), name);
        if (nonNull(userCred)) {
            logger.info("userCred(" + name + ") for user(" + user.getEmail() + ") already exist");
            return null;
        }
        userCred = createUserCred(user, name, login, password, tags, null);
        return userCredDao.save(userCred);
    }

    @Override
    public UserCred update(User user, String name, String login, String password, Set<String> tags) {
        logger.info("update userCred(" + name + ") for user(" + user.getEmail() + ")");
        UserCred userCred = userCredDao.findByNameAndUserId(user.getId(), name);
        if (isNull(userCred)) {
            logger.info("userCred(" + name + ") for user(" + user.getEmail() + ") doesn't exist");
            return null;
        }
        userCred = createUserCred(user, name, login, password, tags, userCred.getId());
        return userCredDao.save(userCred);
    }

    @Override
    public void delete(User user, String name) {
        logger.info("delete userCred(" + name + ") for user(" + user.getEmail() + ")");
        userCredDao.delete(user.getId(), name);
    }

    @Override
    public List<UserCred> getCredsForUser(User user) {
        logger.info("get userCreds for user(" + user.getEmail() + ")");
        return userCredDao.findByUserId(user.getId());
    }

    private UserCred createUserCred(User user, String name, String login, String password, Set<String> tags, Long id) {
        UserCred userCred = new UserCred();
        userCred.setName(name);
        userCred.setLogin(login);
        userCred.setPassword(password);
        userCred.setUser(user);
        userCred.setTags(tags);
        if (nonNull(id)) {
            userCred.setId(id);
        }
        return userCred;
    }
}

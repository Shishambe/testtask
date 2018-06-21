package com.fabware.testtask.service.impl;

import com.fabware.testtask.dao.UserDao;
import com.fabware.testtask.entity.User;
import com.fabware.testtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static java.util.Objects.nonNull;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    private UserDao userDao;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User registerUser(String email, String password) {
        logger.info("register user with email(" + email + ")");
        User user = userDao.findByEmail(email);
        if (nonNull(user)) {
            logger.info("user with email(" + email + ") already exist");
            return null;
        }
        user = createUser(email, password);
        return userDao.save(user);
    }

    @Override
    public User findUser(String email, String password) {
        logger.info("find user with email(" + email + ")");
        User foundUser = userDao.findByEmail(email);
        if (nonNull(foundUser)) {
            if (passwordEncoder.matches(password, foundUser.getPassword())) {
                return foundUser;
            }
        }
        logger.info("find user with email(" + email + "): user not found");
        return null;
    }

    private User createUser(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return user;
    }

}
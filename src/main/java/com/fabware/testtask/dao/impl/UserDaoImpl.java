package com.fabware.testtask.dao.impl;

import com.fabware.testtask.dao.UserDao;
import com.fabware.testtask.entity.User;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.*;

import java.util.logging.Logger;

@Repository
@Transactional(readOnly = true)
public class UserDaoImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class.getName());

    private static final String FIND_USER_BY_EMAIL_QUERY_STRING = "FROM User WHERE email = ?0";

    @PersistenceContext
    private EntityManager em;

    private JpaEntityInformation entityInformation;

    @PostConstruct
    public void init() {
        entityInformation = JpaEntityInformationSupport.getEntityInformation(User.class, em);
    }

    @Override
    @Transactional
    public User save(User user) {
        logger.info("save");
        if (entityInformation.isNew(user)) {
            em.persist(user);
            return user;
        } else {
            return em.merge(user);
        }
    }

    @Override
    public User findByEmail(String email) {
        logger.info("findByEmail");
        User result = null;
        try {
            TypedQuery<User> tq = em.createQuery(FIND_USER_BY_EMAIL_QUERY_STRING, User.class);
            result = tq.setParameter(0, email).getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
        }
        return result;
    }
}

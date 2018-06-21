package com.fabware.testtask.dao.impl;

import com.fabware.testtask.dao.UserCredDao;
import com.fabware.testtask.entity.UserCred;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.List;
import java.util.logging.Logger;

@Repository
@Transactional(readOnly = true)
public class UserCredDaoImpl implements UserCredDao {
    private static final Logger logger = Logger.getLogger(UserCredDaoImpl.class.getName());

    private static final String FIND_BY_NAME_AND_USER_ID_QUERY_STRING = "FROM UserCred WHERE name = ?0 AND user.id = ?1";
    private static final String FIND_BY_USER_ID_QUERY_STRING = "FROM UserCred WHERE user.id = ?0";
    private static final String DELETE_BY_NAME_AND_USER_ID_QUERY_STRING = "DELETE FROM UserCred WHERE name = ?0 AND user.id = ?1";

    @PersistenceContext
    private EntityManager em;

    private JpaEntityInformation entityInformation;

    @PostConstruct
    public void init() {
        entityInformation = JpaEntityInformationSupport.getEntityInformation(UserCred.class, em);
    }

    @Override
    @Transactional
    public UserCred save(UserCred userCred) {
        logger.info("save new userCred");
        if (entityInformation.isNew(userCred)) {
            em.persist(userCred);
            return userCred;
        } else {
            return em.merge(userCred);
        }
    }

    @Override
    @Transactional
    public void delete(Long userId, String name) {
        logger.info("delete userCred(" + name + ") for user(" + userId + ")");
        em.createQuery(DELETE_BY_NAME_AND_USER_ID_QUERY_STRING)
                .setParameter(0, name)
                .setParameter(1, userId)
                .executeUpdate();
    }

    @Override
    public UserCred findByNameAndUserId(Long userId, String name) {
        logger.info("findByName");
        UserCred result = null;
        try {
            TypedQuery<UserCred> tq = em.createQuery(FIND_BY_NAME_AND_USER_ID_QUERY_STRING, UserCred.class);
            result = tq.setParameter(0, name)
                    .setParameter(1, userId)
                    .getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
        }
        return result;
    }

    @Override
    public List<UserCred> findByUserId(Long userId) {
        logger.info("findByUserId(" + userId + ")");
        TypedQuery<UserCred> query = em.createQuery(FIND_BY_USER_ID_QUERY_STRING, UserCred.class)
                .setParameter(0, userId);
        return query.getResultList();
    }
}

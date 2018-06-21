package com.fabware.testtask.dao.impl;

import com.fabware.testtask.dao.CredDao;
import com.fabware.testtask.entity.UserCred;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

@Repository
@Transactional(readOnly = true)
public class CredDaoImpl implements CredDao {
    private static final Logger logger = Logger.getLogger(CredDaoImpl.class.getName());

    private static final String FIND_ALL_QUERY_STRING = "FROM UserCred";
    private static final String FIND_BY_NAME_TAGS_QUERY_STRING = "SELECT DISTINCT uc " +
                                                                 "FROM UserCred uc " +
                                                                 "LEFT JOIN uc.tags uct " +
                                                                 "WHERE ";

    private static final String APPENDED_AND = "AND ";

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<UserCred> findAll() {
        logger.info("findAll userCreds");
        TypedQuery<UserCred> tq = em.createQuery(FIND_ALL_QUERY_STRING, UserCred.class);
        return tq.getResultList();
    }

    @Override
    public List<UserCred> findByNameAndTags(String name, Set<String> incTags, Set<String> excTags) {
        logger.info("search userCred with name(" + name +") incTags(" + incTags + ") excTags(" + excTags + ")");
        //TODO: merge tags
        String queryString = buildSearchByNameAndTagsQuery(name, incTags, excTags);
        TypedQuery<UserCred> tq = em.createQuery(queryString, UserCred.class);
        Map<String, Object> params = getParamsAsMap(name, incTags, excTags);
        tq.getParameters().forEach(p -> tq.setParameter(p.getName(), params.get(p.getName())));
        return tq.getResultList();
    }

    private String buildSearchByNameAndTagsQuery(String name, Set<String> incTags, Set<String> excTags) {
        StringBuilder query = new StringBuilder(FIND_BY_NAME_TAGS_QUERY_STRING);
        if (!name.trim().isEmpty()) {
            query.append("name = :name ");
            query.append(APPENDED_AND);
        }
        if (!excTags.isEmpty()) {
            query.append("uc.id NOT IN (SELECT uc.id FROM UserCred uc join uc.tags uct WHERE uct IN :excTags) ");
            query.append(APPENDED_AND);
        }
        if (!incTags.isEmpty()) {
            query.append("uct IN :incTags GROUP BY uc.id HAVING COUNT(uc.id)=");
            query.append(incTags.size());
        }
        if (query.lastIndexOf(APPENDED_AND) == query.length() - APPENDED_AND.length()) {
            query.delete(query.length() - APPENDED_AND.length(), query.length());
        }
        return query.toString();
    }

    private Map<String, Object> getParamsAsMap(String name, Set<String> incTags, Set<String> excTags){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        paramMap.put("incTags", incTags);
        paramMap.put("excTags", excTags);
        return paramMap;
    }
}

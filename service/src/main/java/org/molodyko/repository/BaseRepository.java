package org.molodyko.repository;

import org.molodyko.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class BaseRepository<T extends BaseEntity<K>, K extends Serializable> {
    Class<T> clazz;
    @Autowired
    EntityManager entityManager;

    public BaseRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void save(T entity) {
        entityManager.persist(entity);
        entityManager.flush();
    }

    public void deleteById(K id) {
        T entity = entityManager.find(clazz, id);
        entityManager.remove(entity);
        entityManager.flush();
    }

    public T findById(K id) {
        return entityManager.find(clazz, id);
    }

    public void update(T entity) {
        entityManager.merge(entity);
        entityManager.flush();
    }

    public List<T> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(clazz);
        Root<T> from = query.from(clazz);
        query.select(from);
        List<T> list =  entityManager.createQuery(query).getResultList();
        return list;
    }
}

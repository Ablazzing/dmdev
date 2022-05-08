package org.molodyko.repository;

import org.hibernate.Session;
import org.molodyko.entity.BaseEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class BaseRepository<T extends BaseEntity<K>, K extends Serializable> {
    Class<T> clazz;

    public BaseRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void save(T entity, Session session) {
        session.save(entity);
        session.flush();
    }

    public void deleteById(K id, Session session) {
        T entity = session.get(clazz, id);
        session.delete(entity);
        session.flush();
    }

    public T findById(K id, Session session) {
        T entity = session.get(clazz, id);
        return entity;
    }

    public void update(T entity, Session session) {
        session.merge(entity);
        session.flush();
    }

    public List<T> findAll(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(clazz);
        Root<T> from = query.from(clazz);
        query.select(from);
        List<T> list = session.createQuery(query).list();
        return list;
    }
}

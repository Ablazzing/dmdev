package org.molodyko.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.molodyko.entity.BaseEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class BaseRepository<T extends BaseEntity<K>, K extends Serializable> {
    SessionFactory sessionFactory;
    Class<T> clazz;

    public BaseRepository(SessionFactory sessionFactory, Class<T> clazz) {
        this.sessionFactory = sessionFactory;
        this.clazz = clazz;
    }

    public void save(T entity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }

    public void deleteById(K id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        T entity = session.get(clazz, id);
        session.delete(entity);
        session.getTransaction().commit();
    }

    public T findById(K id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        T entity = session.get(clazz, id);
        session.getTransaction().commit();
        return entity;
    }

    public void update(T entity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(entity);
        session.getTransaction().commit();
    }

    public List<T> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(clazz);
        Root<T> from = query.from(clazz);
        query.select(from);
        List<T> list = session.createQuery(query).list();
        session.getTransaction().commit();
        return list;
    }
}

package org.molodyko.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.molodyko.entity.User;
import org.molodyko.entity.User_;
import org.molodyko.entity.filter.UserFilter;
import org.molodyko.entity.utils.CriteriaPredicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserRepository extends BaseRepository<User, Integer> {

    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory, User.class);
    }

    public List<User> getUsersByFilter(UserFilter userFilter) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        UserFilter filter = UserFilter.builder()
                .username(userFilter.getUsername())
                .role(userFilter.getRole())
                .build();

        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> user = criteria.from(User.class);

        Predicate[] predicate = CriteriaPredicate.builder(cb)
                .add(filter.getUsername(), username -> cb.equal(user.get(User_.username), username))
                .getPredicates();

        criteria.select(user).where(predicate);
        List<User> users = session.createQuery(criteria).list();
        session.getTransaction().commit();
        return users;
    }
}

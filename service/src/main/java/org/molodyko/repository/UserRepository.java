package org.molodyko.repository;

import org.molodyko.entity.User;
import org.molodyko.entity.User_;
import org.molodyko.entity.filter.UserFilter;
import org.molodyko.entity.utils.CriteriaPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    default List<User> getUsersByFilter(UserFilter userFilter, EntityManager entityManager) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> user = criteria.from(User.class);

        Predicate[] predicate = CriteriaPredicate.builder(cb)
                .add(userFilter.getUsername(), username -> cb.equal(user.get(User_.username), username))
                .getPredicates();

        criteria.select(user).where(predicate);
        List<User> users = entityManager.createQuery(criteria).getResultList();
        return users;
    }
}

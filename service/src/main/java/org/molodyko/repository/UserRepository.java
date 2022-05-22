package org.molodyko.repository;

import org.molodyko.entity.User;
import org.molodyko.entity.User_;
import org.molodyko.entity.filter.UserFilter;
import org.molodyko.entity.utils.CriteriaPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserRepository extends BaseRepository<User, Integer> {

    @Autowired
    public UserRepository() {
        super(User.class);
    }

    public List<User> getUsersByFilter(UserFilter userFilter) {
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

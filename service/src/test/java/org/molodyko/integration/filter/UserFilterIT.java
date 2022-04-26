package org.molodyko.integration.filter;

import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.molodyko.entity.User;
import org.molodyko.entity.UserRole;
import org.molodyko.entity.User_;
import org.molodyko.entity.utils.CriteriaPredicate;
import org.molodyko.entity.filter.UserFilter;
import org.molodyko.integration.IntegrationBase;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserFilterIT extends IntegrationBase {

    @Test
    public void checkUserFilter() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            UserFilter filter = UserFilter.builder().username(null).role(UserRole.ADMIN).build();

            CriteriaQuery<User> criteria = cb.createQuery(User.class);
            Root<User> user = criteria.from(User.class);

            Predicate[] predicate = CriteriaPredicate.builder(cb)
                            .add(filter.getUsername(), username -> cb.equal(user.get(User_.username), username))
                            .getPredicates();

            criteria.select(user).where(predicate);
            List<User> list = session.createQuery(criteria).list();
            Assertions.assertThat(list).hasSize(2);
        }
    }
}
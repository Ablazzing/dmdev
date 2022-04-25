package org.molodyko.entity.filter;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CriteriaPredicate {
    List<Predicate> predicates = new ArrayList<>();
    CriteriaBuilder cb;

    private CriteriaPredicate(CriteriaBuilder cb) {
        this.cb = cb;
    }

    public static CriteriaPredicate builder(CriteriaBuilder cb) {
        return new CriteriaPredicate(cb);
    }

    public <X> CriteriaPredicate addEquals(Expression<X> exp, Object obj) {
        if (obj != null) {
            predicates.add(cb.equal(exp, obj));
        }
        return this;
    }

    public Predicate andAll() {
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

package org.molodyko.entity.utils;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CriteriaPredicate {
    List<Predicate> predicates = new ArrayList<>();

    private CriteriaPredicate() {}

    public static CriteriaPredicate builder(CriteriaBuilder cb) {
        return new CriteriaPredicate();
    }

    public <T> CriteriaPredicate add(T obj, Function<T, Predicate> function) {
        if (obj != null) {
            predicates.add(function.apply(obj));
        }
        return this;
    }

    public Predicate[] getPredicates() {
        return  predicates.toArray(Predicate[]::new);
    }
}

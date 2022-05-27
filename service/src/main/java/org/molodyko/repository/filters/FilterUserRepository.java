package org.molodyko.repository.filters;

import org.molodyko.entity.User;
import org.molodyko.entity.filter.UserFilter;

import java.util.List;

public interface FilterUserRepository {

    List<User> getUsersByFilter(UserFilter userFilter);
}

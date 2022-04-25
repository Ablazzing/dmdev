package org.molodyko.entity.filter;

import lombok.Builder;
import lombok.Data;
import org.molodyko.entity.UserRole;

@Data
@Builder
public class UserFilter {
    String username;
    UserRole role;
}

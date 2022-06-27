package org.molodyko.dto;

import lombok.Builder;
import lombok.Data;
import org.molodyko.entity.UserRole;

@Data
@Builder
public class UserDto {
    Integer id;
    String username;
    String password;
    String email;
    UserRole role;
}

package org.molodyko.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.molodyko.entity.UserRole;

@Jacksonized
@Data
@Builder
public class UserDto {
    Integer id;
    String username;
    String password;
    String email;
    UserRole role;
}

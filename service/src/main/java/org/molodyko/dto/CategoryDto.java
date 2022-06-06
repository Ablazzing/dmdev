package org.molodyko.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {
    Integer id;
    String name;
    String username;
}

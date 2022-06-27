package org.molodyko.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EntryDto {
    Integer id;
    private String amount;
    private String description;
    private String dateEntry;
    private String time;
    private String username;
    private String categoryName;
    private Integer operationNumber;
}

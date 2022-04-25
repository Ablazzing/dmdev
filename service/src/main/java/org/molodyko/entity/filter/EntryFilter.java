package org.molodyko.entity.filter;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class EntryFilter {
    LocalDateTime dateStart;
    LocalDateTime dateEnd;
}

package org.molodyko.repository;

import org.molodyko.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<Holiday, Integer> {

}

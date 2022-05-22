package org.molodyko.repository;

import org.molodyko.entity.DescriptionChanger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptionChangerRepository extends JpaRepository<DescriptionChanger, Integer> {

}

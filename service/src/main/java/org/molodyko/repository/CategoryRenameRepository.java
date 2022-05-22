package org.molodyko.repository;

import org.molodyko.entity.CategoryRename;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRenameRepository extends JpaRepository<CategoryRename, Integer> {

}

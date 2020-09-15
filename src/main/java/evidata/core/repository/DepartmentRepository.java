package evidata.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import evidata.core.model.Department;

import java.util.Optional;

/**
 * Created by Nicolle on iun. in 2018
 */
@Repository
public interface DepartmentRepository extends IRepository<Department, Long> {
    Optional<Department> findById(Long id);

    Page<Department> findByNameStartingWithIgnoreCase(String name, Pageable pageable);

    Optional<Department> findByNameIgnoringCase(String departmentName);
}

package evidata.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import evidata.core.model.Department;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on iun. in 2018
 */
public interface DepartmentService {
    /**
     * Get a list will all available departments
     *
     * @return the list of departments
     */
    List<Department> getAllDepartments();

    /**
     * Create or update a department
     *
     * @param department saved entity
     * @return the new created department or updated department
     */
    Department saveDepartment(Department department);

    /**
     * Delete the department by given ID
     *
     * @param id identifier for deleted department
     */
    void deleteDepartment(Long id);

    /**
     * Get the department with given id
     *
     * @param id identifier of the department
     * @return the optional department
     */
    Optional<Department> findDepartment(Long id);

    /**
     * get department with given type ignoring case
     * @param departmentName identifier of the department
     * @return the optional department
     */
    Optional<Department> findDepartmentByNameIgnoringCase(String departmentName);

    Page<Department> findPaginated(String name, Pageable pageable);
}

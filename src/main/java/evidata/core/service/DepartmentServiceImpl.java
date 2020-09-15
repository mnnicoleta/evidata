package evidata.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import evidata.core.model.Department;
import evidata.core.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on iul. in 2018
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);


    private DepartmentRepository departmentRepository;

    @Autowired
    public void setDepartmentRepository(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentRepository getDepartmentRepository() {
        return departmentRepository;
    }

    @Override
    public List<Department> getAllDepartments() {
        log.trace("getAllDepartments --- method entered");

        List<Department> departments = getDepartmentRepository().findAll(sortByNameAsc());

        log.trace("getAllDepartments: departments={}", departments);

        return departments;
    }

    private Sort sortByNameAsc() {
        return new Sort(Sort.Direction.ASC, "name");
    }

    @Override
    @Transactional
    public Department saveDepartment(Department department) {
        if (department.getId() == null) {
            log.trace("create new department type={} ", department.getName());
        } else {
            log.trace("updateDepartment: id={}, type={} ", department.getId(), department.getName());
        }
        Department savedDepartment = getDepartmentRepository().save(department);
        log.trace("saveDepartment: result={}", savedDepartment);
        return savedDepartment;
    }

    @Override
    public void deleteDepartment(Long id) {
        log.trace("deleteDepartment: id={}", id);
        getDepartmentRepository().delete(id);
        log.trace("deleteDepartment --- method finished");
    }

    @Override
    public Optional<Department> findDepartment(Long id) {
        log.trace("findDepartment: id={}", id);

        Optional<Department> departmentOptional = getDepartmentRepository().findById(id);

        log.trace("findDepartment: departmentOptional={}", departmentOptional);

        return departmentOptional;
    }

    @Override
    public Optional<Department> findDepartmentByNameIgnoringCase(String departmentName) {
        log.trace("findDepartment: departmentName={}", departmentName);

        Optional<Department> departmentOptional = getDepartmentRepository().findByNameIgnoringCase(departmentName);

        log.trace("findDepartment: departmentOptional={}", departmentOptional);

        return departmentOptional;
    }

    public Page<Department> findPaginated(String name, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();

        Page<Department> page;
        if (StringUtils.isEmpty(name)) {
            page = getDepartmentRepository().findAll(new PageRequest(currentPage, pageSize, sortByNameAsc()));
        } else {
            page = getDepartmentRepository().findByNameStartingWithIgnoreCase(name, new PageRequest(currentPage, pageSize, sortByNameAsc()));
        }
        return page;
    }

}

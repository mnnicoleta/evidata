package evidata.core.service;

import evidata.core.model.Department;
import evidata.core.repository.DepartmentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on 10.09.2018
 */

public class DepartmentServiceTest {

    private DepartmentRepository departmentRepositoryMock;

    private DepartmentServiceImpl departmentService;

    @Before
    public void setUp() throws Exception {
        departmentService = new DepartmentServiceImpl();

        departmentRepositoryMock = Mockito.mock(DepartmentRepository.class);
        departmentService.setDepartmentRepository(departmentRepositoryMock);
    }

    @Test
    public void testGetAllDepartmentsEmpty() {
        List<Department> departmentList = departmentService.getAllDepartments();
        Assert.assertTrue(CollectionUtils.isEmpty(departmentList));
    }

    @Test
    public void testGetAllDepartments() {
        Department departmentMock = Mockito.mock(Department.class);

        List<Department> mockList = Arrays.asList(departmentMock);
        Mockito.doReturn(mockList).when(departmentRepositoryMock).findAll(Mockito.any(Sort.class));

        List<Department> departmentList = departmentService.getAllDepartments();
        Assert.assertTrue(departmentList.size() == 1);

        Assert.assertSame(departmentMock, departmentList.get(0));

    }

    @Test
    public void testFindDepartmentNull() {
        Optional<Department> departmentOptional = departmentService.findDepartment(null);
        Assert.assertNull(departmentOptional);
    }

    @Test
    public void testFindDepartmentEmpty() {
        Long id = 10L;
        Optional<Department> optionalDepartment = Optional.empty();
        Mockito.doReturn(optionalDepartment).when(departmentRepositoryMock).findById(Mockito.anyLong());
        Optional<Department> departmentOptional = departmentService.findDepartment(id);
        Assert.assertFalse(departmentOptional.isPresent());
    }

    @Test
    public void testFindDepartment() {
        Long id = 1L;
        String name = "my type";

        Department department = new Department();
        department.setId(id);
        department.setName(name);

        Optional<Department> optionalDepartment = Optional.of(department);
        Mockito.doReturn(optionalDepartment).when(departmentRepositoryMock).findById(id);

        Optional<Department> departmentOptional = departmentService.findDepartment(id);
        Assert.assertTrue(departmentOptional.isPresent());

        Department departmentResult = departmentOptional.get();
        Assert.assertNotNull(departmentResult);
        Assert.assertEquals(name, departmentResult.getName());
    }

    @Test
    public void testSaveDepartment() {
        String name = "my type";
        Department department= new Department();
        department.setName(name);

        Department savedDepartmentMock= new Department();
        Mockito.doReturn(savedDepartmentMock).when(departmentRepositoryMock).save(department);

        Department savedDepartment = departmentService.saveDepartment(department);
        Assert.assertSame(savedDepartmentMock,savedDepartment);
    }

}

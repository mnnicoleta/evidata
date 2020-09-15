package evidata.core.service;

import evidata.core.model.Equipment;
import evidata.core.repository.EquipmentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EquipmentServiceTest {

    private EquipmentRepository equipmentRepositoryMock;

    private EquipmentServiceImpl equipmentService;

    @Before
    public void setUp() throws Exception {
        equipmentService = new EquipmentServiceImpl();

        equipmentRepositoryMock = Mockito.mock(EquipmentRepository.class);
        equipmentService.setEquipmentRepository(equipmentRepositoryMock);
    }

    @Test
    public void testGetAllEquipments() {
        Equipment equipmentMock = Mockito.mock(Equipment.class);

        List<Equipment> mockList = Arrays.asList(equipmentMock);
        Mockito.doReturn(mockList).when(equipmentRepositoryMock).findAll(Mockito.any(Sort.class));

        List<Equipment> equipmentList = equipmentService.getAllEquipments();
        Assert.assertTrue(equipmentList.size() == 1);

        Assert.assertSame(equipmentMock, equipmentList.get(0));

    }

    @Test
    public void testFindEquipmentNull() {
        Optional<Equipment> equipmentOptional = equipmentService.findEquipment(null);
        Assert.assertNull(equipmentOptional);
    }

    @Test
    public void testFindEquipment() {
        Long id = 1L;
        String name = "my type";

        Equipment equipment = new Equipment();
        equipment.setId(id);
        equipment.setName(name);

        Optional<Equipment> optionalEquipment = Optional.of(equipment);
        Mockito.doReturn(optionalEquipment).when(equipmentRepositoryMock).findById(id);

        Optional<Equipment> equipmentOptional = equipmentService.findEquipment(id);
        Assert.assertTrue(equipmentOptional.isPresent());

        Equipment equipmentResult = equipmentOptional.get();
        Assert.assertNotNull(equipmentResult);
        Assert.assertEquals(name, equipmentResult.getName());
    }

    @Test
    public void testSaveEquipment() {
        String name = "Microscope";
        Equipment equipment= new Equipment();
        equipment.setName(name);

        Equipment savedEquipmentMock= new Equipment();
        Mockito.doReturn(savedEquipmentMock).when(equipmentRepositoryMock).save(equipment);

        Equipment savedEquipment = equipmentService.saveEquipment(equipment);
        Assert.assertSame(savedEquipmentMock,savedEquipment);
    }
}

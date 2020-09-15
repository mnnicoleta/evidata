package evidata.core.service;

import evidata.core.model.Expertise;
import evidata.core.repository.ExpertiseRepository;
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
public class ExpertiseServiceTest {
    private ExpertiseRepository expertiseRepositoryMock;

    private ExpertiseServiceImpl expertiseService;

    @Before
    public void setUp() throws Exception {
        expertiseService = new ExpertiseServiceImpl();

        expertiseRepositoryMock = Mockito.mock(ExpertiseRepository.class);
        expertiseService.setExpertiseRepository(expertiseRepositoryMock);
    }

    @Test
    public void testGetAllExpertisesEmpty() {
        List<Expertise> expertiseList = expertiseService.getAllExpertises();
        Assert.assertTrue(CollectionUtils.isEmpty(expertiseList));
    }

    @Test
    public void testGetAllExpertises() {
        Expertise expertiseMock = Mockito.mock(Expertise.class);

        List<Expertise> mockList = Arrays.asList(expertiseMock);
        Mockito.doReturn(mockList).when(expertiseRepositoryMock).findAll(Mockito.any(Sort.class));

        List<Expertise> expertiseList = expertiseService.getAllExpertises();
        Assert.assertTrue(expertiseList.size() == 1);

        Assert.assertSame(expertiseMock, expertiseList.get(0));

    }

    @Test
    public void testFindExpertiseNull() {
        Optional<Expertise> expertiseOptional = expertiseService.findExpertise(null);
        Assert.assertNull(expertiseOptional);
    }

    @Test
    public void testFindExpertiseEmpty() {
        Long id = 10L;
        Optional<Expertise> optionalExpertise = Optional.empty();
        Mockito.doReturn(optionalExpertise).when(expertiseRepositoryMock).findById(Mockito.anyLong());
        Optional<Expertise> expertiseOptional = expertiseService.findExpertise(id);
        Assert.assertFalse(expertiseOptional.isPresent());
    }

    @Test
    public void testFindExpertise() {
        Long id = 10L;
        String expertiseNumber = "my type";

        Expertise expertise = new Expertise();
        expertise.setId(id);
        expertise.setExpertiseNumber(expertiseNumber);

        Optional<Expertise> optionalExpertise = Optional.of(expertise);
        Mockito.doReturn(optionalExpertise).when(expertiseRepositoryMock).findById(id);

        Optional<Expertise> expertiseOptional = expertiseService.findExpertise(id);
        Assert.assertTrue(expertiseOptional.isPresent());

        Expertise expertiseResult = expertiseOptional.get();
        Assert.assertNotNull(expertiseResult);
        Assert.assertEquals(expertiseNumber, expertiseResult.getExpertiseNumber());
    }
}

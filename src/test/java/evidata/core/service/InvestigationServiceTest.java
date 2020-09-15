package evidata.core.service;

import evidata.core.model.Investigation;
import evidata.core.repository.InvestigationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Nicolle on 10.09.2018
 */
public class InvestigationServiceTest {
    private InvestigationRepository investigationRepositoryMock;

    private InvestigationServiceImpl investigationService;

    @Before
    public void setUp() throws Exception {
        investigationService = new InvestigationServiceImpl();

        investigationRepositoryMock = Mockito.mock(InvestigationRepository.class);
        investigationService.setInvestigationRepository(investigationRepositoryMock);
    }

    @Test
    public void testGetInvestigationsByExpertiseIdEmpty() {
        List<Investigation> investigationList = investigationService.getInvestigationsByExpertiseId(1L);
        Assert.assertTrue(CollectionUtils.isEmpty(investigationList));
    }

    @Test
    public void testGetInvestigationsByExpertiseId() {
        Investigation investigationMock = Mockito.mock(Investigation.class);

        List<Investigation> mockList = Arrays.asList(investigationMock);
        Mockito.doReturn(mockList).when(investigationRepositoryMock).findByExpertise_Id(1L);

        List<Investigation> investigationList = investigationService.getInvestigationsByExpertiseId(1L);
        Assert.assertTrue(investigationList.size() == 1);

        Assert.assertSame(investigationMock, investigationList.get(0));

    }

    @Test
    public void testFindInvestigationNull() {
        Optional<Investigation> investigationOptional = investigationService.findInvestigation(null);
        Assert.assertNull(investigationOptional);
    }

    @Test
    public void testFindInvestigationEmpty() {
        Long id = 1L;
        Optional<Investigation> optionalInvestigation = Optional.empty();
        Mockito.doReturn(optionalInvestigation).when(investigationRepositoryMock).findById(Mockito.anyLong());
        Optional<Investigation> investigationOptional = investigationService.findInvestigation(id);
        Assert.assertFalse(investigationOptional.isPresent());
    }

    @Test
    public void testFindInvestigation() {
        Long id = 1L;
        String result = "result";

        Investigation investigation = new Investigation();
        investigation.setId(id);
        investigation.setResult(result);

        Optional<Investigation> optionalInvestigation = Optional.of(investigation);
        Mockito.doReturn(optionalInvestigation).when(investigationRepositoryMock).findById(id);

        Optional<Investigation> investigationOptional = investigationService.findInvestigation(id);
        Assert.assertTrue(investigationOptional.isPresent());

        Investigation investigationResult = investigationOptional.get();
        Assert.assertNotNull(investigationResult);
        Assert.assertEquals(result, investigationResult.getResult());
    }
}

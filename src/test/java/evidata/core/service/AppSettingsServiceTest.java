package evidata.core.service;

import evidata.core.model.AppSettings;
import evidata.core.repository.AppSettingsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Nicolle on 10.09.2018
 */
public class AppSettingsServiceTest {
    private AppSettingsRepository appSettingsRepositoryMock;

    private AppSettingsServiceImpl appSettingsService;

    @Before
    public void setUp() throws Exception {
        appSettingsService = new AppSettingsServiceImpl();

        appSettingsRepositoryMock = Mockito.mock(AppSettingsRepository.class);
        appSettingsService.setAppSettingsRepository(appSettingsRepositoryMock);
    }

    @Test
    public void testSaveAppSettings() {
        AppSettings appSettings= new AppSettings();
        appSettingsService.saveAppSettings(appSettings);
        //verify repository is invoked
        verify(appSettingsRepositoryMock, times(1)).save(appSettings);
    }

    @Test
    public void testGetAppSettings() {
        AppSettings appSettingsMock = Mockito.mock(AppSettings.class);

        List<AppSettings> mockList = Arrays.asList(appSettingsMock);
        Mockito.doReturn(mockList).when(appSettingsRepositoryMock).findAll();

        AppSettings appSettings = appSettingsService.getAppSettings();
        Assert.assertSame(appSettingsMock, appSettings);
    }

    @Test
    public void testGetAppSettingsDefault() {
        List<AppSettings> mockList = new ArrayList<>();
        Mockito.doReturn(mockList).when(appSettingsRepositoryMock).findAll();

        AppSettings appSettings = appSettingsService.getAppSettings();
        Assert.assertEquals(appSettings.getAttachmentDir(), AppSettingsServiceImpl.DEFAULT_ATTACHMENT_DIR);

    }

}

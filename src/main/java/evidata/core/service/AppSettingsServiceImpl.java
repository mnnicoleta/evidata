package evidata.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import evidata.core.model.AppSettings;
import evidata.core.repository.AppSettingsRepository;

import java.util.List;

@Service
public class AppSettingsServiceImpl implements AppSettingsService {

    protected static final String DEFAULT_ATTACHMENT_DIR = "tmp";
    private AppSettingsRepository appSettingsRepository;

    public AppSettingsRepository getAppSettingsRepository() {
        return appSettingsRepository;
    }
    @Autowired
    public void setAppSettingsRepository(AppSettingsRepository appSettingsRepository) {
        this.appSettingsRepository = appSettingsRepository;
    }

    @Override
    public AppSettings getAppSettings() {
        AppSettings appSettings;
        List<AppSettings> appSettingsList = appSettingsRepository.findAll();
        if(CollectionUtils.isEmpty(appSettingsList)){
            appSettings= new AppSettings();
            appSettings.setAttachmentDir(DEFAULT_ATTACHMENT_DIR);
            appSettingsRepository.save(appSettings);
        }else{
            appSettings=appSettingsList.get(0);
        }
        return appSettings;
    }

    @Override
    public AppSettings saveAppSettings(AppSettings appSettings) {
        return appSettingsRepository.save(appSettings);
    }
}

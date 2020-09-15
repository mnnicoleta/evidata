package evidata.core.service;

import evidata.core.model.AppSettings;

public interface AppSettingsService {
    AppSettings getAppSettings();
    AppSettings saveAppSettings(AppSettings appSettings);
}

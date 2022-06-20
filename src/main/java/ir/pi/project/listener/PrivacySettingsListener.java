package ir.pi.project.listener;

import ir.pi.project.event.PrivacySettingEvent;

public interface PrivacySettingsListener {
    void eventOccurred(PrivacySettingEvent privacySettingEvent);
}

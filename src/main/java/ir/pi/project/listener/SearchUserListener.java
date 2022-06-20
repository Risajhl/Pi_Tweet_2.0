package ir.pi.project.listener;

import ir.pi.project.event.PrivacySettingEvent;
import ir.pi.project.event.SearchUserEvent;

public interface SearchUserListener {
    void eventOccurred(SearchUserEvent searchUserEvent);
}

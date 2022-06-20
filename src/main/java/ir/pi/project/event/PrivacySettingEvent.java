package ir.pi.project.event;

import java.util.EventObject;

public class PrivacySettingEvent extends EventObject {

    private boolean isActive;
    private boolean isPublic;
    private String lastSeenState;
    private String newPassword;

    public PrivacySettingEvent(Object source, boolean isActive, boolean isPublic, String lastSeenState,String newPassword) {
        super(source);
        this.isActive=isActive;
        this.isPublic=isPublic;
        this.lastSeenState=lastSeenState;
        this.newPassword=newPassword;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getLastSeenState() {
        return lastSeenState;
    }

    public String getNewPassword() {
        return newPassword;
    }
}

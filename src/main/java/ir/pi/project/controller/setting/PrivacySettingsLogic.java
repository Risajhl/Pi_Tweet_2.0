package ir.pi.project.controller.setting;

import ir.pi.project.config.texts.setting.PrivacySettingsTexts;
import ir.pi.project.controller.MainLogic;
import ir.pi.project.controller.UserLogic;
import ir.pi.project.event.PrivacySettingEvent;
import ir.pi.project.listener.PrivacySettingsListener;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.setting.PrivacySettingsPageController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class PrivacySettingsLogic extends MainLogic {
    static private final Logger logger= LogManager.getLogger(PrivacySettingsLogic.class);
    private final PrivacySettingsTexts privacySettingsTexts=new PrivacySettingsTexts();

    PrivacySettingsPageController privacySettingsPageController;
    public PrivacySettingsLogic(PrivacySettingsPageController privacySettingsPageController) throws IOException {
        this.privacySettingsPageController=privacySettingsPageController;
        privacySettingsPageController.setPrivacySettingsListener(new PrivacySettingsListener() {
            @Override
            public void eventOccurred(PrivacySettingEvent privacySettingEvent) {
               User user= context.Users.get(MainController.currentUser.getId());
                user.setActive(privacySettingEvent.isActive());
                user.setPublic(privacySettingEvent.isPublic());
                user.setLastSeenState(privacySettingEvent.getLastSeenState());
                if(!privacySettingEvent.getNewPassword().equals(""))
                    MainController.currentUser.setPassword(privacySettingEvent.getNewPassword());
                context.Users.update(user);
                logger.info("user "+user.getUserName()+"'s privacy settings updated");
            }
        });
        privacySettingsPageController.setPrivacyStringListener(new StringListener() {
            @Override
            public void stringEventOccurred(String string) {
                privacySettingsPageController.setErrorLabel("",false);

                if(string.equals("ChangePassword")){
                privacySettingsPageController.getCurrentPasswordField().setVisible(true);
                privacySettingsPageController.getCheckButton().setVisible(true);

                }
                if(string.equals("Check")){
                    String currentPassword=privacySettingsPageController.getCurrentPasswordField().getText();
                    if(currentPassword.equals(MainController.currentUser.getPassword())){
                        privacySettingsPageController.getNewPasswordField().setVisible(true);
//                        privacySettingsPageController.setErrorLabel(privacySettingsTexts.getWrongPassword(),true);
                    }
                    else privacySettingsPageController.setErrorLabel(privacySettingsTexts.getWrongPassword(),true);
                }
            }
        });
    }
}

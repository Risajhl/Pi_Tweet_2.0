package ir.pi.project.controller.setting;

import ir.pi.project.controller.MainLogic;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.setting.SettingsController;

import java.time.LocalDateTime;

public class SettingsLogic extends MainLogic {
    SettingsController settingsController;
    public SettingsLogic(SettingsController settingsController){
        this.settingsController=settingsController;
        settingsController.setSettingsPageListener(new StringListener() {
            @Override
            public void stringEventOccurred(String string) {
                try {


                    if (string.equals("Privacy")) {
                        MainController.loadPrivacySettingsPage();
                    }
                    if (string.equals("LogOut")) {
                        MainController.loadWelcome();
                        logOut();
                    }
                    if (string.equals("DeleteAccount")) {
                        MainController.loadDeleteAccountPage();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void logOut(){
        User user=context.Users.get(MainController.currentUser.getId());
        String date= LocalDateTime.now().getYear()+" "+LocalDateTime.now().getMonth()+" "+LocalDateTime.now().getDayOfMonth()+
                " - "+LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute();
        user.setLastSeen("LastSeen: "+date);
        user.setOnline(false);
        context.Users.update(user);

    }




}

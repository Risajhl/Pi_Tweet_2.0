package ir.pi.project.view.setting;

import ir.pi.project.listener.StringListener;
import ir.pi.project.view.MainController;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    private StringListener settingsPageListener;

    public void setSettingsPageListener(StringListener settingsPageListener) {
        this.settingsPageListener = settingsPageListener;
    }

    public void privacy(){
        settingsPageListener.stringEventOccurred("Privacy");
    }
    public void logOut(){
        settingsPageListener.stringEventOccurred("LogOut");
    }
    public void deleteAccount(){
        settingsPageListener.stringEventOccurred("DeleteAccount");
    }

    public void back(){
        MainController.back();
    }
    public void mainMenu(){
        try {
            MainController.loadMainMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

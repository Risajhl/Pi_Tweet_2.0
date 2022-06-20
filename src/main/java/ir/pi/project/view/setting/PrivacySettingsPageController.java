package ir.pi.project.view.setting;

import com.sun.tools.javac.Main;
import ir.pi.project.config.texts.setting.PrivacySettingsTexts;
import ir.pi.project.event.PrivacySettingEvent;
import ir.pi.project.listener.PrivacySettingsListener;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrivacySettingsPageController implements Initializable {

    PrivacySettingsListener privacySettingsListener;
    StringListener privacyStringListener;
    private PrivacySettingsTexts privacySettingsTexts;

    {
        try {
            privacySettingsTexts = new PrivacySettingsTexts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    RadioButton activeRadioButton, inactiveRadioButton,privateRadioButton,
            publicRadioButton, everyOneRadioButton,noOneRadioButton, followersRadioButton;

    @FXML
    TextField currentPasswordField,newPasswordField;

    @FXML
    Label errorLabel, infoLabel;

    @FXML
    Button checkButton;


    public void setPrivacySettingsListener(PrivacySettingsListener privacySettingsListener) {
        this.privacySettingsListener = privacySettingsListener;
    }

    public void setPrivacyStringListener(StringListener privacyStringListener) {
        this.privacyStringListener = privacyStringListener;
    }

    public TextField getCurrentPasswordField() {
        return currentPasswordField;
    }

    public TextField getNewPasswordField() {
        return newPasswordField;
    }

    public Button getCheckButton() {
        return checkButton;
    }



    public void deselectPrivacy(ActionEvent e){
        if(e.getSource().equals(privateRadioButton))
        publicRadioButton.setSelected(false);
        else privateRadioButton.setSelected(false);
    }

    public void deselectActivity(ActionEvent e){
        if(e.getSource().equals(activeRadioButton))
            inactiveRadioButton.setSelected(false);
        else activeRadioButton.setSelected(false);
    }

    public void deselectLastSeen(ActionEvent e){
        if(e.getSource().equals(noOneRadioButton)){
            everyOneRadioButton.setSelected(false);
            followersRadioButton.setSelected(false);
        }
        else if(e.getSource().equals(everyOneRadioButton)){
            noOneRadioButton.setSelected(false);
            followersRadioButton.setSelected(false);
        }
        else {
            noOneRadioButton.setSelected(false);
            everyOneRadioButton.setSelected(false);
        }
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

    public void save(){
        boolean isActive=activeRadioButton.isSelected();
        boolean isPublic=publicRadioButton.isSelected();
        String lastSeen;
        if(everyOneRadioButton.isSelected())lastSeen="EveryOne";
        else if(noOneRadioButton.isSelected())lastSeen="NoOne";
        else lastSeen="Followers";

        String newPassword=newPasswordField.getText();

        PrivacySettingEvent privacySettingEvent=new PrivacySettingEvent(this,isActive,isPublic,lastSeen,newPassword);
        privacySettingsListener.eventOccurred(privacySettingEvent);
        setInfoLabel(privacySettingsTexts.getSaved(),true);
    }

    public void changePassword(){
        privacyStringListener.stringEventOccurred("ChangePassword");


    }

    public void check(){
        privacyStringListener.stringEventOccurred("Check");
    }

    public void setErrorLabel(String text,boolean isVisible){
        errorLabel.setVisible(isVisible);
        errorLabel.setText(text);

    }
    public void setInfoLabel(String text,boolean isVisible){
        infoLabel.setVisible(isVisible);
        infoLabel.setText(text);
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorLabel.setVisible(false);
        infoLabel.setVisible(false);
        currentPasswordField.setVisible(false);
        newPasswordField.setVisible(false);
        checkButton.setVisible(false);


        User user=MainController.currentUser;
        if(user.isActive())activeRadioButton.setSelected(true);
        else inactiveRadioButton.setSelected(true);

        if(user.isPublic())publicRadioButton.setSelected(true);
        else privateRadioButton.setSelected(true);

        if(user.getLastSeenState().equals("NoOne"))
            noOneRadioButton.setSelected(true);
        else if(user.getLastSeenState().equals("EveryOne")) everyOneRadioButton.setSelected(true);
        else followersRadioButton.setSelected(true);

    }
}

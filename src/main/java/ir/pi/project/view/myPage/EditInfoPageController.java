package ir.pi.project.view.myPage;

import ir.pi.project.event.EditInfoFormEvent;
import ir.pi.project.event.SignUpFormEvent;
import ir.pi.project.listener.EditInfoFormListener;
import ir.pi.project.listener.SignUpFormListener;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class EditInfoPageController implements Initializable {

    private EditInfoFormListener editInfoFormListener;
    StringListener profileImageListener;

    Image image;

    @FXML
    TextField firstNameField;
    @FXML
    TextField lastNameField;
    @FXML
    TextField userNameField;
    @FXML
    TextField emailField;
    @FXML
    TextField birthDateField;
    @FXML
    TextField phoneNumberField;
    @FXML
    TextArea biographyArea;
    @FXML
    RadioButton EPBRadioButton;
    @FXML
    Label editInfoError;
    @FXML
    Label infoLabel;

    @FXML
    Circle profileCircle;

    public String getFirstNameField() {
        return firstNameField.getText();
    }

    public String getLastNameField() { return lastNameField.getText(); }

    public String getUserNameField() { return userNameField.getText(); }

    public String getEmailField() { return emailField.getText(); }

    public String getBirthDateField() { return birthDateField.getText(); }

    public String getPhoneNumberField() { return phoneNumberField.getText(); }

    public String getBiography(){ return  biographyArea.getText();}


    public void setEditInfoError(String string){editInfoError.setText(string);}

    public boolean isEPBCanSee() {
        return EPBRadioButton.isSelected();
    }

    public void save(){
        EditInfoFormEvent editInfoFormEvent=
                new EditInfoFormEvent(this,getFirstNameField(),
                        getLastNameField(),
                        getUserNameField(),
                        getEmailField(),
                        getPhoneNumberField(),
                        getBirthDateField(),
                        getBiography());

        editInfoFormListener.eventOccurred(editInfoFormEvent);
    }

    public void setEditInfoFormListener(EditInfoFormListener editInfoFormListener) {
        this.editInfoFormListener = editInfoFormListener;
    }

    public void setProfileImageListener(StringListener profileImageListener) {
        this.profileImageListener = profileImageListener;
    }

    public void changeImage(){
        profileImageListener.stringEventOccurred("ChangeImage");
    }
    public void removeImage(){
        profileImageListener.stringEventOccurred("RemoveImage");
    }

    public void setImage(Image image){
        if (image==null)
        profileCircle.setFill(null);
        else
        profileCircle.setFill(new ImagePattern(image));
    }

    public void setInfoLabel(String text,boolean isVisible){
        infoLabel.setVisible(isVisible);
        infoLabel.setText(text);
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


    public void update(User user){
        infoLabel.setVisible(false);
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        userNameField.setText(user.getUserName());
        emailField.setText(user.getEmail());
        birthDateField.setText(user.getBirthDate());
        phoneNumberField.setText(user.getPhoneNumber());
        EPBRadioButton.setSelected(user.isEPBCanSee());
        if(user.getBiography()!=null){
            biographyArea.setText(user.getBiography());
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

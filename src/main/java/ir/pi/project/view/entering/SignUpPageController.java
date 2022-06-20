package ir.pi.project.view.entering;

import com.sun.tools.javac.Main;
import ir.pi.project.MainApp;
import ir.pi.project.event.SignUpFormEvent;
import ir.pi.project.listener.SignUpFormListener;
import ir.pi.project.view.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpPageController implements Initializable {
    private SignUpFormListener signUpFormListener;

    @FXML
    TextField firstNameField;
    @FXML
    TextField lastNameField;
    @FXML
    TextField userNameField;
    @FXML
    TextField passwordField;
    @FXML
    TextField emailField;
    @FXML
    TextField birthDateField;
    @FXML
    TextField phoneNumberField;

    @FXML
    Label signUpError;

    @FXML
    RadioButton EPBRadioButton;

    public Label getSignUpError() {
        return signUpError;
    }

    public String getFirstNameField() {
        return firstNameField.getText();
    }

    public String getLastNameField() { return lastNameField.getText(); }

    public String getUserNameField() { return userNameField.getText(); }

    public String getPasswordField() { return passwordField.getText(); }

    public String getEmailField() { return emailField.getText(); }

    public String getBirthDateField() { return birthDateField.getText(); }

    public String getPhoneNumberField() { return phoneNumberField.getText(); }

    public void setSignUpError(String string){signUpError.setText(string);}

    public boolean isEPBCanSee() {
        return EPBRadioButton.isSelected();
    }

    public void setSignUpFormListener(SignUpFormListener signUpFormListener) {
        this.signUpFormListener = signUpFormListener;
    }

    public void signItUp(){
        SignUpFormEvent signUpFormEvent =
                new SignUpFormEvent(this,getFirstNameField(),
                        getLastNameField(),
                        getUserNameField(),
                        getPasswordField(),
                        getEmailField(),
                        getPhoneNumberField(),
                        getBirthDateField());

        signUpFormListener.eventOccurred(signUpFormEvent);

    }

    public void back() throws IOException {
        MainController.back();
//        MainController.loadWelcome();
//        WelcomePageController.loadWelcome((Stage)signUpError.getScene().getWindow());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

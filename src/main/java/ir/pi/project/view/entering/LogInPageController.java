package ir.pi.project.view.entering;

import com.sun.tools.javac.Main;
import ir.pi.project.event.LogInFormEvent;
//import ir.pi.project.listener.LogInFormListen;
import ir.pi.project.listener.LogInFormListener;
import ir.pi.project.view.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LogInPageController implements Initializable {

    private LogInFormListener logInFormListener;

    @FXML
    TextField userNameField;
    @FXML
    TextField passwordField;

    @FXML
    Label logInError;

    public String getUserNameField() { return userNameField.getText(); }

    public String getPasswordField() { return passwordField.getText(); }

    public Label getLogInError(){
        return logInError;
    }

    public void setLogInFormListener(LogInFormListener logInFormListener) {
        this.logInFormListener = logInFormListener;
    }

    public void logIn(){

            LogInFormEvent logInFormEvent =
                    new LogInFormEvent(this,getUserNameField(), getPasswordField());
            logInFormListener.eventOccurred(logInFormEvent);


        }


        public void setError(String text,boolean isVisible){
        logInError.setText(text);
        logInError.setVisible(isVisible);
        }

    public void back() throws IOException {
        MainController.loadWelcome();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logInError.setVisible(false);
    }
}



package ir.pi.project.view.myPage;

import ir.pi.project.event.NewTextEvent;
import ir.pi.project.listener.NewTextListener;
import ir.pi.project.view.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForwardTweetPageController implements Initializable {

    NewTextListener usernameListener;

    @FXML
    Label errorLabel;

    @FXML
    TextField usernameField;

    public void setUsernameListener(NewTextListener usernameListener) {
        this.usernameListener = usernameListener;
    }

    public void setError(String text,boolean isVisible){
        errorLabel.setVisible(isVisible);
        errorLabel.setText(text);
    }

    public void forward(){
        NewTextEvent newTextEvent=new NewTextEvent(this,usernameField.getText());
        usernameListener.eventOccurred(newTextEvent);
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
        errorLabel.setVisible(false);
    }
}

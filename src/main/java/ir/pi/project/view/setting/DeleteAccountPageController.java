package ir.pi.project.view.setting;

import ir.pi.project.event.NewTextEvent;
import ir.pi.project.listener.NewTextListener;
import ir.pi.project.view.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteAccountPageController implements Initializable {
    NewTextListener newTextListener;

    @FXML
    Label errorLabel;

    @FXML
    TextField passwordField;


    public void delete(){
        if(passwordField.getText()!=null&& !passwordField.getText().equals("")) {
            NewTextEvent newTextEvent = new NewTextEvent(this, passwordField.getText());
            newTextListener.eventOccurred(newTextEvent);
        }
    }

    public void setNewTextListener(NewTextListener newTextListener) {
        this.newTextListener = newTextListener;
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

    public void setErrorLabel(String text,boolean isVisible){
        errorLabel.setVisible(true);
        errorLabel.setText(text);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorLabel.setVisible(false);
    }
}

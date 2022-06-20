package ir.pi.project.view.message.groupChats;

import ir.pi.project.event.NewTextEvent;
import ir.pi.project.listener.NewTextListener;
import ir.pi.project.view.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewGroupChatPageController implements Initializable {

    NewTextListener newGroupListener;
    NewTextListener addUsernameListener;

    @FXML
    Label errorLabel,infoLabel;

    @FXML
    TextField usernameField,groupChatNameField;

    @FXML
    TextArea usernamesArea;


    public void setNewGroupListener(NewTextListener newGroupListener) {
        this.newGroupListener = newGroupListener;
    }

    public void setAddUsernameListener(NewTextListener addUsernameListener) {
        this.addUsernameListener = addUsernameListener;
    }

    public void add(){
        NewTextEvent newTextEvent=new NewTextEvent(this,usernameField.getText());
        addUsernameListener.eventOccurred(newTextEvent);
        usernameField.setText(null);

    }


    public void addToUsername(String username){
        if(usernamesArea.getText()!=null && !usernamesArea.getText().equals(""))
            usernamesArea.setText(usernamesArea.getText()+"\n"+username);
        else usernamesArea.setText(username);

    }

    public void make(){
        NewTextEvent newTextEvent=new NewTextEvent(this,groupChatNameField.getText());
        newGroupListener.eventOccurred(newTextEvent);
        groupChatNameField.setText(null);
        usernamesArea.setText(null);
    }



    public void setError(String text,boolean isVisible){
        errorLabel.setText(text);
        errorLabel.setVisible(isVisible);

    }
    public void setInfoLabel(String text,boolean isVisible){
        infoLabel.setText(text);
        infoLabel.setVisible(isVisible);

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
        infoLabel.setVisible(false);
    }
}

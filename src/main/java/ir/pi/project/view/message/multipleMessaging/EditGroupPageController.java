package ir.pi.project.view.message.multipleMessaging;

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
import java.util.List;
import java.util.ResourceBundle;

public class EditGroupPageController implements Initializable {
    NewTextListener addUsernameListener;
    NewTextListener removeUsernameListener;

    @FXML
    TextField addUsernameField,removeUsernameField;

    @FXML
    Label errorLabel;

    @FXML
    TextArea usernamesArea;


    public void setAddUsernameListener(NewTextListener addUsernameListener) {
        this.addUsernameListener = addUsernameListener;
    }

    public void setRemoveUsernameListener(NewTextListener removeUsernameListener) {
        this.removeUsernameListener = removeUsernameListener;
    }

    public void add(){
        NewTextEvent newTextEvent=new NewTextEvent(this,addUsernameField.getText());
        addUsernameField.setText(null);
        addUsernameListener.eventOccurred(newTextEvent);

    }

    public void remove(){
        NewTextEvent newTextEvent=new NewTextEvent(this,removeUsernameField.getText());
        removeUsernameField.setText(null);
        removeUsernameListener.eventOccurred(newTextEvent);

    }

    public void setError(String text,boolean isVisible){
        errorLabel.setText(text);
        errorLabel.setVisible(isVisible);

    }

    public void update(List<String> usernames){
        usernamesArea.setText(null);
        if(!usernames.isEmpty()) {
            for (String username :
                    usernames) {
                if (usernamesArea.getText()==null || usernamesArea.getText().equals(""))
                    usernamesArea.setText(username);
                else
                    usernamesArea.setText(usernamesArea.getText() + "\n" + username);
            }
        }
    }

    public void back(){ MainController.back(); }
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

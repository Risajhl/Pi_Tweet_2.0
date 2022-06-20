package ir.pi.project.view.message;

import ir.pi.project.listener.StringListener;
import ir.pi.project.view.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessagesPageController implements Initializable {
    StringListener messagesListener;

    public void setMessagesListener(StringListener messagesListener) {
        this.messagesListener = messagesListener;
    }

    @FXML
    Button groupChatsButton,directChatsButton,toUsersButton,toGroupsButton;

    @FXML
    Label errorLabel;


    public void groupChats(){messagesListener.stringEventOccurred("GroupChats");}
    public void directChats(){messagesListener.stringEventOccurred("DirectChats"); }
    public void toUsers(){messagesListener.stringEventOccurred("ToUsers"); }
    public void toGroups(){ messagesListener.stringEventOccurred("ToGroups"); }
    public void showGroups(){ messagesListener.stringEventOccurred("ShowGroups"); }
    public void savedMessages(){messagesListener.stringEventOccurred("SavedMessages");}
    public void savedTweets(){messagesListener.stringEventOccurred("SavedTweets");}

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
        errorLabel.setText(text);
        errorLabel.setVisible(isVisible);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorLabel.setVisible(false);
    }
}

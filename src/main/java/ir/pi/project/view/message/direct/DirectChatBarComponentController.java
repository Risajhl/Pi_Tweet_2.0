package ir.pi.project.view.message.direct;

import ir.pi.project.event.NewTextEvent;
import ir.pi.project.event.SearchUserEvent;
import ir.pi.project.listener.SearchUserListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class DirectChatBarComponentController implements Initializable {

    SearchUserListener showUserListener;

    @FXML
    Label unreadsNumLabel,usernameLabel;

    public void setShowUserListener(SearchUserListener showUserListener) {
        this.showUserListener = showUserListener;
    }

    public void updateNum(int num, boolean isOn){
        unreadsNumLabel.setVisible(isOn);
        unreadsNumLabel.setText(num+"");
    }
    public void updateUsername(String username){
        usernameLabel.setText(username);
    }

    public void showUser(){
        SearchUserEvent searchUserEvent=new SearchUserEvent(this,usernameLabel.getText());
        showUserListener.eventOccurred(searchUserEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

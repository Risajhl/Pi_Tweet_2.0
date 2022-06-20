package ir.pi.project.view.message.groupChats;

import ir.pi.project.listener.StringListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class GroupChatBarComponentController implements Initializable {

    StringListener groupChatBarListener;

    @FXML
    Label unreadsNumLabel,groupNameLabel;

    public void setGroupChatBarListener(StringListener groupChatBarListener) {
        this.groupChatBarListener = groupChatBarListener;
    }

    public void updateNum(int num, boolean isOn){
        unreadsNumLabel.setVisible(isOn);
        unreadsNumLabel.setText(num+"");
    }
    public void updateGroupName(String groupName){
        groupNameLabel.setText(groupName);
    }
    public void showGroupChat(){
        groupChatBarListener.stringEventOccurred("Show");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

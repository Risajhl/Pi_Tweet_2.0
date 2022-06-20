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

public class ShowGroupsPageController implements Initializable {

    NewTextListener newGroupListener;
    NewTextListener editGroupListener;

    @FXML
    TextField editGroupField,newGroupField;

    @FXML
    TextArea groupsListArea;

    @FXML
    Label errorLabel,infoLabel;

    public void setNewGroupListener(NewTextListener newGroupListener) {
        this.newGroupListener = newGroupListener;
    }

    public void setEditGroupListener(NewTextListener editGroupListener) {
        this.editGroupListener = editGroupListener;
    }

    public void setError(String text,boolean isVisible){
        errorLabel.setText(text);
        errorLabel.setVisible(isVisible);

    }
    public void setInfoLabel(String text,boolean isVisible){
        infoLabel.setText(text);
        infoLabel.setVisible(isVisible);

    }

    public void update(List<String> groupNames){
        groupsListArea.setText(null);
        if(!groupNames.isEmpty()) {
            for (String groupName :
                    groupNames) {
                if (groupsListArea.getText()==null || groupsListArea.getText().equals(""))
                    groupsListArea.setText(groupName);
                else
                    groupsListArea.setText(groupsListArea.getText() + "\n" + groupName);
            }
        }

    }

    public void edit(){
        NewTextEvent newTextEvent=new NewTextEvent(this,editGroupField.getText());
        editGroupListener.eventOccurred(newTextEvent);
        editGroupField.setText(null);
    }

    public void make(){
        NewTextEvent newTextEvent=new NewTextEvent(this,newGroupField.getText());
        newGroupListener.eventOccurred(newTextEvent);
        newGroupField.setText(null);
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
        infoLabel.setVisible(false);
        errorLabel.setVisible(false);
    }
}

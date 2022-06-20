package ir.pi.project.view.explorer;

import ir.pi.project.event.SearchUserEvent;
import ir.pi.project.listener.SearchUserListener;
import ir.pi.project.listener.StringListener;
import ir.pi.project.view.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExplorerPageController implements Initializable {
    StringListener searchPageStringListener;
    SearchUserListener searchUserListener;

    @FXML
    Label errorLabel;
    @FXML
    Label emptyWorldLabel;

    @FXML
    TextField usernameField;


    public void setSearchPageStringListener(StringListener searchPageStringListener) {
        this.searchPageStringListener = searchPageStringListener;
    }

    public void setSearchUserListener(SearchUserListener searchUserListener) {
        this.searchUserListener = searchUserListener;
    }




    public void search(){
        SearchUserEvent searchUserEvent=new SearchUserEvent(this,usernameField.getText());
        searchUserListener.eventOccurred(searchUserEvent);
    }

    public void world(){
        searchPageStringListener.stringEventOccurred("World");
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
        errorLabel.setVisible(isVisible);
        errorLabel.setText(text);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorLabel.setVisible(false);
    }
}

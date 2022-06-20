package ir.pi.project.view.myPage;

import ir.pi.project.event.NewTextEvent;
import ir.pi.project.listener.NewTextListener;
import ir.pi.project.view.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewTweetPageController implements Initializable {

    private NewTextListener newTweetListener;

    @FXML
    TextArea tweetArea;

    @FXML
    Button tweetButton;

    @FXML
    Label userNameLabel;

    public void setNewTweetListener(NewTextListener newTweetListener) {
        this.newTweetListener = newTweetListener;
    }

    public void newTweet(){

        newTweetListener.eventOccurred(new NewTextEvent(this,tweetArea.getText()));
        back();
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
        userNameLabel.setText(MainController.currentUser.getUserName()+": ");

    }
}

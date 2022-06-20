package ir.pi.project.view;

import ir.pi.project.listener.StringListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private StringListener mainMenuListener;

    @FXML
    Button myPageButton;
    @FXML
    Button timeLineButton;
    @FXML
    Button explorerButton;
    @FXML
    Button messagesButton;
    @FXML
    Button settingsButton;

    @FXML
    Label errorLabel;


    public void setMainMenuListener(StringListener mainMenuListener) {
        this.mainMenuListener = mainMenuListener;
    }

    public void myPage(){
        System.out.println("cuuute");
        mainMenuListener.stringEventOccurred("MyPage");

    }
    public void timeLine(){
        mainMenuListener.stringEventOccurred("TimeLine");

    }
    public void explorer(){
        mainMenuListener.stringEventOccurred("Explorer");

    }
    public void messages(){
        mainMenuListener.stringEventOccurred("Messages");

    }
    public void settings(){
        mainMenuListener.stringEventOccurred("Settings");

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

package ir.pi.project.view.myPage.lists.blacklist;

import ir.pi.project.listener.StringListener;
import ir.pi.project.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BlacklistComponentController implements Initializable {

    StringListener blacklistComponentListener;
    VBox content;
    @FXML
    AnchorPane pane;

    @FXML
    Label usernameLabel;


    public void setContent(VBox content) {
        this.content = content;
    }

    public void setBlacklistComponentListener(StringListener blacklistComponentListener) {
        this.blacklistComponentListener = blacklistComponentListener;
    }

    public void unblock(){
        blacklistComponentListener.stringEventOccurred("Unblock");
        content.getChildren().remove(pane);
    }

    public void update(User blackUser){
        usernameLabel.setText(blackUser.getUserName());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

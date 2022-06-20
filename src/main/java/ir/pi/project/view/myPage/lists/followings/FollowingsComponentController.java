package ir.pi.project.view.myPage.lists.followings;

import ir.pi.project.listener.StringListener;
import ir.pi.project.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class FollowingsComponentController implements Initializable {

    StringListener followingsComponentListener;
    VBox content;
    @FXML
    AnchorPane pane;

    @FXML
    Label usernameLabel;

    @FXML
    Circle profileCircle;


    public void setContent(VBox content) {
        this.content = content;
    }

    public void setFollowersComponentListener(StringListener followingsComponentListener) {
        this.followingsComponentListener = followingsComponentListener;
    }

    public void unfollow(){
        followingsComponentListener.stringEventOccurred("Unfollow");
        content.getChildren().remove(pane);
    }

    public void update(User follower){
        usernameLabel.setText(follower.getUserName());
    }
    public void setProfileImage(Image image){
        if(image!=null)
            profileCircle.setFill(new ImagePattern(image));
        else
            profileCircle.setFill(Color.BLACK);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

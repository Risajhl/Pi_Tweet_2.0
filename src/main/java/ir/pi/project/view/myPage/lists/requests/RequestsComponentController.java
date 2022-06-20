package ir.pi.project.view.myPage.lists.requests;

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

public class RequestsComponentController implements Initializable {
    StringListener requestsComponentListener;
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

    public void setRequestsComponentListener(StringListener requestsComponentListener) {
        this.requestsComponentListener = requestsComponentListener;
    }

    public void delete(){
        requestsComponentListener.stringEventOccurred("Delete");
        content.getChildren().remove(pane);
        System.out.println("deleted");
    }
    public void accept(){
        requestsComponentListener.stringEventOccurred("Accept");
        content.getChildren().remove(pane);
        System.out.println("accepted");
    }
    public void deleteAndInform(){
        requestsComponentListener.stringEventOccurred("Delete and Inform");
        content.getChildren().remove(pane);
        System.out.println("deleted without informing");

    }


    public void setProfileImage(Image image){
        if(image!=null)
            profileCircle.setFill(new ImagePattern(image));
        else
            profileCircle.setFill(Color.BLACK);
    }

    public void update(User requester){
        usernameLabel.setText(requester.getUserName());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

package ir.pi.project.view.myPage;

import ir.pi.project.config.texts.myPage.MyPageTexts;
import ir.pi.project.event.NewTextEvent;
import ir.pi.project.listener.NewTextListener;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javax.imageio.ImageIO;

public class MyPagePageController implements Initializable {
    private StringListener myPageListener;
    private NewTextListener newTweetListener;
    private MyPageTexts myPageTexts;

    {
        try {
            myPageTexts = new MyPageTexts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    Label userNameLabel,nameLabel,emailLabel,birthDateLabel,errorLabel,
            phoneNumberLabel,followersNumLabel,followingsNumLabel;

    @FXML
    Button newTweetButton,tweetsButton, editButton,followersButton,uploadImage,
            followingsButton,notificationsButton,requestsButton,pendingsButton,blackListButton;

    @FXML
    TextArea tweetArea,biographyArea;

    @FXML
    Circle profileCircle;

    @FXML
    Rectangle imageRect;


    public void setMyPageListener(StringListener myPageListener) {
        this.myPageListener = myPageListener;
    }
    public void setNewTweetListener(NewTextListener newTweetListener) {
        this.newTweetListener = newTweetListener;
    }

    public void newTweet(){
        newTweetListener.eventOccurred(new NewTextEvent(this,tweetArea.getText()));
        tweetArea.setText(null);
    }


    public void editInfo(){myPageListener.stringEventOccurred("EditInfo");}

    public void tweets(){myPageListener.stringEventOccurred("Tweets");}

    public void followers(){myPageListener.stringEventOccurred("Followers");}
    public void followings(){myPageListener.stringEventOccurred("Followings");}
    public void blackList(){myPageListener.stringEventOccurred("Blacklist");}
    public void notifications(){myPageListener.stringEventOccurred("Notifications");}
    public void requests(){myPageListener.stringEventOccurred("Requests");}
    public void pendings(){myPageListener.stringEventOccurred("Pendings");}
    public void uploadImage(){myPageListener.stringEventOccurred("UploadImage");}

    public void setProfileImage(Image image){
        if(image!=null)
            profileCircle.setFill(new ImagePattern(image));
        else
            profileCircle.setFill(Color.BLACK);
    }



    public void setErrorLabel(String text,boolean isVisible){
        errorLabel.setText(text);
        errorLabel.setVisible(isVisible);
    }

    public void setTweetImage(Image image){
        if (image==null) {
            imageRect.setFill(null);
            imageRect.setVisible(false);
            System.out.println("invisible");
        }
        else {
            imageRect.setFill(new ImagePattern(image));
            imageRect.setVisible(true);
        }
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


    public void update(User user){
        userNameLabel.setText(user.getUserName());
        nameLabel.setText(user.getFirstName()+" "+user.getLastName());
        phoneNumberLabel.setText("Phone Number: "+user.getPhoneNumber());
        emailLabel.setText("Email: "+user.getEmail());
        birthDateLabel.setText("Birth Date: "+user.getBirthDate());
        if(user.getBiography()==null || user.getBiography().equals("")){
            biographyArea.setText(myPageTexts.getNoBio());
        }else {
            biographyArea.setText(user.getBiography());
        }
        followersNumLabel.setText(user.getFollowers().size()+"");
        followingsNumLabel.setText(user.getFollowings().size()+"");
        errorLabel.setVisible(false);
        tweetArea.setText(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageRect.setVisible(false);
    }
}

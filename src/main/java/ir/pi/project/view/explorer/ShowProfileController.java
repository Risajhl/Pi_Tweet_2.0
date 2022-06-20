package ir.pi.project.view.explorer;

import ir.pi.project.config.texts.explorer.ShowProfileTexts;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class ShowProfileController implements Initializable {

    StringListener showProfileListener;
    ShowProfileTexts showProfileTexts;

    {
        try {
            showProfileTexts = new ShowProfileTexts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    Label nameLabel,userNameLabel,followingStateLabel, lastSeenLabel,blockStateLabel,errorLabel,
            emailLabel,birthDateLabel,phoneNumberLabel,followersNumLabel,followingsNumLabel;
    @FXML
    TextArea biographyArea;

    @FXML
    Button followButton,blockButton,messageButton,muteButton,reportButton,tweetsButton;

    @FXML
    Circle profileCircle;


    public void setShowProfileListener(StringListener showProfileListener) {
        this.showProfileListener = showProfileListener;
    }

    public void update(User user, boolean EBPCanSee, boolean lastSeenCanSee, boolean uBlocked, boolean urBlocked,boolean isFollowing,boolean isMuted ,String followingState){

        nameLabel.setText(user.getFirstName()+" "+user.getLastName());
        userNameLabel.setText(user.getUserName());
        followersNumLabel.setText(user.getFollowers().size()+"");
        followingsNumLabel.setText(user.getFollowings().size()+"");
        if(user.getBiography()!=null && !user.getBiography().equals(""))biographyArea.setText(user.getBiography());
        else biographyArea.setText(showProfileTexts.getNoBio());



        if(uBlocked){
            blockStateLabel.setVisible(true);
            blockStateLabel.setText(showProfileTexts.getuBlocked());
            blockButton.setText(showProfileTexts.getUnblock());
            messageButton.setVisible(false);
            followButton.setVisible(false);
        }
        else if(urBlocked){
            blockStateLabel.setVisible(true);
            blockStateLabel.setText(showProfileTexts.getUrBlocked());
            messageButton.setVisible(false);
            followButton.setVisible(false);
        }
        else {
            if(isMuted)muteButton.setText(showProfileTexts.getUnmute());
            else muteButton.setText(showProfileTexts.getMute());

            blockStateLabel.setVisible(false);
            blockButton.setText(showProfileTexts.getBlock());
            messageButton.setVisible(true);
            followButton.setVisible(true);
            if(isFollowing || user.isPublic()) {

                if (EBPCanSee) {
                    emailLabel.setVisible(true);
                    emailLabel.setText("Email: " + user.getEmail());
                    phoneNumberLabel.setVisible(true);
                    phoneNumberLabel.setText("Phone Number: " + user.getPhoneNumber());
                    birthDateLabel.setVisible(true);
                    birthDateLabel.setText("Birth Date: " + user.getBirthDate());
                }
                if (user.isOnline()) lastSeenLabel.setText(showProfileTexts.getOnline());
                else {
                    if (lastSeenCanSee) lastSeenLabel.setText(user.getLastSeen());
                    else lastSeenLabel.setText(showProfileTexts.getLastSeenRecently());
                }

                tweetsButton.setVisible(true);


            }else {
                tweetsButton.setVisible(false);
            }

            followingStateLabel.setText(followingState);

        }



    }

    public void setProfileImage(Image image){
        if(image!=null)
            profileCircle.setFill(new ImagePattern(image));
        else
            profileCircle.setFill(Color.BLACK);
    }


    public void changeFollowButtonText(String s){ followButton.setText(s); }
    public void changeFollowingState(String s){followingStateLabel.setText(s);}
    public void changeBlockButtonText(String s){ blockButton.setText(s); }



    public void mute(){
     showProfileListener.stringEventOccurred("Mute");
    }
    public void follow(){
     showProfileListener.stringEventOccurred("Follow");
    }
    public void report(){
     showProfileListener.stringEventOccurred("Report");
    }
    public void block(){
     showProfileListener.stringEventOccurred("Block");
    }
    public void message(){
     showProfileListener.stringEventOccurred("Message");
    }
    public void tweets(){
     showProfileListener.stringEventOccurred("Tweets");
    }
    public void showImage(){ showProfileListener.stringEventOccurred("ShowImage"); }

    public void setErrorLabel(String text,boolean isVisible){
        errorLabel.setText(text);
        errorLabel.setVisible(isVisible);
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
        emailLabel.setVisible(false);
        birthDateLabel.setVisible(false);
        phoneNumberLabel.setVisible(false);
        blockStateLabel.setVisible(false);
        errorLabel.setVisible(false);

    }
}

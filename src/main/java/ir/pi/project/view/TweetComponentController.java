package ir.pi.project.view;

import ir.pi.project.config.texts.ShowTweetTexts;
import ir.pi.project.event.NewCommentEvent;
import ir.pi.project.listener.NewCommentListener;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.Tweet;
import ir.pi.project.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TweetComponentController implements Initializable {

    private ShowTweetTexts showTweetTexts;

    {
        try {
            showTweetTexts = new ShowTweetTexts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    StringListener tweetComponentListener;
    NewCommentListener newCommentListener;


    @FXML
    Label userNameLabel, likesNumLabel, dateLabel,errorLabel,infoLabel,retweetedLabel;


    @FXML
    TextArea tweetArea, commentArea;

    @FXML
    Circle profileCircle;

    @FXML
    Rectangle imageRect,commentImageRect;


    public void setTweetComponentListener(StringListener tweetComponentListener) {
        this.tweetComponentListener = tweetComponentListener;
    }

    public void setNewCommentListener(NewCommentListener newCommentListener) {
        this.newCommentListener = newCommentListener;
    }

    public void update(Tweet tweet, User writer){
        userNameLabel.setText(writer.getUserName());
        likesNumLabel.setText(tweet.getLikesNum()+"");
        tweetArea.setText(tweet.getText());
        commentArea.setText(null);
        String date=tweet.getTime().getYear()+" "+tweet.getTime().getMonth()+" "+tweet.getTime().getDayOfMonth()+
                "  "+tweet.getTime().getHour()+":"+tweet.getTime().getMinute();
        dateLabel.setText(date);
        setErrorLabel(showTweetTexts.getNoComment(),false);
    }

    public void setErrorLabel(String text,boolean isVisible){
        errorLabel.setText(text);
        errorLabel.setVisible(isVisible);
    }


    public void next(){
        tweetComponentListener.stringEventOccurred("Next");
    }
    public void previous(){
        tweetComponentListener.stringEventOccurred("Previous");
    }
    public void like(){
        tweetComponentListener.stringEventOccurred("Like");
    }
    public void retweet(){
        tweetComponentListener.stringEventOccurred("Retweet");
    }
    public void save(){
        tweetComponentListener.stringEventOccurred("Save");
    }
    public void forward(){
        tweetComponentListener.stringEventOccurred("Forward");
    }
    public void comments(){ tweetComponentListener.stringEventOccurred("Comments"); }
    public void report(){ tweetComponentListener.stringEventOccurred("Report"); }
    public void comment(){ tweetComponentListener.stringEventOccurred("Comment"); }
    public void uploadCommentImage(){ tweetComponentListener.stringEventOccurred("UploadCommentImage"); }




    public void newComment(User writer,Tweet tweet){
        NewCommentEvent newCommentEvent=
                new NewCommentEvent(this,
                        commentArea.getText(),writer,tweet);
        newCommentListener.eventOccurred(newCommentEvent);
        commentArea.setText(null);

    }

    public void setProfileImage(Image image){
        if(image!=null)
            profileCircle.setFill(new ImagePattern(image));
        else
            profileCircle.setFill(Color.BLACK);
    }

    public void setTweetImage(Image image){
        if (image==null) {
            tweetArea.setMaxWidth(770);
            imageRect.setFill(null);
            imageRect.setVisible(false);
        }
        else {
            tweetArea.setMaxWidth(480);
            imageRect.setFill(new ImagePattern(image));
            imageRect.setVisible(true);
        }
    }
    public void setCommentImage(Image image){
        if (image==null) {
            commentImageRect.setFill(null);
            commentImageRect.setVisible(false);
        }
        else {
            commentImageRect.setFill(new ImagePattern(image));
            commentImageRect.setVisible(true);
        }
    }



    public void setInfoLabel(String text,boolean isVisible){
        infoLabel.setVisible(isVisible);
        infoLabel.setText(text);
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

    public void setRetweetedLabel(boolean isVisible){
        retweetedLabel.setVisible(isVisible);

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageRect.setVisible(false);
        commentImageRect.setVisible(false);
        errorLabel.setVisible(false);
        infoLabel.setVisible(false);
        retweetedLabel.setVisible(false);
    }
}

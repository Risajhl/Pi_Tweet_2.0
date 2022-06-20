package ir.pi.project.view.message.multipleMessaging;

import ir.pi.project.event.NewMessageEvent;
import ir.pi.project.event.NewTextEvent;
import ir.pi.project.listener.NewTextListener;
import ir.pi.project.listener.StringListener;
import ir.pi.project.view.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MultipleToUsersController implements Initializable {

    NewTextListener newMessageListener;
    NewTextListener addUsernameListener;
    StringListener sendMessageListener;


    @FXML
    Label errorLabel,sentLabel;

    @FXML
    TextField usernameField;

    @FXML
    TextArea usernamesArea,messageArea;

    @FXML
    Rectangle imageRect;

    public void setNewMessageListener(NewTextListener newMessageListener) {
        this.newMessageListener = newMessageListener;
    }

    public void setSendMessageListener(StringListener sendMessageListener) {
        this.sendMessageListener = sendMessageListener;
    }

    public void setAddUsernameListener(NewTextListener addUsernameListener) {
        this.addUsernameListener = addUsernameListener;
    }

    public void add(){
        NewTextEvent newTextEvent=new NewTextEvent(this,usernameField.getText());
        addUsernameListener.eventOccurred(newTextEvent);
        usernameField.setText(null);

    }
    public void send(){
        NewTextEvent newTextEvent =new NewTextEvent(this,messageArea.getText());
        newMessageListener.eventOccurred(newTextEvent);
        messageArea.setText(null);
        usernamesArea.setText(null);

    }

    public void showSent(boolean isVisible){
        sentLabel.setVisible(isVisible);
    }

    public void addToUsername(String username){
        if(usernamesArea.getText()!=null && !usernamesArea.getText().equals(""))
        usernamesArea.setText(usernamesArea.getText()+"\n"+username);
        else usernamesArea.setText(username);

    }

    public void setError(String text,boolean isVisible){
        errorLabel.setText(text);
        errorLabel.setVisible(isVisible);

    }

    public void uploadImage(){
        sendMessageListener.stringEventOccurred("UploadImage");
    }

    public void setImage(Image image){
        if (image==null) {

            imageRect.setFill(null);
            imageRect.setVisible(false);
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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorLabel.setVisible(false);
        sentLabel.setVisible(false);
        imageRect.setVisible(false);

    }
}

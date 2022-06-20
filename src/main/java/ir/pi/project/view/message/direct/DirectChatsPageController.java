package ir.pi.project.view.message.direct;

import ir.pi.project.config.addresses.FXMLConfig;
import ir.pi.project.controller.messages.direct.DirectChatBarComponentLogic;
import ir.pi.project.event.SearchUserEvent;
import ir.pi.project.listener.SearchUserListener;
import ir.pi.project.listener.StringListener;
import ir.pi.project.view.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class DirectChatsPageController implements Initializable {
    SearchUserListener openChatListener;
    StringListener reloadListener;
    FXMLConfig fxmlConfig;

    {
        try {
            fxmlConfig = new FXMLConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    ScrollPane scroller;

    @FXML
    TextField usernameField;

    @FXML
    Label errorLabel;

    public void setOpenChatListener(SearchUserListener openChatListener) {
        this.openChatListener = openChatListener;
    }

    public void setReloadListener(StringListener reloadListener) {
        this.reloadListener = reloadListener;
    }

    public void openChat(){
        SearchUserEvent openChatEvent=new SearchUserEvent(this,usernameField.getText());
        openChatListener.eventOccurred(openChatEvent);
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


    public void reload(){
        reloadListener.stringEventOccurred("Reload");
    }

    public void update(Map<String,Integer> unreadUsernames, List<String> usernames) {
        try {

            VBox content = new VBox(5);
            scroller.setContent(content);
            scroller.setFitToWidth(true);

            if(!unreadUsernames.isEmpty()){
                for (HashMap.Entry<String,Integer> entry : unreadUsernames.entrySet()) {
                    FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getDirectChatBarComponent()));
                    AnchorPane anchorPane=fxmlLoader.load();
                    content.getChildren().add(anchorPane);

                    new DirectChatBarComponentLogic(fxmlLoader.getController(),entry.getValue(),entry.getKey());

                }
            }


            if(!usernames.isEmpty()){
                for (String username:
                     usernames) {
                    FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getDirectChatBarComponent()));
                    AnchorPane anchorPane=fxmlLoader.load();
                    content.getChildren().add(anchorPane);

                    new DirectChatBarComponentLogic(fxmlLoader.getController(),-1,username);

                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
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

package ir.pi.project.view.message.groupChats;

import ir.pi.project.config.addresses.FXMLConfig;
import ir.pi.project.controller.messages.groupChat.GroupChatBarComponentLogic;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.GroupChat;
import ir.pi.project.view.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class GroupChatsPageController implements Initializable {

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

    public void setReloadListener(StringListener reloadListener) {
        this.reloadListener = reloadListener;
    }


    public void update(Map<Integer,Integer>  unreadGroupChats, List<GroupChat> groupChats){
        try {
            System.out.println(unreadGroupChats);
            System.out.println(groupChats);

            VBox content = new VBox(5);
            scroller.setContent(content);
            scroller.setFitToWidth(true);

            if(!unreadGroupChats.isEmpty()){
                for (HashMap.Entry<Integer,Integer> entry : unreadGroupChats.entrySet()) {
                    FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getGroupChatBarComponent()));
                    AnchorPane anchorPane=fxmlLoader.load();
                    content.getChildren().add(anchorPane);

                    new GroupChatBarComponentLogic(fxmlLoader.getController(),entry.getValue(),entry.getKey());
                }
            }


            if(!groupChats.isEmpty()){


                for (GroupChat groupChat:
                     groupChats) {
                    FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getGroupChatBarComponent()));
                    AnchorPane anchorPane=fxmlLoader.load();
                    content.getChildren().add(anchorPane);

                    new GroupChatBarComponentLogic(fxmlLoader.getController(),-1,groupChat.getId());

                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void newGroupChat(){
        try {
            MainController.loadNewGroupChatPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload(){
        reloadListener.stringEventOccurred("Reload");
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

    }
}

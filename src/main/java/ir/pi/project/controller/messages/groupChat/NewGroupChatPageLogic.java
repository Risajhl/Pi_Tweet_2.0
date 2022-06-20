package ir.pi.project.controller.messages.groupChat;

import ir.pi.project.config.addresses.InfoConfig;
import ir.pi.project.config.texts.messages.MessagesText;
import ir.pi.project.controller.MainLogic;
import ir.pi.project.controller.UserLogic;
import ir.pi.project.controller.messages.direct.ShowDirectChatLogic;
import ir.pi.project.db.ID;
import ir.pi.project.event.NewTextEvent;
import ir.pi.project.listener.NewTextListener;
import ir.pi.project.model.GroupChat;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.message.groupChats.NewGroupChatPageController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewGroupChatPageLogic extends MainLogic {
    static private final Logger logger = LogManager.getLogger(NewGroupChatPageLogic.class);
    private final InfoConfig infoConfig=new InfoConfig();
    private final MessagesText messagesText=new MessagesText();
    NewGroupChatPageController newGroupChatPageController;
    UserLogic userLogic;
    List<String> usernames;
    public NewGroupChatPageLogic(NewGroupChatPageController newGroupChatPageController) throws IOException {
        this.newGroupChatPageController=newGroupChatPageController;
        this.userLogic=new UserLogic();
        this.usernames=new ArrayList<>();

        newGroupChatPageController.setAddUsernameListener(new NewTextListener() {
            @Override
            public void eventOccurred(NewTextEvent newTextEvent) {
                newGroupChatPageController.setError("",false);
                check(newTextEvent.getText());
            }
        });

        newGroupChatPageController.setNewGroupListener(new NewTextListener() {
            @Override
            public void eventOccurred(NewTextEvent newTextEvent) {
                newGroupChatPageController.setError("",false);
                newGroupChatPageController.setError("",false);
                makeTheGroupChat(newTextEvent.getText());
            }
        });
    }



    private void check(String username){
        User user=context.Users.get(MainController.currentUser.getId());
        File usersDirectory = new File(infoConfig.getUsersDirectory());
        boolean exists = false;
        for (File file :
                usersDirectory.listFiles()) {
            User user1 = context.Users.get(ID.getIdFromFileName(file.getName()));

            if (user1.getUserName().equals(username) && user1.isActive()) {
                exists = true;
                if (user1.getId() == MainController.currentUser.getId())
                    newGroupChatPageController.setError(messagesText.getUrIn(),true);
                else {
                    if(userLogic.isFollowing(user.getId(),user1.getId())||userLogic.isFollowing(user1.getId(),user.getId())) {

                        if(!usernames.contains(username)){
                            usernames.add(username);
                            newGroupChatPageController.addToUsername(username);
                        }
                        else
                            newGroupChatPageController.setError(messagesText.getChosenUser(),true);

                    }
                    else
                        newGroupChatPageController.setError(messagesText.getFf(),true);

                }
            }
        }
        if (!exists) {
            newGroupChatPageController.setError(messagesText.getUserNotFound(),true);
        }
    }



    private void makeTheGroupChat(String groupChatName){
        try {

            if(usernames.isEmpty())
                newGroupChatPageController.setError(messagesText.getAtLeastOne(),true);
            else {
                User user=context.Users.get(MainController.currentUser.getId());
                GroupChat groupChat=new GroupChat(groupChatName,user);
                groupChat.getMembers().add(user.getId());
                user.getGroupChats().add(groupChat.getId());
                for (String username :
                        usernames) {
                    File usersDirectory = new File(infoConfig.getUsersDirectory());
                    for (File file :
                            usersDirectory.listFiles()) {
                        User user1 = context.Users.get(ID.getIdFromFileName(file.getName()));

                        if (user1.getUserName().equals(username) && user1.isActive()) {
                            user1.getGroupChats().add(groupChat.getId());
                            groupChat.getMembers().add(user1.getId());
                            context.Users.update(user1);
                            context.GroupChats.update(groupChat);
                        }
                    }
                    context.Users.update(user);
                    newGroupChatPageController.setInfoLabel(messagesText.getGroupChatMade(),true);
                    logger.info("user made a new groupChat with id: "+groupChat.getId());

                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }


}

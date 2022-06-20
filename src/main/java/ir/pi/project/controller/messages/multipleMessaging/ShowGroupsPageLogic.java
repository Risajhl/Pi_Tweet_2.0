package ir.pi.project.controller.messages.multipleMessaging;

import ir.pi.project.config.texts.messages.MessagesText;
import ir.pi.project.controller.MainLogic;
import ir.pi.project.event.NewTextEvent;
import ir.pi.project.listener.NewTextListener;
import ir.pi.project.model.Group;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.message.multipleMessaging.ShowGroupsPageController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowGroupsPageLogic extends MainLogic {
    static private final Logger logger = LogManager.getLogger(ShowGroupsPageLogic.class);
    private final MessagesText messagesText=new MessagesText();

    ShowGroupsPageController showGroupsPageController;
    public ShowGroupsPageLogic(ShowGroupsPageController showGroupsPageController) throws IOException {
        this.showGroupsPageController=showGroupsPageController;
        showGroupsPageController.setEditGroupListener(new NewTextListener() {
            @Override
            public void eventOccurred(NewTextEvent newTextEvent) {
             showGroupsPageController.setError("",false);
             showGroupsPageController.setInfoLabel("",false);
                if(isInGroups(newTextEvent.getText()))edit(newTextEvent.getText());
                else showGroupsPageController.setError(messagesText.getGroupNotFound(),true);
                showGroupsPageController.update(groupNames());

            }
        });

        showGroupsPageController.setNewGroupListener(new NewTextListener() {
            @Override
            public void eventOccurred(NewTextEvent newTextEvent) {
                showGroupsPageController.setError("",false);
                showGroupsPageController.setInfoLabel("",false);

                if(isInGroups(newTextEvent.getText()))showGroupsPageController.setError(messagesText.getTakenGroupName(),true);
                else makeGroup(newTextEvent.getText());

                showGroupsPageController.update(groupNames());

            }
        });

        showGroupsPageController.update(groupNames());

    }


    private void edit(String groupName){
        try {
            User user = context.Users.get(MainController.currentUser.getId());
            List<String> list = new ArrayList<>();
            for (Integer groupId :
                    user.getGroups()) {
                Group group = context.Groups.get(groupId);
                if (group.getName().equals(groupName)) MainController.loadEditGroupPage(groupId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isInGroups(String groupName){
        User user = context.Users.get(MainController.currentUser.getId());
        for (Integer groupId :
                user.getGroups()) {
            Group group = context.Groups.get(groupId);

            if(group.getName().equals(groupName)) return true;
        }

        return false;
    }

    private void makeGroup(String groupName){
        User user = context.Users.get(MainController.currentUser.getId());
        Group group=new Group(groupName,user.getId());
        user.getGroups().add(group.getId());
        context.Groups.update(group);
        context.Users.update(user);
        showGroupsPageController.setInfoLabel(messagesText.getGroupMade(),true);
        logger.info("user made a new group with id: "+group.getId());

    }

    private List<String> groupNames(){
        User user=context.Users.get(MainController.currentUser.getId());
        List<String> list=new ArrayList<>();
        for (Integer groupId:
             user.getGroups()) {
            Group group=context.Groups.get(groupId);
            list.add(group.getName());
        }
        System.out.println(list);
        return list;
    }
}

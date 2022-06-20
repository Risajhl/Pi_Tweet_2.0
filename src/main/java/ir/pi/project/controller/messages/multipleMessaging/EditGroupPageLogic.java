package ir.pi.project.controller.messages.multipleMessaging;

import ir.pi.project.config.addresses.InfoConfig;
import ir.pi.project.config.texts.messages.MessagesText;
import ir.pi.project.controller.MainLogic;
import ir.pi.project.controller.UserLogic;
import ir.pi.project.controller.messages.groupChat.NewGroupChatPageLogic;
import ir.pi.project.db.ID;
import ir.pi.project.event.NewTextEvent;
import ir.pi.project.listener.NewTextListener;
import ir.pi.project.model.Group;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.message.multipleMessaging.EditGroupPageController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditGroupPageLogic extends MainLogic {
    static private final Logger logger = LogManager.getLogger(EditGroupPageLogic.class);
    private final InfoConfig infoConfig=new InfoConfig();
    private final MessagesText messagesText=new MessagesText();
    EditGroupPageController editGroupPageController;
    int groupId;
    UserLogic userLogic;

    public EditGroupPageLogic(EditGroupPageController editGroupPageController, int groupId) throws IOException {
        this.editGroupPageController = editGroupPageController;
        this.groupId = groupId;
        this.userLogic = new UserLogic();
        editGroupPageController.update(usernames(groupId));

        editGroupPageController.setAddUsernameListener(new NewTextListener() {
            @Override
            public void eventOccurred(NewTextEvent newTextEvent) {
                editGroupPageController.setError("", false);

                if (newTextEvent.getText().equals(MainController.currentUser.getUserName()))
                    editGroupPageController.setError(messagesText.getCantAddUrself(), true);
                else if (!isInGroup(newTextEvent.getText())) add(newTextEvent.getText());
                else editGroupPageController.setError(messagesText.getChosenUser(), true);
                editGroupPageController.update(usernames(groupId));

            }
        });

        editGroupPageController.setRemoveUsernameListener(new NewTextListener() {
            @Override
            public void eventOccurred(NewTextEvent newTextEvent) {
                editGroupPageController.setError("", false);
                if (isInGroup(newTextEvent.getText())) remove(newTextEvent.getText());
                else editGroupPageController.setError(messagesText.getUserNotFound(), true);
                editGroupPageController.update(usernames(groupId));

            }
        });

    }

    private void remove(String username) {
        Group group = context.Groups.get(groupId);
        for (int i = 0; i < group.getMembers().size(); i++) {
            User member = context.Users.get(group.getMembers().get(i));
            if (member.getUserName().equals(username)) {
                group.getMembers().remove(i);
                i--;
                context.Groups.update(group);
            }
        }

        logger.info("user removed " + username + "from group with id: " + group.getId());
    }

    private void add(String username) {
        Group group = context.Groups.get(groupId);
        User user = context.Users.get(MainController.currentUser.getId());
        try {

            File usersDirectory = new File(infoConfig.getUsersDirectory());
            boolean exists = false;
            for (File file :
                    usersDirectory.listFiles()) {
                User user1 = context.Users.get(ID.getIdFromFileName(file.getName()));
                if (user1.getUserName().equals(username) && user1.isActive()) {
                    exists = true;
                    if (user1.getUserName().equals(username)) {
                        if (user1.isActive()) {
                            if (userLogic.isFollowing(user1.getId(), user.getId()) || userLogic.isFollowing(user.getId(), user1.getId())) {
                                group.getMembers().add(user1.getId());
                                context.Groups.update(group);
                                context.Users.update(user1);
                                logger.info("user added " + username + "to a group with id: " + group.getId());

                            } else
                                editGroupPageController.setError(messagesText.getFf(), true);


                        }
                    }

                }

            }
            if (!exists) {
                editGroupPageController.setError(messagesText.getUserNotFound(), true);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isInGroup(String username) {
        Group group = context.Groups.get(groupId);
        for (Integer memberId :
                group.getMembers()) {
            User member = context.Users.get(memberId);
            if (member.getUserName().equals(username)) return true;
        }

        return false;
    }

    private List<String> usernames(int groupId) {
        Group group = context.Groups.get(groupId);
        List<String> list = new ArrayList<>();
        for (Integer memberId :
                group.getMembers()) {
            User member = context.Users.get(memberId);
            list.add(member.getUserName());
        }
        return list;
    }

}

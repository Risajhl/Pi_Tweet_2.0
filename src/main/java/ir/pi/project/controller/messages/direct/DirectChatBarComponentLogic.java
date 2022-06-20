package ir.pi.project.controller.messages.direct;

import ir.pi.project.config.addresses.InfoConfig;
import ir.pi.project.controller.MainLogic;
import ir.pi.project.db.ID;
import ir.pi.project.event.SearchUserEvent;
import ir.pi.project.listener.SearchUserListener;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.message.direct.DirectChatBarComponentController;

import java.io.File;
import java.io.IOException;

public class DirectChatBarComponentLogic extends MainLogic {
    DirectChatBarComponentController directChatBarComponentController;
    private final InfoConfig infoConfig=new InfoConfig();


    public DirectChatBarComponentLogic(DirectChatBarComponentController directChatBarComponentController, int num, String username) throws IOException {
        this.directChatBarComponentController = directChatBarComponentController;
        directChatBarComponentController.setShowUserListener(new SearchUserListener() {
            @Override
            public void eventOccurred(SearchUserEvent searchUserEvent) {
                showUser(searchUserEvent.getUsername());
            }
        });
        if (num != -1) {
            directChatBarComponentController.updateNum(num, true);
        } else directChatBarComponentController.updateNum(-1, false);
        directChatBarComponentController.updateUsername(username);

    }


    private void showUser(String username) {
        File usersDirectory = new File(infoConfig.getUsersDirectory());
        for (File file :
                usersDirectory.listFiles()) {
            User user1 = context.Users.get(ID.getIdFromFileName(file.getName()));
            if (user1.getUserName().equals(username) && user1.isActive()) {
                try {
                    MainController.showProfile(user1);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }

    }


}





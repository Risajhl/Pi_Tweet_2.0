package ir.pi.project.controller.messages;

import ir.pi.project.config.texts.messages.MessagesText;
import ir.pi.project.controller.MainLogic;
import ir.pi.project.listener.StringListener;
import ir.pi.project.view.MainController;
import ir.pi.project.view.message.MessagesPageController;

import java.io.IOException;

public class MessagesPageLogic extends MainLogic {
    private final MessagesText messagesText=new MessagesText();
    MessagesPageController messagesPageController;
    public MessagesPageLogic(MessagesPageController messagesPageController) throws IOException {
        this.messagesPageController=messagesPageController;
        messagesPageController.setMessagesListener(new StringListener() {
            @Override
            public void stringEventOccurred(String string) {
                messagesPageController.setErrorLabel("",false);
                try {

                    switch (string) {
                        case "DirectChats" -> MainController.loadDirectChatsPage();
                        case "GroupChats" -> MainController.loadGroupChatsPage();
                        case "ToUsers" -> MainController.loadMultipleToUsersPage();
                        case "ToGroups" -> MainController.loadMultipleToGroupsPage();
                        case "ShowGroups" -> MainController.loadShowGroupsPage();
                        case "SavedMessages" -> MainController.showSavedMessages();
                        case "SavedTweets" -> {
                            if (!context.Users.get(MainController.currentUser.getId()).getSavedTweets().isEmpty())
                                MainController.loadTweets(context.Users.get(MainController.currentUser.getId()).getSavedTweets());
                            messagesPageController.setErrorLabel(messagesText.getNoSavedTweets(),true);
                        }
                    }
            }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}

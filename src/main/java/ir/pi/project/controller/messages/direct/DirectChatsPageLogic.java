package ir.pi.project.controller.messages.direct;

import ir.pi.project.config.addresses.InfoConfig;
import ir.pi.project.config.texts.messages.MessagesText;
import ir.pi.project.controller.MainLogic;
import ir.pi.project.db.ID;
import ir.pi.project.event.SearchUserEvent;
import ir.pi.project.listener.SearchUserListener;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.Message;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.message.direct.DirectChatsPageController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectChatsPageLogic extends MainLogic {
    DirectChatsPageController directChatsPageController;
    InfoConfig infoConfig=new InfoConfig();
    MessagesText messagesText=new MessagesText();
    public DirectChatsPageLogic(DirectChatsPageController directChatsPageController) throws IOException {
        this.directChatsPageController=directChatsPageController;
        directChatsPageController.setOpenChatListener(new SearchUserListener() {
            @Override
            public void eventOccurred(SearchUserEvent searchUserEvent) {
                directChatsPageController.setErrorLabel("",false);
                if(userExists(searchUserEvent.getUsername()))
                openChat(searchUserEvent.getUsername());
                else directChatsPageController.setErrorLabel(messagesText.getUserNotFound(),true);
            }
        });
        directChatsPageController.setReloadListener(new StringListener() {
            @Override
            public void stringEventOccurred(String string) {
                directChatsPageController.setErrorLabel("",false);

                if(string.equals("Reload"))
                    showChatBars();
            }
        });

        showChatBars();


    }

    private boolean userExists(String username){
        File usersDirectory = new File(infoConfig.getUsersDirectory());
        for (File file :
                usersDirectory.listFiles()) {
            User user1 = context.Users.get(ID.getIdFromFileName(file.getName()));
            if (user1.getUserName().equals(username) && user1.isActive()) {
            return true;
            }
        }
        return false;
    }


    private void showChatBars() {
        User user = context.Users.get(MainController.currentUser.getId());
        if (!user.getUnReadUsernames().isEmpty()) {
            for (HashMap.Entry<String, Integer> entry : user.getUnReadUsernames().entrySet()) {

                user.getUnRead().add(entry.getKey());
                context.Users.update(user);
            }
        }


        List<String> usernames=new ArrayList<>();
        if (!user.getChats().isEmpty()) {
            for (List<Integer> list :
                    user.getChats()) {
                if (!list.isEmpty()){
                    Message message = context.Messages.get(list.get(0));
                User user1 = context.Users.get(message.getSenderId());
                User user2 = context.Users.get(message.getReceiverId());
                if (user1.isActive() && user2.isActive()) {
                    if (user2.getId() == user.getId() && !isInList(context.Users.get(MainController.currentUser.getId()).getUnRead(), user1.getUserName()))
                        usernames.add(user1.getUserName());
                    if (user1.getId() == user.getId() && !isInList(context.Users.get(MainController.currentUser.getId()).getUnRead(), user2.getUserName()))
                        usernames.add(user2.getUserName());
                }
            }
            }
            directChatsPageController.update(context.Users.get(MainController.currentUser.getId()).getUnReadUsernames(),usernames);
        }


    }

    public void openChat(String username) {
        try {


            User user = context.Users.get(MainController.currentUser.getId());
            boolean usernameExists = false;

            for (List<Integer> list :
                    user.getChats()) {
                if (!list.isEmpty()) {
                    Message message = context.Messages.get(list.get(0));
                    User user1 = context.Users.get(message.getSenderId());
                    User user2 = context.Users.get(message.getReceiverId());
                    if (user1.getUserName().equals(username) && user1.getId() != user.getId() || user2.getUserName().equals(username) && user2.getId() != user.getId()) {
                        if (user1.isActive() && user2.isActive()) {
                            usernameExists = true;

                            int userId = 0;
                            int user1Id = 0;

                            if (message.getReceiverId() == user.getId()) {
                                userId = message.getReceiverId();
                                user1Id = message.getSenderId();
                                user = context.Users.get(user.getId());
                                context.Users.update(user);
                                User user3 = context.Users.get(message.getSenderId());
                                if (isInList(context.Users.get(userId).getUnRead(), user3.getUserName())) {
                                    removeFromUnread(userId, user3.getUserName());
                                }
                            } else if (message.getSenderId() == user.getId()) {
                                user1Id = message.getReceiverId();
                                userId = message.getSenderId();
                                user = context.Users.get(user.getId());
                                context.Users.update(user);
                                User user3 = context.Users.get(message.getReceiverId());
                                if (isInList(user.getUnRead(), user3.getUserName())) {
                                    removeFromUnread(userId, user3.getUserName());


                                }
                            }

                            MainController.showDirectChatPage(list,userId,user1Id);


                        }
                    }

                }
            }

            if (!usernameExists) {
                List<Integer> chat=new ArrayList<>();
                for (User user1:
                     context.Users.all()) {
                    if(user1.getUserName().equals(username))
                        MainController.showDirectChatPage(chat,user.getId(),user1.getId());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    private boolean isInList(List<String> list, String userName){
        for (int i=0;i<list.size();i++){
            if(list.get(i).equals(userName))return true;
        }
        return false;
    }

    private void removeFromUnread(Integer userId, String userName){
        User user=context.Users.get(userId);
        for (int i=0;i<user.getUnRead().size();i++){
            if(user.getUnRead().get(i).equals(userName)){
                user.getUnRead().remove(i);
                user.getUnReadUsernames().remove(userName);
                i--;
            }
        }
        context.Users.update(user);
    }




}

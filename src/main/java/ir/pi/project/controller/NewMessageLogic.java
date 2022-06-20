package ir.pi.project.controller;

import ir.pi.project.db.ID;
import ir.pi.project.model.Message;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NewMessageLogic extends MainLogic {
    static private final Logger logger= LogManager.getLogger(NewMessageLogic.class);

    public void newMessage(int userId, int user1Id, String ans, Image image,boolean isForwarded){
        User user = context.Users.get(userId);
        User user1 =context.Users.get(user1Id);
        boolean b = false;
        for (Map.Entry<String, Integer> entry : user1.getUnReadUsernames().entrySet()) {
            if (entry.getKey().equals(user.getUserName())) b = true;
        }
        if (!b) user1.getUnReadUsernames().put(user.getUserName(), 0);


        int id=0;
        if(image!=null) {
            context.Images.update(image);
            id= ID.lastUsedId();
        }
        Message message = new Message(userId, user1Id, ans);
        message.setForwarded(isForwarded);
        message.setImage(id);
        context.Messages.update(message);

        logger.info("new message with id: "+message.getId()+" was made");

        boolean chatExists = false;
        for (List<Integer> chat :
                user.getChats()) {
            Message message1 =context.Messages.get(chat.get(0));
            if ((message1.getReceiverId() == user1Id && message1.getSenderId() == userId) || (message1.getSenderId() == user1Id && message1.getReceiverId() == userId)) {
                chatExists = true;
            }
        }
        if (chatExists) {
            for (List<Integer> chat :
                    user.getChats()) {
                Message message1 = context.Messages.get(chat.get(0));
                if ((message1.getReceiverId() == user1Id && message1.getSenderId() == userId) || (message1.getSenderId() == user1Id && message1.getReceiverId() == userId)) {
                    chat.add(message.getId());
                    user1.getUnReadUsernames().put(user.getUserName(), user1.getUnReadUsernames().get(user.getUserName()) + 1);
                }
            }

            for (List<Integer> chat :
                    user1.getChats()) {
                Message message1 = context.Messages.get(chat.get(0));
                if ((message1.getSenderId() == user1Id && message1.getReceiverId() == userId) || (message1.getReceiverId() == user1Id && message1.getSenderId() == userId)) {
                    chat.add(message.getId());

                }
            }

        } else {
            List<Integer> newChat = new ArrayList<>();
            newChat.add(message.getId());
            user.getChats().add(newChat);
            user1.getChats().add(newChat);
            user1.getUnReadUsernames().put(user.getUserName(), user1.getUnReadUsernames().get(user.getUserName()) + 1);
        }

        context.Messages.update(message);
        context.Users.update(user);
        context.Users.update(user1);

            logger.info(user.getUserName()+" sent a message to "+ user1.getUserName());


    }
}

package ir.pi.project.controller.messages.direct;

import ir.pi.project.config.texts.messages.MessagesText;
import ir.pi.project.controller.MainLogic;
import ir.pi.project.controller.NewMessageLogic;
import ir.pi.project.controller.UserLogic;
import ir.pi.project.event.NewTextEvent;
import ir.pi.project.listener.NewTextListener;
import ir.pi.project.listener.StringListener;
import ir.pi.project.listener.MessageListener;
import ir.pi.project.model.Message;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.message.ShowChatPageController;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowDirectChatLogic extends MainLogic {
    static private final Logger logger = LogManager.getLogger(ShowDirectChatLogic.class);
    private final MessagesText messagesText=new MessagesText();
        Image image;
        int messageToBeEdited;

    ShowChatPageController showChatPageController;
    NewMessageLogic newMessageLogic;
    UserLogic userLogic;
    public ShowDirectChatLogic(ShowChatPageController showChatPageController, List<Integer> chat,int userId,int user1Id) throws IOException {
        this.showChatPageController=showChatPageController;
        this.newMessageLogic=new NewMessageLogic();
        this.userLogic=new UserLogic();

        showChatPageController.setName(context.Users.get(user1Id).getUserName());
        showChat(userId,user1Id);
        showChatPageController.setNewMessageListener(new NewTextListener() {
            @Override
            public void eventOccurred(NewTextEvent newTweetEvent) {
                if(userLogic.isFollowing(user1Id,userId)||userLogic.isFollowing(userId,user1Id)) {
                    newMessageLogic.newMessage(userId, user1Id, newTweetEvent.getText(), image, false);
                    showChatPageController.clearTextField();
                    showChat(userId, user1Id);
                    showChatPageController.setImage(null);
                    image = null;
                }
                else showChatPageController.setErrorLabel(messagesText.getFf(),true);
            }
        });

        showChatPageController.setEditMessageListener(new NewTextListener() {
            @Override
            public void eventOccurred(NewTextEvent newTextEvent) {
                editTheText(newTextEvent.getText());

            }
        });

        showChatPageController.setMessageButtonListener(new MessageListener() {
            @Override
            public void eventOccurred(int id, String buttonName) {
                switch (buttonName) {
                    case "Edit" -> edit(id);
                    case "Delete" -> delete(id);
                    case "Save" -> save(id);
                }
            }
        });

        showChatPageController.setChatListener(new StringListener() {
            @Override
            public void stringEventOccurred(String string) {
                if(string.equals("UploadImage")){
                    uploadImage();
                }
            }
        });

    }

    private void save(int messageId){
        User user=context.Users.get(MainController.currentUser.getId());
        user.getSavedMessages().add(messageId);
        context.Users.update(user);
        logger.info("user saved a message with id: "+messageId);
    }

    private void edit(int messageId){
        Message message=context.Messages.get(messageId);
        if(!message.isForwarded()) {
            showChatPageController.setMessageFieldText(message.getText());
            showChatPageController.setEditButton(true);
            messageToBeEdited = messageId;
            showChatPageController.setSendButton(false);
            showChatPageController.setImageButton(false);
        }else {
            showChatPageController.setErrorLabel(messagesText.getCantEditForwarded(),true);
        }

    }

    private void editTheText(String text){
        if(messageToBeEdited!=-1) {
            Message message = context.Messages.get(messageToBeEdited);
            message.setText(text);
            context.Messages.update(message);
            messageToBeEdited = -1;
            showChatPageController.clearTextField();
            showChatPageController.setEditButton(false);
            showChatPageController.setSendButton(true);
            showChatPageController.setImageButton(true);
            logger.info("user edited message with id: "+message.getId() );
            showChat(message.getSenderId(),message.getReceiverId());
        }
    }

    private void delete(int messageId){
        Message message=context.Messages.get(messageId);
        message.setText(messagesText.getDeletedMessage());
        message.setImage(0);
        context.Messages.update(message);
        logger.info("user deleted message with id: "+message.getId());
        showChat(message.getSenderId(),message.getReceiverId());
    }

    private void uploadImage(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterPNG);
        File file = fileChooser.showOpenDialog(null);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
            image = SwingFXUtils.toFXImage(bufferedImage, null);
            showChatPageController.setImage(image);
            logger.info("user uploaded an image for a new message");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showChat(int userId,int user1Id){
        showChatPageController.empty();
        List<Integer> chat=new ArrayList<>();

        User user=context.Users.get(MainController.currentUser.getId());
        for (List<Integer> list:
             user.getChats()) {
            if(!list.isEmpty()) {
                Message message = context.Messages.get(list.get(0));
                if ((message.getSenderId() == userId && message.getReceiverId() == user1Id) || (message.getReceiverId() == userId && message.getSenderId() == user1Id)) {
                    chat = list;
                }
            }
        }
        if(!chat.isEmpty()) {
            for (Integer integer : chat) {
                Message message = context.Messages.get(integer);
                String time = message.getTime().getYear() + " " + message.getTime().getMonth() + " " + message.getTime().getDayOfMonth() +
                        "  " + message.getTime().getHour() + ":" + message.getTime().getMinute();
                if (message.getSenderId() == user.getId()) {
                    if (message.getImage() != 0)
                        showChatPageController.showMyMessage(message.getText(), time, context.Images.get(message.getImage()), message.getId());
                    else showChatPageController.showMyMessage(message.getText(), time, null, message.getId());

                } else if (message.getReceiverId() == user.getId()) {
                    User sender=context.Users.get(message.getSenderId());
                    Image messageImage;
                    if(message.getImage()!=0)messageImage=context.Images.get(message.getImage());
                    else messageImage=null;

                    Image profileImage;
                    if(sender.getImage()!=null && sender.getImage()!=0)
                        profileImage=context.Images.get(sender.getImage());
                    else profileImage=null;

                    showChatPageController.showOthersMessage(message.getText(),time,sender.getUserName(),messageImage,profileImage,message.getId());
                }
            }
        }

    }

}

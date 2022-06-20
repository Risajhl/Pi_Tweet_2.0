package ir.pi.project.controller.messages.groupChat;

import ir.pi.project.controller.MainLogic;
import ir.pi.project.controller.messages.direct.ShowDirectChatLogic;
import ir.pi.project.db.ID;
import ir.pi.project.event.NewTextEvent;
import ir.pi.project.listener.MessageListener;
import ir.pi.project.listener.NewTextListener;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.GroupChat;
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
import java.util.List;

public class ShowGroupChatLogic extends MainLogic {
    static private final Logger logger = LogManager.getLogger(ShowGroupChatLogic.class);

    ShowChatPageController showChatPageController;
    Image image;
    int messageToBeEdited;
    int groupChatId;
    public ShowGroupChatLogic(ShowChatPageController showChatPageController,Integer groupChatId){
        this.showChatPageController=showChatPageController;
        this.groupChatId=groupChatId;
        GroupChat groupChat=context.GroupChats.get(groupChatId);
        showChatPageController.setName(groupChat.getGroupName());
        showChatPageController.setNewMessageListener(new NewTextListener() {
            @Override
            public void eventOccurred(NewTextEvent newTweetEvent) {
                newMessage(MainController.currentUser.getId(),groupChatId,newTweetEvent.getText());
                showChat(groupChatId);
                showChatPageController.setImage(null);
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

        showChat(groupChatId);
    }
    private void save(int messageId){
        User user=context.Users.get(MainController.currentUser.getId());
        user.getSavedMessages().add(messageId);
        context.Users.update(user);
        logger.info("user saved a message with id: "+messageId);

    }

    private void edit(int messageId){
        Message message=context.Messages.get(messageId);
        showChatPageController.setMessageFieldText(message.getText());
        showChatPageController.setEditButton(true);
        messageToBeEdited=messageId;
        showChatPageController.setSendButton(false);
        showChatPageController.setImageButton(false);

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
            showChat(groupChatId);
        }
    }

    private void delete(int messageId){
        GroupChat groupChat=context.GroupChats.get(groupChatId);
            for(int i=0;i<groupChat.getMessages().size();i++){
                if(groupChat.getMessages().get(i)==messageId){
                    groupChat.getMessages().remove(i);
                    i--;
                }
        }
        context.GroupChats.update(groupChat);
        logger.info("user deleted message with id: "+messageId);

        showChat(groupChatId);
    }

    private void showChat(Integer groupChatId){
        showChatPageController.empty();
        GroupChat groupChat=context.GroupChats.get(groupChatId);
        User user=context.Users.get(MainController.currentUser.getId());
        context.Users.update(user);
        List<Integer> messages=groupChat.getMessages();
        if(!messages.isEmpty()){
            for (Integer messageId:
                 messages) {
                Message message=context.Messages.get(messageId);
                String time = message.getTime().getYear() + " " + message.getTime().getMonth() + " " + message.getTime().getDayOfMonth() +
                        "  " + message.getTime().getHour() + ":" + message.getTime().getMinute();
                if(message.getSenderId()==user.getId()){
                    if(message.getImage()!=0)
                        showChatPageController.showMyMessage(message.getText(), time, context.Images.get(message.getImage()), message.getId());
                    else   showChatPageController.showMyMessage(message.getText(), time, null, message.getId());
                }
                else  {
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

    private void newMessage(int senderId,int groupId, String ans){
        GroupChat groupChat=context.GroupChats.get(groupId);
        for (Integer memberId:
             groupChat.getMembers()) {
            User member=context.Users.get(memberId);
            boolean b = false;
            if (memberId!=MainController.currentUser.getId()) member.getUnreadGroupChats().put(groupId, 0);
            context.Users.update(member);

        }

        int id=0;
        if(image!=null) {
            context.Images.update(image);
            id= ID.lastUsedId();
        }
        Message message = new Message(senderId, groupId, ans);
        message.setImage(id);
        context.Messages.update(message);
        image=null;

        groupChat.getMessages().add(message.getId());
        for (Integer memberId:
                groupChat.getMembers()) {
            if(!memberId.equals(MainController.currentUser.getId())) {
                User member = context.Users.get(memberId);

                member.getUnreadGroupChats().put(groupId, member.getUnreadGroupChats().get(groupId) + 1);
                context.Users.update(member);

            }
        }
        context.Messages.update(message);
        context.GroupChats.update(groupChat);
        context.Users.update(context.Users.get(senderId));
        logger.info("user made a new message with id: "+message.getId()+" in the group chat with id: "+groupChat.getId());
        showChatPageController.clearTextField();

    }


}

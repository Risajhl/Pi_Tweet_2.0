package ir.pi.project.controller.messages;

import ir.pi.project.controller.MainLogic;
import ir.pi.project.controller.NewMessageLogic;
import ir.pi.project.controller.messages.multipleMessaging.MultipleToUsersLogic;
import ir.pi.project.db.ID;
import ir.pi.project.event.NewTextEvent;
import ir.pi.project.listener.NewTextListener;
import ir.pi.project.listener.StringListener;
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

public class SavedMessagesLogic extends MainLogic {
    static private final Logger logger = LogManager.getLogger(SavedMessagesLogic.class);
    Image image;
    NewMessageLogic newMessageLogic=new NewMessageLogic();
    ShowChatPageController showChatPageController;
    public SavedMessagesLogic( ShowChatPageController showChatPageController){
        this.showChatPageController=showChatPageController;
        showChatPageController.setName("Saved Messages");

        showChatPageController.setNewMessageListener(new NewTextListener() {
            @Override
            public void eventOccurred(NewTextEvent newTextEvent) {
                User user=context.Users.get(MainController.currentUser.getId());
                newMessageLogic.newMessage(user.getId(), user.getId(), newTextEvent.getText(), image, false);
                user.getSavedMessages().add(ID.lastUsedId());
                context.Users.update(user);
                showChatPageController.clearTextField();
                showChatPageController.setImage(null);
                image = null;
                showChat();
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
        showChat();
    }

    private void showChat(){
        showChatPageController.empty();
        User user=context.Users.get(MainController.currentUser.getId());
        List<Integer> messages=user.getSavedMessages();
        if(!messages.isEmpty()){
            for (Integer messageId:
                    messages) {
                Message message=context.Messages.get(messageId);
                String time = message.getTime().getYear() + " " + message.getTime().getMonth() + " " + message.getTime().getDayOfMonth() +
                        "  " + message.getTime().getHour() + ":" + message.getTime().getMinute();
                if(message.getSenderId()==user.getId()){

                    if(message.getImage()!=0)
                    showChatPageController.showMyMessage(message.getText(),time,context.Images.get(message.getImage()),message.getId());
                    else   showChatPageController.showMyMessage(message.getText(),time,null,message.getId());

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

    private void newMessage(String text){
        User user=context.Users.get(MainController.currentUser.getId());

        Message message=new Message(user.getId(),user.getId(),text);
        user.getSavedMessages().add(message.getId());
        context.Messages.update(message);
        context.Users.update(user);


        showChatPageController.clearTextField();
        logger.info("user added a message with id: "+message.getId()+" to savedMessages");

    }

}

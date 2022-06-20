package ir.pi.project.controller.messages.multipleMessaging;

import ir.pi.project.config.addresses.InfoConfig;
import ir.pi.project.config.texts.messages.MessagesText;
import ir.pi.project.controller.MainLogic;
import ir.pi.project.controller.NewMessageLogic;
import ir.pi.project.controller.UserLogic;
import ir.pi.project.db.ID;
import ir.pi.project.event.NewTextEvent;
import ir.pi.project.listener.NewTextListener;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.message.multipleMessaging.MultipleToUsersController;
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

public class MultipleToUsersLogic extends MainLogic {
    static private final Logger logger = LogManager.getLogger(MultipleToUsersLogic.class);
    private final InfoConfig infoConfig=new InfoConfig();
    private final MessagesText messagesText=new MessagesText();

    Image image;
    MultipleToUsersController multipleToUsersController;
    List<String> usernames;
    UserLogic userLogic;
    NewMessageLogic newMessageLogic;
    public MultipleToUsersLogic(MultipleToUsersController multipleToUsersController) throws IOException {
        this.multipleToUsersController=multipleToUsersController;
        this.newMessageLogic=new NewMessageLogic();
        this.usernames=new ArrayList<>();
        this.userLogic=new UserLogic();

        multipleToUsersController.setAddUsernameListener(new NewTextListener() {

            @Override
            public void eventOccurred(NewTextEvent newTextEvent) {
                multipleToUsersController.setError("",false);
                check(newTextEvent.getText());

            }
        });

        multipleToUsersController.setNewMessageListener(new NewTextListener() {

            @Override
            public void eventOccurred(NewTextEvent newTextEvent) {
                multipleToUsersController.setError("",false);
                multipleToUsersController.showSent(false);
                String messageText=newTextEvent.getText();
                    send(messageText);
                    multipleToUsersController.setImage(null);
                    image=null;
            }
        });

        multipleToUsersController.setSendMessageListener(new StringListener() {
            @Override
            public void stringEventOccurred(String string) {
                if(string.equals("UploadImage")){
                    uploadImage();
                }
            }
        });
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
            multipleToUsersController.setImage(image);
            logger.info("user uploaded an image for a new message");

        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    multipleToUsersController.setError(messagesText.getItsU(),true);
                 else {
                    if(userLogic.isFollowing(user.getId(),user1.getId())||userLogic.isFollowing(user1.getId(),user.getId())) {

                        if(!usernames.contains(username)){
                            usernames.add(username);
                        multipleToUsersController.addToUsername(username);
                        }
                        else
                            multipleToUsersController.setError(messagesText.getChosenUser(),true);

                    }
                    else
                        multipleToUsersController.setError(messagesText.getFf(),true);

                }
            }
        }
        if (!exists) {
            multipleToUsersController.setError(messagesText.getUserNotFound(),true);
        }
    }


    private void send(String text){
        try {
            User user=context.Users.get(MainController.currentUser.getId());
            if(usernames.isEmpty())
                multipleToUsersController.setError(messagesText.getAtLeastOne(),true);
            else {
                for (String username :
                        usernames) {
                    File usersDirectory = new File(infoConfig.getUsersDirectory());
                    for (File file :
                            usersDirectory.listFiles()) {
                        User user1 = context.Users.get(ID.getIdFromFileName(file.getName()));

                        if (user1.getUserName().equals(username) && user1.isActive()) {
                            newMessageLogic.newMessage(user.getId(), user1.getId(), text,image,false);
                        }
                    }
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }



}

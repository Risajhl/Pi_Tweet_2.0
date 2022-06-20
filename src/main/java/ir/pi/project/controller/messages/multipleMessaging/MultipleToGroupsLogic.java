package ir.pi.project.controller.messages.multipleMessaging;

import ir.pi.project.config.addresses.InfoConfig;
import ir.pi.project.config.texts.messages.MessagesText;
import ir.pi.project.controller.MainLogic;
import ir.pi.project.controller.NewMessageLogic;
import ir.pi.project.controller.UserLogic;
import ir.pi.project.controller.messages.groupChat.ShowGroupChatLogic;
import ir.pi.project.db.ID;
import ir.pi.project.event.NewTextEvent;
import ir.pi.project.listener.NewTextListener;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.Group;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.message.multipleMessaging.MultipleToGroupsController;
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

public class MultipleToGroupsLogic extends MainLogic {
    static private final Logger logger = LogManager.getLogger(MultipleToGroupsLogic.class);
    private final InfoConfig infoConfig=new InfoConfig();
    private final MessagesText messagesText=new MessagesText();
    Image image;
    MultipleToGroupsController multipleToGroupsController;
    List<String> groupNames;
    List<Integer> receivers;
    UserLogic userLogic;
    NewMessageLogic newMessageLogic;
    public MultipleToGroupsLogic( MultipleToGroupsController multipleToGroupsController) throws IOException {
        this.multipleToGroupsController=multipleToGroupsController;
        this.userLogic=new UserLogic();
        this.newMessageLogic=new NewMessageLogic();
        this.groupNames=new ArrayList<>();
        this.receivers=new ArrayList<>();
        multipleToGroupsController.update(groupNames);

        multipleToGroupsController.setAddUsernameListener(new NewTextListener() {
            @Override
            public void eventOccurred(NewTextEvent newTextEvent) {
                multipleToGroupsController.setError("",false);
                multipleToGroupsController.showSent(false);

                check(newTextEvent.getText());
                multipleToGroupsController.update(groupNames);
            }
        });
        multipleToGroupsController.setNewMessageListener(new NewTextListener() {
            @Override
            public void eventOccurred(NewTextEvent newTextEvent) {
                multipleToGroupsController.setError("",false);
                multipleToGroupsController.showSent(false);

                send(newTextEvent.getText());
                multipleToGroupsController.update(groupNames);
                multipleToGroupsController.setImage(null);
                image=null;
            }
        });

        multipleToGroupsController.setSendMessageListener(new StringListener() {
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
            multipleToGroupsController.setImage(image);
            logger.info("user uploaded an image for a new message");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void check(String groupName){
        User user=context.Users.get(MainController.currentUser.getId());
        File groupsDirectory = new File(infoConfig.getGroupsDirectory());
        boolean exists = false;
        for (File file :
                groupsDirectory.listFiles()) {
            Group group = context.Groups.get(ID.getIdFromFileName(file.getName()));

            if (group.getName().equals(groupName)) {
                exists=true;
                if (!group.getMembers().isEmpty()) {
                    for (int i = 0; i < group.getMembers().size(); i++) {
                        User member = context.Users.get(group.getMembers().get(i));
                        if (member.isActive()) {
                            receivers.add(group.getMembers().get(i));

                        }
                    }
                    groupNames.add(group.getName());
                } else {
                    multipleToGroupsController.setError(messagesText.getEmptyGroup(), true);
                }
            }
        }
        if (!exists) {
            multipleToGroupsController.setError(messagesText.getGroupNotFound(),true);
        }
        System.out.println(receivers);
    }

    private void send(String text) {
        if (!receivers.isEmpty()) {
            for (Integer receiverId :
                    receivers) {
                newMessageLogic.newMessage(MainController.currentUser.getId(), receiverId, text,image,false);
            }
            multipleToGroupsController.showSent(true);
        }
    }


}

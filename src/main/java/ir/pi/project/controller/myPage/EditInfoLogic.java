package ir.pi.project.controller.myPage;

import ir.pi.project.config.texts.myPage.EditInfoTexts;
import ir.pi.project.controller.MainLogic;
import ir.pi.project.controller.setting.DeleteAccountPageLogic;
import ir.pi.project.db.ID;
import ir.pi.project.event.EditInfoFormEvent;
import ir.pi.project.listener.EditInfoFormListener;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.User;
import ir.pi.project.view.myPage.EditInfoPageController;
import ir.pi.project.view.MainController;
import ir.pi.project.view.myPage.MyPagePageController;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EditInfoLogic extends MainLogic {
    static private final Logger logger = LogManager.getLogger(EditInfoLogic.class);
    EditInfoTexts editInfoTexts=new EditInfoTexts();

    Image profileImage;
    EditInfoPageController editInfoPageController;

    public EditInfoLogic(EditInfoPageController editInfoPageController,MyPagePageController myPagePageController) throws IOException {
        this.editInfoPageController=editInfoPageController;
        editInfoPageController.update(context.Users.get(MainController.currentUser.getId()));
        if(context.Users.get(MainController.currentUser.getId()).getImage()!=null)
        editInfoPageController.setImage(context.Images.get(context.Users.get(MainController.currentUser.getId()).getImage()));

        editInfoPageController.setEditInfoFormListener(new EditInfoFormListener() {
            @Override
            public void eventOccurred(EditInfoFormEvent editInfoFormEvent) {


                editInfoPageController.setInfoLabel("",false);
                if(!isUserNameAvailable(editInfoFormEvent.getUserName())){
                    editInfoPageController.setEditInfoError(editInfoTexts.getTakenUsername());

                }
                else if(!isPhoneNumberAvailable(editInfoFormEvent.getPhoneNumber())){
                    editInfoPageController.setEditInfoError(editInfoTexts.getTakenPhoneNumber());
                }
                else if(!isEmailAvailable(editInfoFormEvent.getEmail())){
                    editInfoPageController.setEditInfoError(editInfoTexts.getTakenEmail());
                }
                else {
                    setChanges(editInfoFormEvent,editInfoPageController.isEPBCanSee());
                    logger.info("user with id: "+MainController.currentUser.getId()+"'s info updated");
                    editInfoPageController.setInfoLabel(editInfoTexts.getSaved(),true);
                }
                myPagePageController.update(context.Users.get(MainController.currentUser.getId()));
                if (context.Users.get(MainController.currentUser.getId()).getImage()!=null)
                myPagePageController.setProfileImage(context.Images.get(context.Users.get(MainController.currentUser.getId()).getImage()));
                else myPagePageController.setProfileImage(null);
            }
        });


        editInfoPageController.setProfileImageListener(new StringListener() {
            @Override
            public void stringEventOccurred(String string) {
                if(string.equals("ChangeImage")){
                    changeImage();
                    logger.info("user set a new profileImage");

                }
                if (string.equals("RemoveImage")){
                    editInfoPageController.setImage(null);
                    User user=  context.Users.get(MainController.currentUser.getId());
                    user.setImage(null);
                    context.Users.update(user);
                    logger.info("user removed their profileImage");
                }
            }
        });
    }


    public void changeImage(){

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterPNG);
        File file = fileChooser.showOpenDialog(null);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
            profileImage = SwingFXUtils.toFXImage(bufferedImage, null);
            editInfoPageController.setImage(profileImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void setChanges(EditInfoFormEvent e,boolean isEPBCanSee){
        User user=context.Users.get(MainController.currentUser.getId());
        user.setFirstName(e.getFirstName());
        user.setLastName(e.getLastName());
        user.setUserName(e.getUserName());
        user.setPhoneNumber(e.getPhoneNumber());
        user.setEmail(e.getEmail());
        user.setBirthDate(e.getBirthDate());
        user.setBiography(e.getBiography());
        user.setEPBCanSee(isEPBCanSee);

        if(profileImage!=null) {
            context.Images.update(profileImage);
            user.setImage(ID.lastUsedId());
        }



        context.Users.update(user);
    }

    public boolean isUserNameAvailable(String username){
        User thisUser= MainController.currentUser;
        for (User user:
                context.Users.all()) {
            if(user.getUserName().equals(username) && user.getId()!=thisUser.getId())return false;
        }
        return true;
    }

    public boolean isEmailAvailable(String email){
        User thisUser= MainController.currentUser;

        for (User user:
                context.Users.all()) {
            if(user.getEmail().equals(email)&& user.getId()!=thisUser.getId())return false;
        }
        return true;
    }

    public boolean isPhoneNumberAvailable(String phoneNumber){
        User thisUser= MainController.currentUser;

        for (User user:
                context.Users.all()) {
            if(user.getUserName().equals(phoneNumber)&& user.getId()!=thisUser.getId())return false;
        }
        return true;
    }


}

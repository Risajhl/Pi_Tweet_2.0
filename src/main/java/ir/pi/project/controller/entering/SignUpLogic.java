package ir.pi.project.controller.entering;

import ir.pi.project.config.texts.entering.SignUpTexts;
import ir.pi.project.controller.MainLogic;
import ir.pi.project.event.SignUpFormEvent;
import ir.pi.project.listener.SignUpFormListener;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.entering.SignUpPageController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;

public class SignUpLogic extends MainLogic {
    static private final Logger logger= LogManager.getLogger(SignUpLogic.class);
    private final SignUpTexts signUpError=new SignUpTexts();

    SignUpPageController signUpPageController;
    public SignUpLogic(SignUpPageController signUpPageController) throws IOException {
        this.signUpPageController=signUpPageController;
        signUpPageController.setSignUpFormListener(new SignUpFormListener() {
            @Override
            public void eventOccurred(SignUpFormEvent formEvent) {
                if(!isUserNameAvailable(formEvent.getUserName())){
                    signUpPageController.setSignUpError(signUpError.getTakenUsername());
                }
                else if(!isPhoneNumberAvailable(formEvent.getPhoneNumber())){
                    signUpPageController.setSignUpError(signUpError.getTakenPhoneNumber());
                }
                else if(!isEmailAvailable(formEvent.getEmail())){
                    signUpPageController.setSignUpError(signUpError.getTakenEmail());
                }
                else {
                    signUp(formEvent,signUpPageController.isEPBCanSee());
                    try {
                        logger.info("user "+signUpPageController.getUserNameField()+" signed up");
                        MainController.loadWelcome();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void signUp(SignUpFormEvent e, boolean canSee){

        User user=new User(e.getFirstName(),e.getLastName(),e.getUserName(),e.getPassword(),e.getEmail());
        user.setBirthDate(e.getBirthDate());
        user.setPhoneNumber(e.getPhoneNumber());
        String date= LocalDateTime.now().getYear()+" "+LocalDateTime.now().getMonth()+" "+LocalDateTime.now().getDayOfMonth()+
                " - "+LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute();
        user.setLastSeen("LastSeen: "+date);
        user.setOnline(false);
        user.setEPBCanSee(canSee);
        context.Users.update(user);

    }

    private boolean isUserNameAvailable(String username){
        for (User user:
                context.Users.all()) {
            if(user.getUserName().equals(username))return false;
        }
        return true;
    }

    private boolean isEmailAvailable(String email){
        for (User user:
                context.Users.all()) {
            if(user.getEmail().equals(email))return false;
        }
        return true;
    }

    private boolean isPhoneNumberAvailable(String phoneNumber){
        for (User user:
                context.Users.all()) {
            if(user.getUserName().equals(phoneNumber))return false;
        }
        return true;
    }


}

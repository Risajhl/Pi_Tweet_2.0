package ir.pi.project.controller.entering;

import ir.pi.project.config.texts.entering.LogInTexts;
import ir.pi.project.controller.MainLogic;
import ir.pi.project.event.LogInFormEvent;
import ir.pi.project.listener.LogInFormListener;
import ir.pi.project.model.User;
import ir.pi.project.view.entering.LogInPageController;
import ir.pi.project.view.MainController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class LogInLogic extends MainLogic {
    static private final Logger logger= LogManager.getLogger(LogInLogic.class);
    private LogInTexts logInError=new LogInTexts();
    LogInPageController logInPageController;
    public LogInLogic(LogInPageController logInPageController) throws IOException {
        this.logInPageController=logInPageController;
        logInPageController.setLogInFormListener(new LogInFormListener() {
            @Override
            public void eventOccurred(LogInFormEvent logInFormEvent) {
            if(!canBeFound(logInFormEvent.getUserName(),logInFormEvent.getPassword())){
                logInPageController.setError(logInError.getNotFound(),true);
            logInPageController.getLogInError().setVisible(true);
            }else {
                try {
                    logger.info("user "+logInFormEvent.getUserName()+" logged in");
                    MainController.loadMainMenu();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        });


    }


    private boolean canBeFound(String username,String password){
        for (User user:
                context.Users.all()) {
            if(user.getUserName().equals(username) && user.getPassword().equals(password)){

                user.setLastSeen("Online");
                user.setOnline(true);

                context.Users.update(user);
                MainController.currentUser=user;
                return true;
            }
        }
        return false;
    }


}

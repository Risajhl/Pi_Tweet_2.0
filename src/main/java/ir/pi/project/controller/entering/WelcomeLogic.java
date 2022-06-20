package ir.pi.project.controller.entering;

import ir.pi.project.view.MainController;
import ir.pi.project.listener.StringListener;
import ir.pi.project.view.entering.WelcomePageController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WelcomeLogic {

    WelcomePageController welcomePageController;

    public WelcomeLogic(WelcomePageController welcomePageController) {
        this.welcomePageController = welcomePageController;
        welcomePageController.addListener(new StringListener() {
            @Override
            public void stringEventOccurred(String string) {
                try {
                    if (string.equals("SignUp"))
                        MainController.loadSignUpPage();

                    if (string.equals("LogIn"))
                        MainController.loadLogInPage();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

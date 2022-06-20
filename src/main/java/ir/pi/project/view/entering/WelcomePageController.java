package ir.pi.project.view.entering;

import ir.pi.project.listener.StringListener;
//import ir.pi.project.listener.WelcomeToLoginListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class WelcomePageController implements Initializable {

    private LinkedList<StringListener> stringListeners=new LinkedList<>();

    @FXML
    Button signUpButton;
    @FXML
    Button logInButton;

    public void addListener(StringListener stringListener) {
        stringListeners.add(stringListener);
    }

//    public static void loadWelcome(Stage primaryStage) throws IOException {
//        Parent root= FXMLLoader.load(WelcomePageController.class.getResource("/FXML/welcomePage.fxml"));
//        Scene scene=new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("PiTweet");
//        primaryStage.setResizable(false);
//        primaryStage.show();
//    }


    public void SignUp(){
        for (StringListener stringListener:
             stringListeners) {
            stringListener.stringEventOccurred("SignUp");
        }
        
    }



    public void logIn(){
        for (StringListener stringListener:
                stringListeners) {
            stringListener.stringEventOccurred("LogIn");
        }
        
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }



}

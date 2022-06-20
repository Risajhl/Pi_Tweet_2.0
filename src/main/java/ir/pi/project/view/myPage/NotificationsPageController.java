package ir.pi.project.view.myPage;

import ir.pi.project.view.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NotificationsPageController implements Initializable {

    @FXML
    TextArea notificationsArea;


    public void update(List<String> notifications){
        String s="";
        for (int i=notifications.size()-1;i>=0;i--){
            s=s+""+notifications.get(i)+"\n";
        }
        notificationsArea.setText(s);
    }

    public void back(){
        MainController.back();
    }
    public void mainMenu(){
        try {
            MainController.loadMainMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

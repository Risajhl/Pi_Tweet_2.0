package ir.pi.project.view.myPage;

import ir.pi.project.view.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PendingsController implements Initializable {

    @FXML
    TextArea pendingArea;


    public void update(List<String> myRequests){
        String s="";
        for (int i=myRequests.size()-1;i>=0;i--){
            s=s+""+myRequests.get(i)+"\n";
        }
        pendingArea.setText(s);
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

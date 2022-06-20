package ir.pi.project.view.explorer;

import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowProfileImageController implements Initializable {
    @FXML
    Rectangle imageRect;

    public void show(Image image){
        if(image!=null)
            imageRect.setFill(new ImagePattern(image));

    }

    public void back(){ MainController.back(); }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

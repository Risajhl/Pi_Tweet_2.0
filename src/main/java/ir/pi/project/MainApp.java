package ir.pi.project;

import ir.pi.project.config.addresses.FXMLConfig;
import ir.pi.project.config.texts.explorer.ShowProfileTexts;
import ir.pi.project.controller.entering.WelcomeLogic;
import ir.pi.project.view.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

public class MainApp extends Application {
    static private final Logger logger= LogManager.getLogger(MainApp.class);
    private FXMLConfig fxmlConfig;

    {
        try {
            fxmlConfig = new FXMLConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {

        logger.info("Program started");

        MainController.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlConfig.getWelcomePage()));
        Parent root = fxmlLoader.load();
        new WelcomeLogic(fxmlLoader.getController());
        Scene scene = new Scene(root);
        MainController.addScene(scene);
        stage.setScene(scene);
        stage.setTitle("PiTweet");
        stage.setResizable(false);
        stage.show();

    }
}


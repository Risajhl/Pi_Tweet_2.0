package ir.pi.project.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.pi.project.config.addresses.InfoConfig;
import ir.pi.project.model.User;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;


public class ImageDB implements DBSet<Image>{
    static private final Logger logger= LogManager.getLogger(ImageDB.class);
    private InfoConfig infoConfig;
    {
        try {
            infoConfig = new InfoConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Image get(int id) {

        File directory = new File(infoConfig.getImagesDirectory());
        File Data = new File(directory, id + ".png");
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(Data);
            logger.info("image with id "+id+" was loaded in image get");
            return SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public LinkedList<Image> all() {
        return null;
    }


    @Override
    public void update(Image image) {
        File data = null;
        int id=0;
        try {
             id=ID.newID();
            File directory = new File(infoConfig.getImagesDirectory());

            data = new File(directory, id + ".png");
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", data);

            logger.info("image with id "+id+ " saved");

        } catch (Exception e) {
            logger.warn("image with "+id+" could not be saved in image update");
            e.printStackTrace();
        }
    }
}

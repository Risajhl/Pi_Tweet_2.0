package ir.pi.project.controller.myPage;

import ir.pi.project.config.texts.myPage.MyPageTexts;
import ir.pi.project.controller.MainLogic;
import ir.pi.project.db.ID;
import ir.pi.project.event.NewTextEvent;
import ir.pi.project.listener.NewTextListener;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.Tweet;
import ir.pi.project.model.User;
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
import java.util.ArrayList;
import java.util.List;

public class MyPagePageLogic extends MainLogic {
   static private final Logger logger = LogManager.getLogger(MyPagePageLogic.class);
   private final MyPageTexts myPageTexts=new MyPageTexts();

    Image tweetImage;
    MyPagePageController myPagePageController;

    public MyPagePageLogic(MyPagePageController myPagePageController) throws IOException {
        this.myPagePageController=myPagePageController;

        if(context.Users.get(MainController.currentUser.getId()).getImage()!=null)
        myPagePageController.setProfileImage(context.Images.get(context.Users.get(MainController.currentUser.getId()).getImage()));
        myPagePageController.setMyPageListener(new StringListener() {
            @Override
            public void stringEventOccurred(String string) {
                try {
                    myPagePageController.setErrorLabel("",false);
                    switch (string) {
                        case "Tweets" -> {
                            User user = context.Users.get(MainController.currentUser.getId());
                            if (!user.getTweets().isEmpty()) {
                                System.out.println(user.getTweets());
                                MainController.loadTweets(tweets(user.getTweets()));
                            } else {
                                myPagePageController.setErrorLabel(myPageTexts.getNoTweets(),true);
                            }
                        }
                        case "EditInfo" -> MainController.loadEditInfoPage(myPagePageController);
                        case "Followers" -> MainController.loadFollowersListPage(myPagePageController);
                        case "Followings" -> MainController.loadFollowingsPage(myPagePageController);
                        case "Notifications" -> MainController.loadNotificationsPage();
                        case "Requests" -> MainController.loadRequestsPage(myPagePageController);
                        case "Pendings" -> MainController.loadPendingsPage();
                        case "Blacklist" -> MainController.loadBlacklistPage(myPagePageController);
                        case "UploadImage" -> uploadTweetImage();
                    }
            }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        myPagePageController.setNewTweetListener(new NewTextListener() {
            @Override
            public void eventOccurred(NewTextEvent newTweetEvent) {
                newTweet(newTweetEvent.getText());
                myPagePageController.setTweetImage(null);
            }
        });
        myPagePageController.update(context.Users.get(MainController.currentUser.getId()));
    }

    public void uploadTweetImage(){

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterPNG);
        File file = fileChooser.showOpenDialog(null);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
            tweetImage = SwingFXUtils.toFXImage(bufferedImage, null);
            myPagePageController.setTweetImage(tweetImage);
            logger.info("user uploaded an image for new tweet");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void newTweet(String string){
        User user=context.Users.get(MainController.currentUser.getId());

        int id=0;
        if(tweetImage!=null) {
            context.Images.update(tweetImage);
            id=ID.lastUsedId();
        }
        Tweet tweet=new Tweet(user.getId(),string);
        tweet.setImage(id);
        context.Tweets.update(tweet);

        user.getTweets().add(tweet.getId());
        context.Users.update(user);
        logger.info("user made a new tweet with id: "+tweet.getId());

    }


    public List<Integer> tweets(List<Integer> list){
        List<Integer> tweets=new ArrayList<>();
        for (Integer tweetId:
             list) {
            Tweet tweet=context.Tweets.get(tweetId);
            if(!tweet.isBanned()){
                tweets.add(tweetId);
            }
        }
        return tweets;
    }
}

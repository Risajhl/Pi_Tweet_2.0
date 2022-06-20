package ir.pi.project.controller;

import ir.pi.project.config.others.LimitsConfig;
import ir.pi.project.config.texts.ShowTweetTexts;
import ir.pi.project.db.ID;
import ir.pi.project.event.NewCommentEvent;
import ir.pi.project.listener.NewCommentListener;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.Tweet;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.TweetComponentController;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ShowTweetLogic extends MainLogic {
    static private final Logger logger = LogManager.getLogger(ShowTweetLogic.class);
    private final ShowTweetTexts showTweetTexts=new ShowTweetTexts();
    private LimitsConfig limitsConfig=new LimitsConfig();
    List<Integer> tweets;
    int i;
    User writer;
    Tweet tweet;
    Image commentImage;


    TweetComponentController tweetComponentController;

    public ShowTweetLogic(TweetComponentController tweetComponentController, List<Integer> tweets) throws IOException {
        this.tweetComponentController = tweetComponentController;


        if (tweets.isEmpty()) {

        } else {
            this.i = tweets.size() - 1;
            this.tweets = tweets;
            this.tweet = context.Tweets.get(tweets.get(i));
            this.writer = context.Users.get(tweet.getWriter());
            tweetComponentController.update(tweet, writer);

        }

        tweetComponentController.setRetweetedLabel( context.Users.get(MainController.currentUser.getId()).getRetweets().contains(tweet.getId()));


        tweetComponentController.setTweetComponentListener(new StringListener() {
            @Override
            public void stringEventOccurred(String string) {
                User user=context.Users.get(MainController.currentUser.getId());
                tweetComponentController.setErrorLabel("",false);
                tweetComponentController.setInfoLabel("",false);
                try {

                    switch (string) {
                        case "Comments":
                            if (!tweet.getComments().isEmpty()) MainController.loadTweets(tweet.getComments());
                            else tweetComponentController.setErrorLabel(showTweetTexts.getNoComment(),true);

                            break;
                        case "Next":
                            if (i != 0) {
                                i--;
                                tweet = context.Tweets.get(tweets.get(i));
                                writer = context.Users.get(tweet.getWriter());
                                tweetComponentController.update(tweet, writer);
                                setProfileImage();
                                setTweetImage();
                                tweetComponentController.setRetweetedLabel(user.getRetweets().contains(tweet.getId()));
                            }
                            break;
                        case "Previous":
                            if (i != tweets.size() - 1) {
                                i++;
                                tweet = context.Tweets.get(tweets.get(i));
                                writer = context.Users.get(tweet.getWriter());
                                tweetComponentController.update(tweet, writer);
                                setProfileImage();
                                setTweetImage();
                                tweetComponentController.setRetweetedLabel(user.getRetweets().contains(tweet.getId()));
                            }

                            break;
                        case "Like":
                            like();
                            break;
                        case "Save":
                            save();
                            tweetComponentController.setInfoLabel(showTweetTexts.getSaved(), true);


                            break;
                        case "Forward":
                            MainController.loadForwardTweetPage(tweet.getId());
                            break;
                        case "Retweet":
                            retweet();
                            break;
                        case "Report":
                            tweet = context.Tweets.get(tweet.getId());
                            tweet.addToReportedTimes();
                            if (tweet.getReportedTimes() == limitsConfig.getReportLimit()) {
                                tweet.setBanned(true);
                            }
                            context.Tweets.update(tweet);
                            tweetComponentController.setInfoLabel(showTweetTexts.getReported(), true);
                            break;
                        case "Comment":

                            tweetComponentController.newComment(context.Users.get(MainController.currentUser.getId()), tweet);
                            tweetComponentController.setInfoLabel(showTweetTexts.getCommented(), true);
                            break;
                        case "UploadCommentImage":
                            uploadCommentImage();
                            break;
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        tweetComponentController.setNewCommentListener(new NewCommentListener() {
            @Override
            public void eventOccurred(NewCommentEvent newCommentEvent) {

                Tweet tweet = newCommentEvent.getTweet();
                User writer = newCommentEvent.getWriter();
                String text = newCommentEvent.getText();

                int id=0;
                if(commentImage!=null) {
                    context.Images.update(commentImage);
                    id= ID.lastUsedId();
                }

                Tweet comment = new Tweet(writer.id, text);
                comment.setImage(id);
                context.Tweets.update(comment);
                tweet.getComments().add(comment.getId());
                context.Tweets.update(tweet);

                tweetComponentController.setCommentImage(null);
                logger.info("user commented on tweet with id: "+tweet.getId());

            }
        });

        setProfileImage();
        setTweetImage();


    }

    private void retweet(){
        User user=context.Users.get(MainController.currentUser.getId());
        user.getTweets().add(tweet.getId());
        user.getRetweets().add(tweet.getId());
        context.Users.update(user);
        logger.info("user retweeted tweet with id: "+tweet.getId());
    }


    public void uploadCommentImage(){

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterPNG);
        File file = fileChooser.showOpenDialog(null);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
            commentImage = SwingFXUtils.toFXImage(bufferedImage, null);
            tweetComponentController.setCommentImage(commentImage);
            logger.info("user uploaded an image for a comment for tweet with id: "+tweet.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void setProfileImage(){
        if(writer.getImage()!=null)
            tweetComponentController.setProfileImage(context.Images.get(writer.getImage()));
        else
            tweetComponentController.setProfileImage(null);
    }
    private void setTweetImage(){

        if(tweet.getImage()!=0)
                tweetComponentController.setTweetImage(context.Images.get(tweet.getImage()));

        else
            tweetComponentController.setTweetImage(null);
    }

    private void save(){
        User user = context.Users.get(MainController.currentUser.getId());
        user.getSavedTweets().add(tweet.getId());
        context.Users.update(user);
        logger.info("user saved tweet with id: "+tweet.getId());

    }

    private void like() {
        User user = context.Users.get(MainController.currentUser.getId());

        boolean isLikedBefore = false;
        if (!user.getLikedTweets().isEmpty()) {
            for (int j = 0; j < user.getLikedTweets().size(); j++) {
                if (tweets.get(i).equals(user.getLikedTweets().get(j))) {
                    isLikedBefore = true;
                    user.getLikedTweets().remove(j);
                    context.Users.update(user);
                    Tweet tweet = context.Tweets.get(tweets.get(i));
                    tweet.addToLikeNums(-1);
                    context.Tweets.update(tweet);
                    tweetComponentController.update(tweet, context.Users.get(tweet.getWriter()));
                    logger.info("user liked tweet with id: "+tweet.getId());
                }
            }
        }
        if (!isLikedBefore) {
            user.getLikedTweets().add(tweets.get(i));
            Tweet tweet = context.Tweets.get(tweets.get(i));
            tweet.addToLikeNums(1);
            context.Users.update(user);
            context.Tweets.update(tweet);
            tweetComponentController.update(tweet, writer);
            logger.info("user disLiked tweet with id: "+tweet.getId());

        }


    }


}

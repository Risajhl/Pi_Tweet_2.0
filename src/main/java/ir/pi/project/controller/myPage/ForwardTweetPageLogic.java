package ir.pi.project.controller.myPage;

import ir.pi.project.config.addresses.InfoConfig;
import ir.pi.project.config.texts.myPage.ForwardTweetTexts;
import ir.pi.project.controller.MainLogic;
import ir.pi.project.controller.NewMessageLogic;
import ir.pi.project.controller.UserLogic;
import ir.pi.project.db.ID;
import ir.pi.project.event.NewTextEvent;
import ir.pi.project.listener.NewTextListener;
import ir.pi.project.model.Tweet;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.myPage.ForwardTweetPageController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class ForwardTweetPageLogic extends MainLogic {
    static private final Logger logger= LogManager.getLogger(ForwardTweetPageLogic.class);
    private final InfoConfig infoConfig=new InfoConfig();
    private final ForwardTweetTexts forwardTweetTexts=new ForwardTweetTexts();

    ForwardTweetPageController forwardTweetPageController;
    int tweetId;
    int receiverId;
    UserLogic userLogic;
    NewMessageLogic newMessageLogic;
    public ForwardTweetPageLogic(ForwardTweetPageController forwardTweetPageController,int tweetId) throws IOException {
        this.forwardTweetPageController=forwardTweetPageController;
        this.tweetId=tweetId;
        this.userLogic=new UserLogic();
        this.newMessageLogic=new NewMessageLogic();
        forwardTweetPageController.setUsernameListener(new NewTextListener() {
            @Override
            public void eventOccurred(NewTextEvent newTextEvent) {
                forwardTweetPageController.setError("",false);
             if (canSend(newTextEvent.getText())) {
                 forward();
                 logger.info("user forwarded tweet with id: "+tweetId+"to user "+newTextEvent.getText());
                 forwardTweetPageController.back();
             }
            }
        });
    }

    public boolean canSend(String username){
        try {
            User user=context.Users.get(MainController.currentUser.getId());

            File usersDirectory = new File(infoConfig.getUsersDirectory());
            boolean exists = false;
            for (File file :
                    usersDirectory.listFiles()) {
                User user1 = context.Users.get(ID.getIdFromFileName(file.getName()));
                if (user1.getUserName().equals(username) && user1.isActive()) {
                    exists = true;
                    if (user1.getId() == MainController.currentUser.getId()) {
                        forwardTweetPageController.setError(forwardTweetTexts.getItsU(),true);
                    } else {
                        if(userLogic.isFollowing(user1.getId(),user.getId())
                                || userLogic.isFollowing(user.getId(),user1.getId())){
                            this.receiverId=user1.getId();
                            return true;
                        }
                        else  {forwardTweetPageController.setError(forwardTweetTexts.getFf(),true);

                        }
                    }
                }
            }
            if (!exists) {
                forwardTweetPageController.setError(forwardTweetTexts.getNotFound(),true);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void forward(){
        Tweet tweet=context.Tweets.get(tweetId);
        User writer=context.Users.get(tweet.getWriter());
        String text=forwardTweetTexts.getForwardedFrom()+ "'"+writer.getUserName()+"'\n--------------------------------------------------------------\n"
                +tweet.getText();
        newMessageLogic.newMessage(MainController.currentUser.getId(),receiverId,text,context.Images.get(tweet.getImage()),true);
    }

}

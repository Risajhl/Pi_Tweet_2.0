package ir.pi.project.controller.explorer;

import ir.pi.project.config.addresses.InfoConfig;
import ir.pi.project.config.texts.explorer.ExplorerTexts;
import ir.pi.project.controller.MainLogic;
import ir.pi.project.controller.UserLogic;
import ir.pi.project.db.ID;
import ir.pi.project.event.SearchUserEvent;
import ir.pi.project.listener.SearchUserListener;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.Tweet;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.explorer.ExplorerPageController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExplorerPageLogic extends MainLogic {
    static private final Logger logger = LogManager.getLogger(ExplorerPageLogic.class);
    private final InfoConfig infoConfig=new InfoConfig();
    private final ExplorerTexts explorerTexts=new ExplorerTexts();

    ExplorerPageController explorerPageController;
    UserLogic userLogic;

    public ExplorerPageLogic(ExplorerPageController explorerPageController) throws IOException {
        this.explorerPageController = explorerPageController;
        this.userLogic = new UserLogic();
        explorerPageController.setSearchPageStringListener(new StringListener() {
            @Override
            public void stringEventOccurred(String string) {
                try {
                    explorerPageController.setErrorLabel("",false);
                    if (string.equals("World")) {
                        if (worldList().isEmpty())
                            explorerPageController.setErrorLabel(explorerTexts.getEmptyWorld(),true);
                        else MainController.loadTweets(worldList());

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        explorerPageController.setSearchUserListener(new SearchUserListener() {
            @Override
            public void eventOccurred(SearchUserEvent searchUserEvent) {
                String username = searchUserEvent.getUsername();
                logger.info("user searched for "+username+" profile");
                searchFor(username);
            }
        });
    }


    private void searchFor(String username) {
        try {

            File usersDirectory = new File(infoConfig.getUsersDirectory());
            boolean exists = false;
            for (File file :
                    usersDirectory.listFiles()) {
                User user1 = context.Users.get(ID.getIdFromFileName(file.getName()));
                if (user1.getUserName().equals(username) && user1.isActive()) {
                    exists = true;
                    if (user1.getId() == MainController.currentUser.getId()) {
                        explorerPageController.setErrorLabel(explorerTexts.getItsU(),true);
                    } else {
                        MainController.showProfile(user1);
                    }
                }
            }
            if (!exists) {
                explorerPageController.setErrorLabel(explorerTexts.getNotFound(),true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private List<Integer> worldList() {

        User user = context.Users.get(MainController.currentUser.getId());
        List<Integer> tweets = new ArrayList<>();
        File directory = new File(infoConfig.getTweetsDirectory());
        for (File file :
                directory.listFiles()) {
            Tweet tweet = context.Tweets.get(ID.getIdFromFileName(file.getName()));
            if (!tweet.isBanned()) {
                User user1 = context.Users.get(tweet.getWriter());
                System.out.println(user1.getUserName() + " " + userLogic.isMutedBy(user1.getId(), user.getId()));
                if (user1.isActive() && !userLogic.isMutedBy(user1.getId(), user.getId())) {
                    if (user1.isPublic() || userLogic.isFollowing(user.getId(), user1.getId())) {
                        tweets.add(ID.getIdFromFileName(file.getName()));
                    }
                }
            }
        }

        if (!tweets.isEmpty())
            Collections.shuffle(tweets);

        return tweets;

    }


}

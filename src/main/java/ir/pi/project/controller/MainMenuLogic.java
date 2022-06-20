package ir.pi.project.controller;

import ir.pi.project.config.texts.MainMenuTexts;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.Tweet;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.MainMenuController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainMenuLogic extends MainLogic {
    private final MainMenuTexts mainMenuTexts=new MainMenuTexts();
    MainMenuController mainMenuController;
    UserLogic userLogic;

    public MainMenuLogic(MainMenuController mainMenuController) throws IOException {
        this.mainMenuController = mainMenuController;
        this.userLogic = new UserLogic();
        mainMenuController.setMainMenuListener(new StringListener() {
            @Override
            public void stringEventOccurred(String string) {
                try {
                    mainMenuController.setErrorLabel("",false);

                    if (string.equals("MyPage")) {
                        MainController.loadMyPagePage();

                    }
                    if (string.equals("TimeLine")) {
                        if (tweets().isEmpty()) {
                            mainMenuController.setErrorLabel(mainMenuTexts.getEmptyWorld(),true);
                        } else {
                            MainController.loadTweets(tweets());
                        }
                    }
                    if (string.equals("Explorer")) {
                        MainController.loadExplorer();


                    }
                    if (string.equals("Messages")) {
                        MainController.loadMessagesPage();
                    }
                    if (string.equals("Settings")) {
                        MainController.loadSettingsPage();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public List<Integer> tweets() {
        User user = context.Users.get(MainController.currentUser.getId());
        List<Integer> tweets = new ArrayList<>();
        if (!user.getFollowings().isEmpty()) {
            for (int i = 0; i < user.getFollowings().size(); i++) {
                User user1 = context.Users.get(user.getFollowings().get(i));
                if (user1.isActive()) {
                    if (!userLogic.isMutedBy(user1.getId(), user.getId()) && !userLogic.isBlockedBy(user1.getId(), user.getId())) {
                        if (!user1.getTweets().isEmpty()) {
                            for (int j = 0; j < user1.getTweets().size(); j++) {
                                Tweet tweet = context.Tweets.get(user1.getTweets().get(j));
                                if (!tweet.isBanned()) {
                                    if (user1.getRetweets().contains(user1.getTweets().get(j))) {

                                        User writer = context.Users.get(tweet.getWriter());
                                        if (writer.isPublic() || userLogic.isFollowing(user.getId(), writer.getId())) {
                                            if (!userLogic.isBlockedBy(writer.getId(), user.getId()) && !userLogic.isMutedBy(writer.getId(), user.getId()) && writer.isActive() && writer.getId() != user.getId()) {

                                                tweets.add(user1.getTweets().get(j));
                                            }
                                        }
                                    } else {
                                        tweets.add(user1.getTweets().get(j));
                                    }
                                }
                            }
                        }
                        if (!user1.getLikedTweets().isEmpty()) {
                            for (int j = 0; j < user1.getLikedTweets().size(); j++) {
                                Tweet tweet = context.Tweets.get(user1.getLikedTweets().get(j));
                                if (!tweet.isBanned()){
                                    User user2 = context.Users.get(tweet.getWriter());
                                if (user2.isActive() && !userLogic.isMutedBy(user2.getId(), user.getId()) && user2.getId() != user.getId()) {
                                    if (user2.isPublic() || userLogic.isFollowing(user.getId(), user2.getId())) {
                                        tweets.add(user1.getLikedTweets().get(j));
                                    }
                                }
                            }
                            }
                        }
                    }
                }
            }
            if (!tweets.isEmpty())
                Collections.shuffle(tweets);
        }
        return tweets;
    }


}



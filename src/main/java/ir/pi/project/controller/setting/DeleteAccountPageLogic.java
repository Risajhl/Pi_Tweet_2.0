package ir.pi.project.controller.setting;

import ir.pi.project.config.addresses.InfoConfig;
import ir.pi.project.config.texts.setting.DeleteAccountTexts;
import ir.pi.project.controller.MainLogic;
import ir.pi.project.db.ID;
import ir.pi.project.event.NewTextEvent;
import ir.pi.project.listener.NewTextListener;
import ir.pi.project.model.*;
import ir.pi.project.view.MainController;
import ir.pi.project.view.setting.DeleteAccountPageController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class DeleteAccountPageLogic extends MainLogic {
    private final InfoConfig infoConfig=new InfoConfig();
    private final DeleteAccountTexts deleteAccountTexts=new DeleteAccountTexts();
    static private final Logger logger = LogManager.getLogger(DeleteAccountPageLogic.class);
    DeleteAccountPageController deleteAccountPageController;

    public DeleteAccountPageLogic(DeleteAccountPageController deleteAccountPageController) throws IOException {
        this.deleteAccountPageController = deleteAccountPageController;
        deleteAccountPageController.setNewTextListener(new NewTextListener() {
            @Override
            public void eventOccurred(NewTextEvent newTextEvent) {
                if (context.Users.get(MainController.currentUser.getId()).getPassword().equals(newTextEvent.getText())) {
                    deleteAccount();
                    try {
                        MainController.loadWelcome();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else deleteAccountPageController.setErrorLabel(deleteAccountTexts.getWrongPassword(),true);
            }
        });


    }


    public void deleteAccount() {
        User user = context.Users.get(MainController.currentUser.getId());


        File usersDirectory = new File(infoConfig.getUsersDirectory());
        File tweetsDirectory = new File(infoConfig.getTweetsDirectory());
        File messagesDirectory = new File(infoConfig.getMessagesDirectory());
        File groupsDirectory = new File(infoConfig.getGroupsDirectory());
        File groupChatsDirectory = new File(infoConfig.getGroupChatsDirectory());


        for (File file :
                usersDirectory.listFiles()) {
            User user1 = context.Users.get(ID.getIdFromFileName(file.getName()));
            if (user1.getBlackList().contains(user.getId())) {
                user1.getBlackList().remove((Object) user.getId());
                context.Users.update(user1);
                logger.info("user " + user.getId() + " deleted from " + user1.getUserName() + " BlackList");
            }
            if (user1.getFollowings().contains(user.getId())) {
                user1.getFollowings().remove((Object) user.getId());
                context.Users.update(user1);
                logger.info("user " + user.getId() + " deleted from " + user1.getUserName() + " Followings");
            }
            if (user1.getFollowers().contains(user.getId())) {
                user1.getFollowers().remove((Object) user.getId());
                context.Users.update(user1);
                logger.info("user " + user.getId() + " deleted from " + user1.getUserName() + " Followers");
            }

            for (int i = 0; i < user1.getGroups().size(); i++) {
                Group group = context.Groups.get(user1.getGroups().get(i));
                if (group.getMembers().contains(user.getId())) {
                    group.getMembers().remove((Object) user.getId());
                    logger.info("user " + user.getId() + " deleted from group " + group.getId());
                    context.Groups.update(group);

                }
            }


            for (int i = 0; i < user1.getSavedMessages().size(); i++) {
                Message message = context.Messages.get(user1.getSavedMessages().get(i));
                if (message.getSenderId() == user.getId()) {
                    user1.getSavedMessages().remove(i);
                    context.Users.update(user1);
                    logger.info("message " + message.getId() + " deleted from " + user1.getUserName() + " SavedMessages");
                    i--;
                }
            }

            for (int i = 0; i < user1.getSavedTweets().size(); i++) {
                Tweet tweet = context.Tweets.get(user1.getSavedTweets().get(i));
                if (tweet.getWriter() == user.getId()) {
                    user1.getSavedMessages().remove(i);
                    context.Users.update(user1);
                    logger.info("tweet " + tweet.getId() + " deleted from " + user1.getUserName() + " savedMessages");
                    i--;
                }
            }


            for (int i = 0; i < user1.getLikedTweets().size(); i++) {
                Tweet tweet = context.Tweets.get(user1.getLikedTweets().get(i));
                if (tweet.getWriter() == user.getId()) {
                    user1.getLikedTweets().remove(i);
                    context.Users.update(user1);
                    logger.info("tweet " + tweet.getId() + " deleted from " + user1.getUserName() + " LikedTweets");
                    i--;
                }
            }

            if (user1.getUnRead().contains(user.getUserName())) {
                user1.getUnRead().remove(user.getUserName());
                context.Users.update(user1);
                logger.info("user " + user.getId() + " deleted from " + user1.getUserName() + " unReads");
            }

            if (user1.getUnReadUsernames().containsKey(user.getUserName())) {
                user1.getUnReadUsernames().remove(user.getUserName());
                context.Users.update(user1);
                logger.info("user " + user.getId() + " deleted from " + user1.getUserName() + " unReadUserNames");
            }

            if (user1.getRequests().contains(user.getUserName())) {
                user.getRequests().remove(user.getUserName());
                context.Users.update(user1);
                logger.info("user " + user.getId() + " deleted from " + user1.getUserName() + " requests");

            }

            for (int i = 0; i < user1.getChats().size(); i++) {
                Message message = context.Messages.get(user1.getChats().get(i).get(0));
                if (message.getSenderId() == user.getId() || message.getReceiverId() == user.getId()) {
                    user1.getChats().remove(i);
                    context.Users.update(user1);
                    logger.info("a chat with user " + user.getId() + " deleted from " + user1.getUserName() + " chats");
                    i--;
                }
            }

            for (int i = 0; i < user1.getTweets().size(); i++) {
                Tweet tweet = context.Tweets.get(user1.getTweets().get(i));
                if (tweet.getWriter() == user.getId()) {
                    user1.getTweets().remove(i);
                    context.Users.update(user1);
                    logger.info("a retweet of user " + user.getId() + " deleted from " + user1.getUserName() + " tweets");
                    i--;
                }
            }


            for (int i = 0; i < user1.getNotifications().size(); i++) {
                String followName = user.getUserName() + " started following you!";
                String unfollowName = user.getUserName() + " stopped following you!";
                if (user1.getNotifications().get(i).equals(followName) || user1.getNotifications().get(i).equals(unfollowName)) {
                    user1.getNotifications().remove(i);
                    context.Users.update(user1);
                    logger.info("a notification of user " + user.getId() + " deleted from " + user1.getUserName() + " notifications");
                    i--;
                }
            }


        }

        for (File file :
                groupChatsDirectory.listFiles()) {
            GroupChat groupChat = context.GroupChats.get(ID.getIdFromFileName(file.getName()));
            if(!groupChat.getMessages().isEmpty()){
                for (int i = 0; i < groupChat.getMessages().size(); i++) {

                    Message message = context.Messages.get(groupChat.getMessages().get(i));
                    if (message.getSenderId() == user.getId()) {
                        groupChat.getMessages().remove(i);
                        context.GroupChats.update(groupChat);
                        logger.info("a message of user " + user.getId() + " deleted from groupChat " + groupChat.getGroupName() + " with id: " + groupChat.getId());

                        i--;
                    }

                }

            }

            for (int i = 0; i < groupChat.getMembers().size(); i++) {
                User member = context.Users.get(groupChat.getMembers().get(i));
                if (member.getId() == user.getId()) {
                    groupChat.getMembers().remove(i);
                    context.GroupChats.update(groupChat);
                    logger.info("user was deleted from the group chat" + groupChat.getGroupName()+" with id: "+groupChat.getId());

                    i--;
                }
            }
        }

        for (Integer tweetId :
                user.getLikedTweets()) {
            Tweet tweet = context.Tweets.get(tweetId);
            tweet.addToLikeNums(-1);
            context.Tweets.update(tweet);
        }

        for (File file :
                tweetsDirectory.listFiles()) {
            Tweet tweet = context.Tweets.get(ID.getIdFromFileName(file.getName()));
            if (tweet.getWriter() == user.getId()) {
                file.delete();
                logger.info("Tweet " + tweet.getId() + " deleted");
            }
        }



        for (File file :
                messagesDirectory.listFiles()) {
            Message message = context.Messages.get(ID.getIdFromFileName(file.getName()));
            if (message.getSenderId() == user.getId()) {
                file.delete();
                logger.info("Message " + message.getId() + " deleted");
            }

        }


        for (File file :
                usersDirectory.listFiles()) {
            User thisUser = context.Users.get(ID.getIdFromFileName(file.getName()));
            if (thisUser.getId() == user.getId()) {
                file.delete();
                logger.info("User " + thisUser.getId() + " deleted");

            }
        }




    }


}

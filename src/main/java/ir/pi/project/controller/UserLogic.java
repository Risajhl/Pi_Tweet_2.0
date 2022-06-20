package ir.pi.project.controller;

import ir.pi.project.config.texts.explorer.ShowProfileTexts;
import ir.pi.project.model.Group;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class UserLogic extends MainLogic {

    static private final Logger logger= LogManager.getLogger(UserLogic.class);
    private  ShowProfileTexts showProfileText;

    {
        try {
            showProfileText = new ShowProfileTexts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void follow(int userId,int user1Id) {
        User me=context.Users.get(userId);
        User user1 = context.Users.get(user1Id);
        if (user1.isPublic()) {
            user1.getFollowers().add(me.getId());
            me.getFollowings().add(user1Id);
            user1.getNotifications().add(me.getUserName() + showProfileText.getStartedFollowing());

        } else {
            user1.getRequests().add(me.getId());
        }


        context.Users.update(user1);
        context.Users.update(me);
        logger.info(me.getUserName() + " followed " + user1.getUserName());
    }

    public void deleteRequest(int id){
        User user1 = context.Users.get(id);
        for (int i = 0; i < user1.getRequests().size(); i++) {
            if (user1.getRequests().get(i).equals(MainController.currentUser.getId())) {
                user1.getRequests().remove(i);
            }
        }
        context.Users.update(user1);
        context.Users.update(MainController.currentUser);
        logger.info("user deleted its request to "+user1.getUserName());
    }

    public void unFollow(int userId,int user1Id) {
        User me=context.Users.get(userId);
        User user1 = context.Users.get(user1Id);
        for (int i = 0; i < user1.getFollowers().size(); i++) {
            if (user1.getFollowers().get(i) == me.getId()) {
                user1.getFollowers().remove(i);
                for (Integer groupId :
                        user1.getGroups()) {
                    Group group = context.Groups.get(groupId);
                    if (group.getMembers().contains(user1.getId())) {
                        group.getMembers().remove((Object) user1.getId());
                    }
                }
                user1.getNotifications().add(me.getUserName() + showProfileText.getStoppedFollowing());
            }
        }
        for (int i = 0; i < me.getFollowings().size(); i++) {
            if (me.getFollowings().get(i) == user1Id) {
                me.getFollowings().remove(i);
            }
        }
        context.Users.update(user1);
        context.Users.update(me);
        logger.info(me.getUserName() + " unfollowed " + user1.getUserName());

    }

    public boolean isFollowing(int user1Id,int user2Id) {
        User otherUser = context.Users.get(user2Id);
        for (int i = 0; i < otherUser.getFollowers().size(); i++) {
            if (otherUser.getFollowers().get(i) ==user1Id) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRequestedToFollow(int id) {
        User user1 = context.Users.get(id);
        for (int i = 0; i < user1.getRequests().size(); i++) {
            if (user1.getRequests().get(i).equals(MainController.currentUser.getId())) {
                return true;
            }
        }
        return false;
    }

    public boolean isBlockedBy(int userId,int user1Id) {
        User user1 = context.Users.get(user1Id);
        for (int i = 0; i < user1.getBlackList().size(); i++) {
            if (user1.getBlackList().get(i) == userId) {
                return true;
            }
        }
        return false;
    }

    public void block(int userId,int user1Id) {

        User user=context.Users.get(userId);
        User user1 = context.Users.get(user1Id);
        user.getBlackList().add(user1Id);
        context.Users.update(user1);
        context.Users.update(user);
        if (isFollowing(userId,user1Id)) unFollow(userId,user1Id);
        if (isFollowing(user1Id,userId)) {
            unFollow(user1Id,userId);
        }

        logger.info(user.getUserName() + " blocked " + user1.getUserName());
    }

    public void unBlock(int userId,int user1Id) {

        User user=context.Users.get(userId);
        User user1 = context.Users.get(user1Id);
        for (int i = 0; i < user.getBlackList().size(); i++) {
            if (user1Id == user.getBlackList().get(i)) {
                user.getBlackList().remove(i);

            }
        }
        context.Users.update(user);
        logger.info(user.getUserName() + " unBlocked " + user1.getUserName());
    }

    public void mute(int userId,int user1Id) {
        User user=context.Users.get(userId);
        User user1=context.Users.get(user1Id);

        if (!isMutedBy(user1.getId(),user.getId())) {
            user.getMuted().add(user1.getId());
        }
        context.Users.update(user);
        context.Users.update(user1);
        System.out.println(user.getMuted());
        logger.info(user.getUserName() + " muted " + user1.getUserName());
    }

    public void unMute(int userId,int user1Id){
        User user=context.Users.get(userId);
        User user1=context.Users.get(user1Id);

        for(int i=0;i<user.getMuted().size();i++){
            if(user.getMuted().get(i).equals(user1Id))
                user.getMuted().remove(i);
        }
        context.Users.update(user);
        context.Users.update(user1);
        logger.info(user.getUserName() + " unMuted " + user1.getUserName());

    }

    public boolean isMutedBy(int userId,int user1Id){
        User user1=context.Users.get(user1Id);
        for (int i = 0; i < user1.getMuted().size(); i++) {
            if (user1.getMuted().get(i) == userId) {
                return true;
            }
        }

        return false;
    }
}

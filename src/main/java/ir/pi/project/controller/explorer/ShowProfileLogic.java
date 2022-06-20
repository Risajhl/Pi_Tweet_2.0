package ir.pi.project.controller.explorer;

import ir.pi.project.config.texts.explorer.ShowProfileTexts;
import ir.pi.project.controller.MainLogic;
import ir.pi.project.controller.UserLogic;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.Message;
import ir.pi.project.model.Tweet;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.explorer.ShowProfileController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowProfileLogic extends MainLogic {

    private final ShowProfileTexts showProfileTexts=new ShowProfileTexts();
    ShowProfileController showProfileController;
    User user;
    UserLogic userLogic;
    public ShowProfileLogic(ShowProfileController showProfileController,User user) throws IOException {
        this.showProfileController=showProfileController;
        this.user=user;
        this.userLogic=new UserLogic();



        showProfileController.setShowProfileListener(new StringListener() {
            @Override
            public void stringEventOccurred(String string) {
                try {
                    showProfileController.setErrorLabel("",false);
//                    showProfileController.showNoTweets(false);
                    switch (string) {
                        case "Follow":
                            followLogic();

                            break;
                        case "Message":
                            openChat(user.getUserName());
                            break;
                        case "Mute":
                            muteLogic();

                            break;
                        case "Block":
                            blockLogic();
                            break;
                        case "Tweets":

                            if (!tweets(user.getTweets()).isEmpty())
                                MainController.loadTweets(tweets(user.getTweets()));
                            else showProfileController.setErrorLabel(showProfileTexts.getNoTweets(), true);

                            break;
                        case "Report":
                            showProfileController.setErrorLabel(showProfileTexts.getReportText(), true);


                            break;
                            case "ShowImage":
                                if(context.Users.get(user.getId()).getImage()!=null)
                            MainController.showProfileImage(context.Users.get(user.getId()));


                            break;
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        update();
        setProfileImage();
    }



    private void setProfileImage(){
        if(user.getImage()!=null)
            showProfileController.setProfileImage(context.Images.get(user.getImage()));
        else
            showProfileController.setProfileImage(null);
    }
    public void update(){
        User me= context.Users.get(MainController.currentUser.getId());
        User user=context.Users.get(this.user.getId());


        //lastSeen
        boolean lastSeenCanSee=true;
        switch (user.getLastSeenState()) {
            case "NoOne" -> lastSeenCanSee = false;
            case "Followers" -> lastSeenCanSee = userLogic.isFollowing(me.getId(),user.getId());
        }

        //isFollowing
        boolean isFollowing;
        isFollowing=userLogic.isFollowing(me.getId(),user.getId());

        //followingState
        String followingState;

        if (userLogic.hasRequestedToFollow(user.getId())) {
            followingState = showProfileTexts.getRequested();
            showProfileController.changeFollowButtonText(showProfileTexts.getDeleteRequest());
        } else if(!userLogic.isFollowing(me.getId(),user.getId()) && !user.isPublic()){
            followingState=showProfileTexts.getPrivateAcc();
        }

        else {
            if (userLogic.isFollowing(me.getId(),user.getId())) {
                followingState = showProfileTexts.getFollowing();
                showProfileController.changeFollowButtonText(showProfileTexts.getUnfollow());
            } else {
                followingState = "";
                showProfileController.changeFollowButtonText(showProfileTexts.getFollow());

            }
        }


        //EBPCanSee
        boolean EPBCanSee;
        EPBCanSee=user.isEPBCanSee();

        //uBlocked
        boolean uBlocked;
        uBlocked=userLogic.isBlockedBy(user.getId(),me.getId());

        //urBlocked
        boolean urBlocked;
        urBlocked=userLogic.isBlockedBy(me.getId(), user.getId());

        //isMuted
        boolean isMuted;
        isMuted=userLogic.isMutedBy(user.getId(), me.getId());
        System.out.println(isMuted);

        showProfileController.update(user,EPBCanSee,lastSeenCanSee,uBlocked,urBlocked,isFollowing,isMuted,followingState);


    }

    public void followLogic(){
        User me=MainController.currentUser;
        User user=context.Users.get(this.user.getId());
        if(userLogic.isFollowing(me.getId(),user.getId())){
            userLogic.unFollow(me.getId(),user.getId());
            update();
            showProfileController.changeFollowButtonText(showProfileTexts.getFollow());
        }
        else if(userLogic.hasRequestedToFollow(user.getId()) && !user.isPublic()){
            userLogic.deleteRequest(user.getId());
            update();
            showProfileController.changeFollowButtonText(showProfileTexts.getFollow());
        }
        else {
            if(user.isPublic()) {
                userLogic.follow(me.getId(),user.getId());
                update();
                showProfileController.changeFollowButtonText(showProfileTexts.getUnfollow());
            }
            else {
                user.getRequests().add(me.getId());
                context.Users.update(user);
                update();
                showProfileController.changeFollowButtonText(showProfileTexts.getUnfollow());
            }
        }




    }

    private void blockLogic(){
        User me=context.Users.get(MainController.currentUser.getId());
        User user=context.Users.get(this.user.getId());
        if(userLogic.isBlockedBy(user.getId(),me.getId())){
            userLogic.unBlock(me.getId(),user.getId());
        }else {
            userLogic.block(me.getId(),user.getId());
        }
        update();

    }

    private void muteLogic(){
        User me=context.Users.get(MainController.currentUser.getId());
        User user=context.Users.get(this.user.getId());

        if(!userLogic.isMutedBy(user.getId(),me.getId())){
            userLogic.mute(me.getId(),user.getId());
        }
        else {
            userLogic.unMute(me.getId(),user.getId());
        }

        update();
    }

    private List<Integer> tweets(List<Integer> list){
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

    private void openChat(String username) {
        try {

            User user = context.Users.get(MainController.currentUser.getId());
            boolean usernameExists = false;

            for (List<Integer> list :
                    user.getChats()) {
                if (!list.isEmpty()) {
                    Message message = context.Messages.get(list.get(0));
                    User user1 = context.Users.get(message.getSenderId());
                    User user2 = context.Users.get(message.getReceiverId());
                    if (user1.getUserName().equals(username) && user1.getId() != user.getId() || user2.getUserName().equals(username) && user2.getId() != user.getId()) {
                        if (user1.isActive() && user2.isActive()) {
                            usernameExists = true;

                            int userId = 0;
                            int user1Id = 0;

                            if (message.getReceiverId() == user.getId()) {
                                userId = message.getReceiverId();
                                user1Id = message.getSenderId();
                                user = context.Users.get(user.getId());
                                context.Users.update(user);
                                User user3 = context.Users.get(message.getSenderId());
                                if (isInList(context.Users.get(userId).getUnRead(), user3.getUserName())) {
                                    removeFromUnread(userId, user3.getUserName());
                                }
                            } else if (message.getSenderId() == user.getId()) {
                                user1Id = message.getReceiverId();
                                userId = message.getSenderId();
                                user = context.Users.get(user.getId());
                                context.Users.update(user);
                                User user3 = context.Users.get(message.getReceiverId());
                                if (isInList(user.getUnRead(), user3.getUserName())) {
                                    removeFromUnread(userId, user3.getUserName());


                                }
                            }

                            MainController.showDirectChatPage(list,userId,user1Id);


                        }
                    }

                }
            }

            if (!usernameExists) {
                List<Integer> chat=new ArrayList<>();
                for (User user1:
                        context.Users.all()) {
                    if(user1.getUserName().equals(username))
                        MainController.showDirectChatPage(chat,user.getId(),user1.getId());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isInList(List<String> list, String userName){
        for (String s : list) {
            if (s.equals(userName)) return true;
        }
        return false;
    }

    private void removeFromUnread(Integer userId, String userName){
        User user=context.Users.get(userId);
        for (int i=0;i<user.getUnRead().size();i++){
            if(user.getUnRead().get(i).equals(userName)){
                user.getUnRead().remove(i);
                user.getUnReadUsernames().remove(userName);
                i--;
            }
        }
        context.Users.update(user);
    }




}

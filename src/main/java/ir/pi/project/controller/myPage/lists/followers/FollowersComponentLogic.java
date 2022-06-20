package ir.pi.project.controller.myPage.lists.followers;

import ir.pi.project.controller.MainLogic;
import ir.pi.project.controller.UserLogic;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.User;
import ir.pi.project.view.myPage.lists.followers.FollowersComponentController;
import ir.pi.project.view.MainController;
import ir.pi.project.view.myPage.MyPagePageController;
import javafx.scene.layout.VBox;

public class FollowersComponentLogic extends MainLogic {
    FollowersComponentController followersComponentController;
    User follower;
    UserLogic userLogic;
    MyPagePageController myPagePageController;
    public FollowersComponentLogic(FollowersComponentController followersComponentController, int followerId, VBox content, MyPagePageController myPagePageController){
        this.followersComponentController=followersComponentController;
        this.userLogic=new UserLogic();
        this.follower=context.Users.get(followerId);
        this.myPagePageController=myPagePageController;
        followersComponentController.update(follower);
        setProfileImage();
        followersComponentController.setContent(content);
        followersComponentController.setFollowersComponentListener(new StringListener() {
            @Override
            public void stringEventOccurred(String string) {
                if(string.equals("Delete")){
                    userLogic.unFollow(followerId,MainController.currentUser.getId());
                }
                myPagePageController.update(context.Users.get(MainController.currentUser.getId()));
            }
        });
    }

    private void setProfileImage(){
        if(follower.getImage()!=null)
            followersComponentController.setProfileImage(context.Images.get(follower.getImage()));
        else
            followersComponentController.setProfileImage(null);
    }

}

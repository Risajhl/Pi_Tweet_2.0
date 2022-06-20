package ir.pi.project.controller.myPage.lists.followings;

import ir.pi.project.controller.MainLogic;
import ir.pi.project.controller.UserLogic;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.myPage.lists.followings.FollowingsComponentController;
import ir.pi.project.view.myPage.MyPagePageController;
import javafx.scene.layout.VBox;

public class FollowingsComponentLogic extends MainLogic {
    FollowingsComponentController followingsComponentController;
    User following;
    UserLogic userLogic;
    MyPagePageController myPagePageController;

    public FollowingsComponentLogic(FollowingsComponentController followingsComponentController, int followingId, VBox content, MyPagePageController myPagePageController) {
        this.followingsComponentController = followingsComponentController;
        this.userLogic = new UserLogic();
        this.following = context.Users.get(followingId);
        this.myPagePageController = myPagePageController;
        followingsComponentController.update(following);
        setProfileImage();
        followingsComponentController.setContent(content);
        followingsComponentController.setFollowersComponentListener(new StringListener() {
            @Override
            public void stringEventOccurred(String string) {
                if (string.equals("Unfollow")) {
                    userLogic.unFollow(MainController.currentUser.getId(), followingId);
                }
                myPagePageController.update(context.Users.get(MainController.currentUser.getId()));
            }
        });
    }


    private void setProfileImage() {
        if (following.getImage() != null)
            followingsComponentController.setProfileImage(context.Images.get(following.getImage()));
        else
            followingsComponentController.setProfileImage(null);
    }

}

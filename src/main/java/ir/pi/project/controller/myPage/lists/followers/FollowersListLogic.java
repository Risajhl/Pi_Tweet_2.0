package ir.pi.project.controller.myPage.lists.followers;

import ir.pi.project.controller.MainLogic;
import ir.pi.project.view.myPage.lists.followers.FollowersListPageController;
import ir.pi.project.view.MainController;
import ir.pi.project.view.myPage.MyPagePageController;

public class FollowersListLogic extends MainLogic {
    FollowersListPageController followersListPageController;
    MyPagePageController myPagePageController;
    public FollowersListLogic(  FollowersListPageController followersListPageController,MyPagePageController myPagePageController){
        this.followersListPageController=followersListPageController;
        this.myPagePageController=myPagePageController;
        followersListPageController.setMyPagePageController(this.myPagePageController);
        followersListPageController.update(context.Users.get(MainController.currentUser.getId()).getFollowers());
    }
}

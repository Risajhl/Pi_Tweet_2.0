package ir.pi.project.controller.myPage.lists.followings;

import ir.pi.project.controller.MainLogic;
import ir.pi.project.view.MainController;
import ir.pi.project.view.myPage.lists.followings.FollowingsListPageController;
import ir.pi.project.view.myPage.MyPagePageController;

public class FollowingsListLogic extends MainLogic {
    FollowingsListPageController followingsListPageController;
    MyPagePageController myPagePageController;
    public FollowingsListLogic(FollowingsListPageController followingsListPageController,MyPagePageController myPagePageController){
        this.followingsListPageController=followingsListPageController;
        this.myPagePageController=myPagePageController;
        followingsListPageController.setMyPagePageController(this.myPagePageController);
        followingsListPageController.update(context.Users.get(MainController.currentUser.getId()).getFollowings());
    }
}

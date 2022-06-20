package ir.pi.project.controller.myPage.lists.blacklist;

import ir.pi.project.controller.MainLogic;
import ir.pi.project.view.MainController;
import ir.pi.project.view.myPage.lists.blacklist.BlacklistController;
import ir.pi.project.view.myPage.MyPagePageController;

public class BlacklistLogic extends MainLogic {
    BlacklistController blacklistController;
    MyPagePageController myPagePageController;
    public BlacklistLogic(  BlacklistController blacklistController,MyPagePageController myPagePageController){
        this.blacklistController=blacklistController;
        this.myPagePageController=myPagePageController;
        blacklistController.setMyPagePageController(this.myPagePageController);
        blacklistController.update(context.Users.get(MainController.currentUser.getId()).getBlackList());
    }
}

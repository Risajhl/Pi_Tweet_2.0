package ir.pi.project.controller.myPage.lists.blacklist;

import ir.pi.project.controller.MainLogic;
import ir.pi.project.controller.UserLogic;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.myPage.lists.blacklist.BlacklistComponentController;
import ir.pi.project.view.myPage.MyPagePageController;
import javafx.scene.layout.VBox;

public class BlacklistComponentLogic extends MainLogic {
    BlacklistComponentController blacklistComponentController;
    User blackUser;
    UserLogic userLogic;
    MyPagePageController myPagePageController;
    public BlacklistComponentLogic(BlacklistComponentController blacklistComponentController, int followerId, VBox content, MyPagePageController myPagePageController) {
        this.blacklistComponentController = blacklistComponentController;
        this.userLogic = new UserLogic();
        this.blackUser = context.Users.get(followerId);
        this.myPagePageController = myPagePageController;
        blacklistComponentController.update(blackUser);
        blacklistComponentController.setContent(content);
        blacklistComponentController.setBlacklistComponentListener(new StringListener() {
            @Override
            public void stringEventOccurred(String string) {
                if (string.equals("Unblock")) {
                    userLogic.unBlock(MainController.currentUser.getId(), blackUser.getId());
                }
                myPagePageController.update(context.Users.get(MainController.currentUser.getId()));
            }
        });
    }


}

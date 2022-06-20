package ir.pi.project.controller.myPage;

import ir.pi.project.controller.MainLogic;
import ir.pi.project.view.MainController;
import ir.pi.project.view.myPage.NotificationsPageController;

public class NotificationsPageLogic extends MainLogic {
    NotificationsPageController notificationsPageController;
    public NotificationsPageLogic(NotificationsPageController notificationsPageController){
        this.notificationsPageController=notificationsPageController;
        notificationsPageController.update(context.Users.get(MainController.currentUser.getId()).getNotifications());
    }
}

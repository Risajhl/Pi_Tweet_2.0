package ir.pi.project.controller.myPage;

import ir.pi.project.controller.MainLogic;
import ir.pi.project.view.MainController;
import ir.pi.project.view.myPage.PendingsController;

public class PendingsListLogic extends MainLogic {
    PendingsController pendingsController;
    public PendingsListLogic(PendingsController pendingsController){
        this.pendingsController=pendingsController;
        pendingsController.update(context.Users.get(MainController.currentUser.getId()).getMyRequests());
    }
}

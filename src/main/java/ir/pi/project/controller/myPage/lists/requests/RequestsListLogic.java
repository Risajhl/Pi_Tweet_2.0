package ir.pi.project.controller.myPage.lists.requests;

import ir.pi.project.controller.MainLogic;
import ir.pi.project.view.MainController;
import ir.pi.project.view.myPage.MyPagePageController;
import ir.pi.project.view.myPage.lists.requests.RequestsListController;

public class RequestsListLogic extends MainLogic {
    RequestsListController requestsListController;
    MyPagePageController myPagePageController;
    public RequestsListLogic(RequestsListController requestsListController,MyPagePageController myPagePageController){
        this.requestsListController=requestsListController;
        this.myPagePageController=myPagePageController;
        requestsListController.setMyPagePageController(this.myPagePageController);
        requestsListController.update(context.Users.get(MainController.currentUser.getId()).getRequests());
    }
}

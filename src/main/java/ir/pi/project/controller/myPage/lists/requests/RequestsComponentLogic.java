package ir.pi.project.controller.myPage.lists.requests;

import ir.pi.project.config.texts.explorer.ShowProfileTexts;
import ir.pi.project.controller.MainLogic;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.myPage.MyPagePageController;
import ir.pi.project.view.myPage.lists.requests.RequestsComponentController;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class RequestsComponentLogic extends MainLogic {
    static private final Logger logger = LogManager.getLogger(RequestsListLogic.class);
    private final ShowProfileTexts showProfileTexts=new ShowProfileTexts();

    RequestsComponentController requestsComponentController;
    User requester;
    MyPagePageController myPagePageController;

    public RequestsComponentLogic(RequestsComponentController requestsComponentController, int requesterId, VBox content, MyPagePageController myPagePageController) throws IOException {
        this.requestsComponentController = requestsComponentController;
        this.requester = context.Users.get(requesterId);
        this.myPagePageController = myPagePageController;
        requestsComponentController.update(requester);
        setProfileImage();
        requestsComponentController.setContent(content);
        requestsComponentController.setRequestsComponentListener(new StringListener() {
            @Override
            public void stringEventOccurred(String string) {
                switch (string) {
                    case "Delete" -> delete();
                    case "Delete and Inform" -> deleteAndInform();
                    case "Accept" -> accept();
                }

                myPagePageController.update(context.Users.get(MainController.currentUser.getId()));
            }
        });
    }

    private void setProfileImage(){
        if(context.Images.get(requester.getImage())!=null)
            requestsComponentController.setProfileImage(context.Images.get(requester.getImage()));
        else
            requestsComponentController.setProfileImage(null);
    }

    private void accept(){
        User user1=context.Users.get(MainController.currentUser.getId());
        User user2=context.Users.get(requester.getId());

        user1.getNotifications().add(user2.getUserName() + showProfileTexts.getStartedFollowing());
        user2.getMyRequests().add(user1.getUserName()+showProfileTexts.getRequestAccepted());
        for (int i=0;i<user1.getRequests().size();i++){
            if (user1.getRequests().get(i).equals(user2.getId())) {
                user1.getFollowers().add(user1.getRequests().get(i));
                user1.getRequests().remove(i);
            }
        }
        user2.getFollowings().add(user1.getId());

        context.Users.update(user1);
        context.Users.update(user2);
        logger.info("user "+user1.getUserName()+" accepted "+user2.getUserName()+"'s request");
    }

    private void delete(){
        User user1=context.Users.get(MainController.currentUser.getId());
        User user2=context.Users.get(requester.getId());
        for (int i=0;i<user1.getRequests().size();i++){
            if (user1.getRequests().get(i).equals(user2.getId())) {
                user1.getRequests().remove(i);
            }
        }
        context.Users.update(user1);
        context.Users.update(user2);
        logger.info("user "+user1.getUserName()+" deleted "+user2.getUserName()+"'s request");

    }

    private void deleteAndInform(){
        User user1=context.Users.get(MainController.currentUser.getId());
        User user2=context.Users.get(requester.getId());
        for (int i=0;i<user1.getRequests().size();i++){
            if (user1.getRequests().get(i).equals(user2.getId())) {
                user1.getRequests().remove(i);
                user2.getNotifications().add(user1.getUserName() + showProfileTexts.getDeleteRequest());
                user2.getMyRequests().add(user1.getUserName()+showProfileTexts.getDeleteRequest());
            }
        }
        context.Users.update(user1);
        context.Users.update(user2);
        logger.info("user "+user1.getUserName()+" deleted "+user2.getUserName()+"'s request");

    }



}

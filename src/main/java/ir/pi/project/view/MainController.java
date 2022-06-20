package ir.pi.project.view;

import ir.pi.project.config.addresses.FXMLConfig;
import ir.pi.project.controller.*;
import ir.pi.project.controller.entering.LogInLogic;
import ir.pi.project.controller.entering.SignUpLogic;
import ir.pi.project.controller.entering.WelcomeLogic;
import ir.pi.project.controller.explorer.ExplorerPageLogic;
import ir.pi.project.controller.explorer.ShowProfileImageLogic;
import ir.pi.project.controller.explorer.ShowProfileLogic;
import ir.pi.project.controller.messages.SavedMessagesLogic;
import ir.pi.project.controller.messages.direct.DirectChatsPageLogic;
import ir.pi.project.controller.messages.MessagesPageLogic;
import ir.pi.project.controller.messages.direct.ShowDirectChatLogic;
import ir.pi.project.controller.messages.groupChat.GroupChatsPageLogic;
import ir.pi.project.controller.messages.groupChat.NewGroupChatPageLogic;
import ir.pi.project.controller.messages.groupChat.ShowGroupChatLogic;
import ir.pi.project.controller.messages.multipleMessaging.EditGroupPageLogic;
import ir.pi.project.controller.messages.multipleMessaging.MultipleToGroupsLogic;
import ir.pi.project.controller.messages.multipleMessaging.ShowGroupsPageLogic;
import ir.pi.project.controller.messages.multipleMessaging.MultipleToUsersLogic;
import ir.pi.project.controller.myPage.*;
import ir.pi.project.controller.myPage.lists.blacklist.BlacklistLogic;
import ir.pi.project.controller.myPage.lists.followers.FollowersListLogic;
import ir.pi.project.controller.myPage.lists.followings.FollowingsListLogic;
import ir.pi.project.controller.myPage.lists.requests.RequestsListLogic;
import ir.pi.project.controller.setting.DeleteAccountPageLogic;
import ir.pi.project.controller.setting.PrivacySettingsLogic;
import ir.pi.project.controller.setting.SettingsLogic;
import ir.pi.project.model.User;
import ir.pi.project.view.myPage.MyPagePageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class MainController {
    static private final Logger logger= LogManager.getLogger(MainController.class);
    private static FXMLConfig fxmlConfig;

    {
        try {
            fxmlConfig = new FXMLConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User currentUser;
    public static Stage stage;
    public static Stack<Scene> scenes=new Stack<>();

    public static void addScene(Scene scene){
        scenes.push(scene);
    }

    public static void back(){
        stage.setScene(scenes.pop());
        stage.setScene(scenes.peek());
    }

    public static FXMLConfig getFXMLConfig(){
        try {
            return new FXMLConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void loadWelcome() throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getWelcomePage()));
        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user entered welcomePage");

        new WelcomeLogic(fxmlLoader.getController());
    }

    public static void loadSignUpPage() throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getSignUpPage()));
        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user entered signUpPage");

        new SignUpLogic(fxmlLoader.getController());

    }
    public static void loadLogInPage() throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getLogInPage()));
        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user entered logInPage");

        new LogInLogic(fxmlLoader.getController());

    }
    public static void loadMainMenu() throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getMainMenu()));
        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user entered MainMenu");


        new MainMenuLogic(fxmlLoader.getController());


    }

    public static void loadMyPagePage() throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getMyPagePage()));
        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user entered myPage");

        new MyPagePageLogic(fxmlLoader.getController());
    }


    public static void loadEditInfoPage(MyPagePageController myPagePageController) throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getEditInfoPage()));
        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user entered editInfoPage");

        new EditInfoLogic(fxmlLoader.getController(),myPagePageController);

    }
    public static void loadSettingsPage() throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getSettingsPage()));
        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user entered settingsPage");

        new SettingsLogic(fxmlLoader.getController());

    }
    public static void loadPrivacySettingsPage() throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getPrivacySettingsPage()));
        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user entered PrivacySettingsPage");


        new PrivacySettingsLogic(fxmlLoader.getController());

    }
    public static void loadTweets(List<Integer> tweets) throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        System.out.println("hii");
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getTweetComponent()));
        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user loaded some tweets");


        new ShowTweetLogic(fxmlLoader.getController(),tweets);

    }

    public static void loadExplorer() throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getExplorerPage()));
        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user entered explorerPage");


        new ExplorerPageLogic(fxmlLoader.getController());
    }

    public static void showProfile(User user) throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getProfilePage()));
        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);

        logger.info("user entered "+user.getUserName()+"'s profile");


        new ShowProfileLogic(fxmlLoader.getController(),user);
    }
    public static void showProfileImage(User user) throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getShowProfileImage()));
        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);

        logger.info("user opened "+user.getUserName()+"'s profile image");


        new ShowProfileImageLogic(fxmlLoader.getController(),user);
    }

    public static void loadFollowersListPage(MyPagePageController myPagePageController) throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getFollowersListPage()));

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user opened followersList");



        new FollowersListLogic(fxmlLoader.getController(),myPagePageController);

    }

    public static void loadFollowingsPage(MyPagePageController myPagePageController) throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getFollowingsListPage()));

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user opened followingsList");



        new FollowingsListLogic(fxmlLoader.getController(),myPagePageController);

    }
    public static void loadRequestsPage(MyPagePageController myPagePageController) throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getRequestsListPage()));

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user opened requestsList");


        new RequestsListLogic(fxmlLoader.getController(),myPagePageController);

    }
    public static void loadBlacklistPage(MyPagePageController myPagePageController) throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getBlacklistPage()));

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);

        logger.info("user opened blackList");



        new BlacklistLogic(fxmlLoader.getController(),myPagePageController);

    }


    public static void loadNotificationsPage() throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getNotificationsPage()));

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        scene.getStylesheets().add(MainController.class.getResource("/CSS/textAreaTransparentBackground.css").toExternalForm());
        stage.setScene(scene);
        addScene(scene);
        logger.info("user opened notificationsList");


        new NotificationsPageLogic(fxmlLoader.getController());
    }
    public static void loadPendingsPage() throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getMyRequestsPage()));

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        scene.getStylesheets().add(MainController.class.getResource("/CSS/textAreaTransparentBackground.css").toExternalForm());
        stage.setScene(scene);
        addScene(scene);
        logger.info("user opened pendingsList");


        new PendingsListLogic(fxmlLoader.getController());
    }

      public static void loadMessagesPage() throws IOException {
          FXMLConfig fxmlConfig=getFXMLConfig();
          assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getMessagesPage()));

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
          logger.info("user entered messagesPage");


          new MessagesPageLogic(fxmlLoader.getController());
    }
    public static void loadDirectChatsPage() throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getDirectChatsPage()));

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user entered directChatsPage");

        new DirectChatsPageLogic(fxmlLoader.getController());
    }
    public static void showDirectChatPage(List<Integer> chat, int userId, int user1Id) throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getShowChat()));

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user opened directChat with user with id: "+user1Id);

        new ShowDirectChatLogic(fxmlLoader.getController(),chat,userId,user1Id);
    }
    public static void showGroupChatPage(Integer groupChatId) throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getShowChat()));

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user opened groupChat with id: "+groupChatId);


        new ShowGroupChatLogic(fxmlLoader.getController(),groupChatId);
    }



    public static void loadGroupChatsPage() throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getGroupChatsPage()));

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user entered groupChatsPage");


        new GroupChatsPageLogic(fxmlLoader.getController());
    }

    public static void loadNewGroupChatPage() throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getNewGroupChatPage()));

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user entered newGroupChat Page");


        new NewGroupChatPageLogic(fxmlLoader.getController());
    }

    public static void loadMultipleToUsersPage() throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getMultipleToUsersPage()));

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user entered multipleMessagingToUsers");


        new MultipleToUsersLogic(fxmlLoader.getController());
    }
        public static void loadMultipleToGroupsPage() throws IOException {
            FXMLConfig fxmlConfig=getFXMLConfig();
            assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getMultipleToGroupsPage()));

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
            logger.info("user entered multipleMessagingToGroups");


            new MultipleToGroupsLogic(fxmlLoader.getController());
    }
    public static void loadEditGroupPage(Integer groupId) throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getEditGroupPage()));

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user entered editGroupPage");


        new EditGroupPageLogic(fxmlLoader.getController(),groupId);
    }
    public static void loadShowGroupsPage() throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getShowGroupsPage()));

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user entered groupsList");

        new ShowGroupsPageLogic(fxmlLoader.getController());
    }


    public static void showSavedMessages() throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getShowChat()));

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user opened savedMessages");

        new SavedMessagesLogic(fxmlLoader.getController());
    }
    public static void loadDeleteAccountPage() throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getDeleteAccountPage()));

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user entered deleteAccountPage");


        new DeleteAccountPageLogic(fxmlLoader.getController());
    }
    public static void loadForwardTweetPage(int tweetId) throws IOException {
        FXMLConfig fxmlConfig=getFXMLConfig();
        assert fxmlConfig != null;
        FXMLLoader fxmlLoader=new FXMLLoader(MainController.class.getResource(fxmlConfig.getForwardTweetPage()));

        Parent root=fxmlLoader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        addScene(scene);
        logger.info("user entered forwardTweetPage");


        new ForwardTweetPageLogic(fxmlLoader.getController(),tweetId);
    }











}

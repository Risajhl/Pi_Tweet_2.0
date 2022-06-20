package ir.pi.project.config.addresses;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class FXMLConfig {
    AddressesConfig addressesConfig=new AddressesConfig();

    private String mainMenu,tweetComponent, logInPage,signUpPage, welcomePage,
            explorerPage, profilePage, messagesPage, directChatBarComponent,showProfileImage,
            directChatsPage, groupChatBarComponent,groupChatsPage, newGroupChatPage,
            editGroupPage,multipleToGroupsPage,multipleToUsersPage,showGroupsPage,
            blacklistComponent,blacklistPage,followersComponent,followersListPage,
            followingsComponent,followingsListPage,requestsComponent,requestsListPage,
            editInfoPage,forwardTweetPage,myPagePage,myRequestsPage,settingsPage,
            notificationsPage,deleteAccountPage, privacySettingsPage,showChat;

    public FXMLConfig() throws IOException {
        setProperties();
    }

    private void setProperties() throws IOException {
        Properties properties = new Properties();
        FileReader fileReader = new FileReader(addressesConfig.getFxml());
        properties.load(fileReader);
        mainMenu = (String) properties.get("mainMenu");
        tweetComponent = (String) properties.get("tweetComponent");
        logInPage = (String) properties.get("logInPage");
        signUpPage = (String) properties.get("signUpPage");
        welcomePage = (String) properties.get("welcomePage");
        explorerPage = (String) properties.get("explorerPage");
        profilePage = (String) properties.get("profilePage");
        showProfileImage = (String) properties.get("showProfileImage");
        messagesPage = (String) properties.get("messagesPage");
        directChatBarComponent = (String) properties.get("directChatBarComponent");
        directChatsPage = (String) properties.get("directChatsPage");
        groupChatBarComponent = (String) properties.get("groupChatBarComponent");
        groupChatsPage = (String) properties.get("groupChatsPage");
        newGroupChatPage = (String) properties.get("newGroupChatPage");
        editGroupPage = (String) properties.get("editGroupPage");
        multipleToGroupsPage = (String) properties.get("multipleToGroupsPage");
        multipleToUsersPage = (String) properties.get("multipleToUsersPage");
        showGroupsPage = (String) properties.get("showGroupsPage");
        blacklistComponent = (String) properties.get("blacklistComponent");
        blacklistPage = (String) properties.get("blacklistPage");
        followersComponent = (String) properties.get("followersComponent");
        followersListPage = (String) properties.get("followersListPage");
        followingsComponent = (String) properties.get("followingsComponent");
        followingsListPage = (String) properties.get("followingsListPage");
        requestsComponent = (String) properties.get("requestsComponent");
        requestsListPage = (String) properties.get("requestsListPage");
        editInfoPage = (String) properties.get("editInfoPage");
        forwardTweetPage = (String) properties.get("forwardTweetPage");
        myPagePage = (String) properties.get("myPagePage");
        myRequestsPage = (String) properties.get("myRequestsPage");
        notificationsPage = (String) properties.get("notificationsPage");
        deleteAccountPage = (String) properties.get("deleteAccountPage");
        privacySettingsPage = (String) properties.get("privacySettingsPage");
        settingsPage = (String) properties.get("settingsPage");
        showChat = (String) properties.get("showChat");
    }

    public AddressesConfig getAddressesConfig() { return addressesConfig; }

    public String getMainMenu() { return mainMenu; }

    public String getTweetComponent() { return tweetComponent; }

    public String getLogInPage() { return logInPage; }

    public String getSignUpPage() { return signUpPage; }

    public String getWelcomePage() { return welcomePage; }

    public String getExplorerPage() { return explorerPage; }

    public String getProfilePage() { return profilePage; }
    public String getShowProfileImage() { return showProfileImage; }

    public String getMessagesPage() { return messagesPage; }

    public String getDirectChatBarComponent() { return directChatBarComponent; }

    public String getDirectChatsPage() { return directChatsPage; }

    public String getGroupChatBarComponent() { return groupChatBarComponent; }

    public String getGroupChatsPage() { return groupChatsPage; }

    public String getNewGroupChatPage() { return newGroupChatPage; }

    public String getEditGroupPage() { return editGroupPage; }

    public String getMultipleToGroupsPage() { return multipleToGroupsPage; }

    public String getMultipleToUsersPage() { return multipleToUsersPage; }

    public String getShowGroupsPage() { return showGroupsPage; }

    public String getBlacklistComponent() { return blacklistComponent; }

    public String getBlacklistPage() { return blacklistPage; }

    public String getFollowersComponent() { return followersComponent; }

    public String getFollowersListPage() { return followersListPage; }

    public String getFollowingsComponent() { return followingsComponent; }

    public String getFollowingsListPage() { return followingsListPage; }

    public String getRequestsComponent() { return requestsComponent; }

    public String getRequestsListPage() { return requestsListPage; }

    public String getEditInfoPage() { return editInfoPage; }

    public String getForwardTweetPage() { return forwardTweetPage; }

    public String getMyPagePage() { return myPagePage; }

    public String getMyRequestsPage() { return myRequestsPage; }

    public String getNotificationsPage() { return notificationsPage; }

    public String getDeleteAccountPage() { return deleteAccountPage; }

    public String getPrivacySettingsPage() { return privacySettingsPage; }

    public String getSettingsPage() { return settingsPage; }

    public String getShowChat() { return showChat; }
}

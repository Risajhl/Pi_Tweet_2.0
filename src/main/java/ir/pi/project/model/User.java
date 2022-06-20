package ir.pi.project.model;


import ir.pi.project.config.addresses.InfoConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.*;

public class User extends Model {

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String birthDate;
    private String email;
    private String phoneNumber;
    private String biography;
    private String lastSeen;
    private String lastSeenState;

    private Integer image;


    private List<Integer> tweets;
    private List<Integer> retweets;
    private List<Integer> likedTweets;
    private List<Integer> followers;
    private List<Integer> followings;
    private List<Integer> blackList;
    private List<Integer> muted;
    private boolean isOnline;
    private boolean isActive;
    private boolean isPublic;
    private boolean EPBCanSee;
    private List<Integer> savedMessages;
    private List<Integer> savedTweets;
    private List<Integer> requests;
    private List<String> myRequests;
    private List<String> notifications;
    private List<List<Integer>> chats;
    private List<List<Integer>> unReadChats;
    private HashMap<String, Integer> unReadUsernames;
    private List<String> unRead;
    private List<Integer> groups;

    private List<Integer> groupChats;
    private HashMap<Integer,Integer> unreadGroupChats;



    public User(String firstName, String lastName, String userName, String password, String email) {
//        try {
//            File lastId = new File("./src/main/resources/lastId");
//            Scanner sc = new Scanner(lastId);
//            int q = sc.nextInt();
//            this.id = q;
            super();
            this.firstName = firstName;
            this.lastName = lastName;
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.lastSeenState = "EveryOne";
            this.isActive = true;
            this.isPublic = true;
            this.tweets = new ArrayList<>();
            this.retweets = new ArrayList<>();
            this.likedTweets = new ArrayList<>();
            this.followers = new ArrayList<>();
            this.followings = new ArrayList<>();
            this.blackList = new ArrayList<>();
            this.muted = new ArrayList<>();
            this.notifications = new ArrayList<>();
            this.requests = new ArrayList<>();
            this.chats = new ArrayList<>();
            this.unReadChats = new ArrayList<>();
            this.unReadUsernames = new HashMap<>();
            this.unRead = new ArrayList<>();
            this.savedMessages = new ArrayList<>();
            this.savedTweets=new ArrayList<>();
            this.groups = new ArrayList<>();
            this.myRequests=new ArrayList<>();
            this.groupChats=new ArrayList<>();
            this.unreadGroupChats=new HashMap<>();

//            FileOutputStream fout = new FileOutputStream(lastId, false);
//
//            PrintStream out = new PrintStream(fout);
//            q++;
//            out.println(q);
//            out.flush();
//            out.close();
//
//
//        } catch (FileNotFoundException e) {
//            logger.warn("New user could not be made");
//            e.printStackTrace();
//        }
    }


    public boolean isOnline() {
        return isOnline;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getBiography() {
        return biography;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getFollowers() {
        return followers;
    }

    public List<Integer> getFollowings() {
        return followings;
    }

    public List<Integer> getBlackList() {
        return blackList;
    }

    public List<Integer> getMuted() {
        return muted;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public List<Integer> getTweets() {
        return tweets;
    }

    public List<Integer> getRequests() {
        return requests;
    }

    public List<String> getMyRequests() {
        return myRequests;
    }

    public List<Integer> getLikedTweets() {
        return likedTweets;
    }

    public List<Integer> getRetweets() {
        return retweets;
    }

    public List<List<Integer>> getChats() {
        return chats;
    }


    public Map<String, Integer> getUnReadUsernames() {
        return unReadUsernames;
    }

    public List<String> getUnRead() {
        return unRead;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public List<Integer> getSavedMessages() {
        return savedMessages;
    }

    public List<Integer> getSavedTweets() {
        return savedTweets;
    }

    public List<Integer> getGroups() {
        return groups;
    }

    public String getLastSeenState() {
        return lastSeenState;
    }

    public List<Integer> getGroupChats() {
        return groupChats;
    }

    public HashMap<Integer, Integer> getUnreadGroupChats() {
        return unreadGroupChats;
    }

    public boolean isEPBCanSee() {
        return EPBCanSee;
    }

    public void setEPBCanSee(boolean EPBCanSee) {
        this.EPBCanSee = EPBCanSee;
    }

    public void setLastSeenState(String lastSeenState) {
        this.lastSeenState = lastSeenState;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }


    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
package ir.pi.project.db;

import ir.pi.project.model.*;
import javafx.scene.image.Image;

public class Context {
    public DBSet<User> Users = new UserDB();
    public DBSet<Tweet> Tweets=new TweetDB();
    public DBSet<Message> Messages=new MessageDB();
    public DBSet<GroupChat> GroupChats=new GroupChatDB();
    public DBSet<Group> Groups=new GroupDB();
    public DBSet<Image> Images=new ImageDB();

}

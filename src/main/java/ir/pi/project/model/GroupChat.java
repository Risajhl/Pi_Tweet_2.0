package ir.pi.project.model;

import java.util.ArrayList;
import java.util.List;

public class GroupChat extends Model{
    String groupName;
    List<Integer> members;
    List<Integer> messages;
    public GroupChat(String groupName,User groupMaker){
        super();
        this.groupName=groupName;
        this.members=new ArrayList<>();
        this.messages=new ArrayList<>();


    }

    public String getGroupName() {
        return groupName;
    }

    public List<Integer> getMembers() {
        return members;
    }

    public List<Integer> getMessages() {
        return messages;
    }
}

package ir.pi.project.event;

import java.util.EventObject;

public class NewMessageEvent extends EventObject {


    String text;
    int senderId;
    int receiverId;
    public NewMessageEvent(Object source,String text,int senderId,int receiverId) {
        super(source);
        this.text=text;
        this.senderId=senderId;
        this.receiverId=receiverId;
    }


}

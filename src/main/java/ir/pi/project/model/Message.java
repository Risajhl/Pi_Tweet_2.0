package ir.pi.project.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Message extends Model{
    static private final Logger logger= LogManager.getLogger(Message.class);

    private int senderId;
    private int receiverId;
    private Integer image;
    private String text;
    private LocalDateTime time;
    private boolean isForwarded;

    public Message(int senderId,int receiverId, String text){

        super();
            this.senderId=senderId;
            this.receiverId=receiverId;
            this.text = text;
            this.time = LocalDateTime.now();
            this.isForwarded=false;

    }


    public int getId() {
        return id;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isForwarded() {
        return isForwarded;
    }

    public void setForwarded(boolean forwarded) {
        isForwarded = forwarded;
    }
}

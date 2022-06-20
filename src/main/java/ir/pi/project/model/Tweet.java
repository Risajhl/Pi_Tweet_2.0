package ir.pi.project.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tweet extends Model {

    static private final Logger logger= LogManager.getLogger(Tweet.class);

//    int id;
    int writer;
    Integer image;
    String text;
    List<Integer> comments;
    int likesNum;
    LocalDateTime time;
    int reportedTimes;
    boolean isBanned;

    public Tweet(int userId, String text) {
//        try {
//            File lastId = new File("./src/main/resources/lastId");
//            Scanner sc = new Scanner(lastId);
//            int q = sc.nextInt();
//            this.id = q;
        super();
            this.writer = userId;
            this.text = text;
            this.comments = new ArrayList<>();
            this.likesNum = 0;
            this.time = LocalDateTime.now();
            this.reportedTimes=0;
            this.isBanned=false;

//            FileOutputStream fout = new FileOutputStream(lastId, false);
//
//            PrintStream out = new PrintStream(fout);
//            q++;
//            out.println(q);
//            out.flush();
//            out.close();
//        }
//        catch (FileNotFoundException e){
//            logger.warn("New tweet could not be made");
//            e.printStackTrace();
//        }
    }

    public int getId() {
        return id;
    }

    public int getWriter() {
        return writer;
    }

    public String getText() {
        return text;
    }

    public int getLikesNum() {
        return likesNum;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public List<Integer> getComments() {
        return comments;
    }


    public void addToLikeNums(int n){
        this.likesNum=likesNum+n;
    }
    public void addToReportedTimes(){
        this.reportedTimes++;
    }

    public int getReportedTimes() {
        return reportedTimes;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public Integer getImage() {
        return this.image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

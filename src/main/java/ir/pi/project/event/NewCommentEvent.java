package ir.pi.project.event;

import ir.pi.project.model.Tweet;
import ir.pi.project.model.User;

import java.util.EventObject;

public class NewCommentEvent extends EventObject {
    String text;
    User writer;
    Tweet tweet;
    public NewCommentEvent(Object source, String text, User writer, Tweet tweet) {
        super(source);
        this.text=text;
        this.tweet=tweet;
        this.writer=writer;
    }

    public String getText() {
        return text;
    }

    public User getWriter() {
        return writer;
    }

    public Tweet getTweet() {
        return tweet;
    }
}

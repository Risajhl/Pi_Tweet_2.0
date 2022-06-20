package ir.pi.project.event;

import ir.pi.project.listener.StringListener;

import java.util.EventObject;

public class SearchUserEvent extends EventObject {

    String username;

    public SearchUserEvent(Object source,String username) {
        super(source);
        this.username=username;
    }

    public String getUsername() {
        return username;
    }
}

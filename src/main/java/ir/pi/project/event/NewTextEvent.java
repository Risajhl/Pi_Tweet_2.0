package ir.pi.project.event;

import java.util.EventObject;

public class NewTextEvent extends EventObject {
    private String text;

    public NewTextEvent(Object source, String text) {
        super(source);
        this.text=text;

    }

    public String getText() {
        return text;
    }

}

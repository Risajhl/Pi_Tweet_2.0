package ir.pi.project.listener;

import ir.pi.project.event.NewCommentEvent;

public interface NewCommentListener {
    void eventOccurred(NewCommentEvent newCommentEvent);
}

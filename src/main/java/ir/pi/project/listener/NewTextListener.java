package ir.pi.project.listener;

import ir.pi.project.event.NewTextEvent;

public interface NewTextListener {
    void eventOccurred(NewTextEvent newTextEvent);
}

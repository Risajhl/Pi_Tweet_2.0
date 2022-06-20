package ir.pi.project.listener;

import ir.pi.project.event.LogInFormEvent;
import ir.pi.project.event.SignUpFormEvent;

public interface LogInFormListener {
    void eventOccurred(LogInFormEvent logInFormEvent);
}

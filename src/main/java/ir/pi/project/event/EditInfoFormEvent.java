package ir.pi.project.event;

import java.util.EventObject;

public class EditInfoFormEvent extends EventObject {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String phoneNumber;
    private String birthDate;
    private String biography;

    public EditInfoFormEvent(Object source,
                             String firstName,
                             String lastName,
                             String userName,
                             String email,
                             String phoneNumber,
                             String birthDate,
                             String biography) {
        super(source);
        this.firstName=firstName;
        this.lastName=lastName;
        this.userName=userName;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.birthDate=birthDate;
        this.biography=biography;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getBiography() {
        return biography;
    }
}

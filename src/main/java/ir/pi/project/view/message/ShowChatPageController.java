package ir.pi.project.view.message;

import ir.pi.project.config.others.ShowChatConfig;
import ir.pi.project.config.texts.messages.MessagesText;
import ir.pi.project.event.NewTextEvent;
import ir.pi.project.listener.MessageListener;
import ir.pi.project.listener.NewTextListener;
import ir.pi.project.listener.StringListener;
import ir.pi.project.view.MainController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowChatPageController implements Initializable {

    private MessagesText messagesText;

    {
        try {
            messagesText = new MessagesText();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    NewTextListener newMessageListener;
    NewTextListener editMessageListener;
    StringListener chatListener;
    MessageListener messageButtonListener;
    private ShowChatConfig showChatConfig;

    {
        try {
            showChatConfig = new ShowChatConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    VBox content;

    @FXML
    ScrollPane scroller;

    @FXML
    TextArea messageField;

    @FXML
    Label chatNameLabel,errorLabel;

    @FXML
    Button editButton,sendButton,imageButton;


    @FXML
    Rectangle imageRect;

    public void setNewMessageListener(NewTextListener newMessageListener) {
        this.newMessageListener = newMessageListener;
    }

    public void setChatListener(StringListener chatListener) {
        this.chatListener = chatListener;
    }

    public void setMessageButtonListener(MessageListener messageButtonListener) {
        this.messageButtonListener = messageButtonListener;
    }

    public void setEditMessageListener(NewTextListener editMessageListener) {
        this.editMessageListener = editMessageListener;
    }

    public void send(){
        if(!messageField.getText().isEmpty() && messageField.getText()!=null && !messageField.getText().equals("")) {
            NewTextEvent newMessageEvent = new NewTextEvent(this, messageField.getText());
            newMessageListener.eventOccurred(newMessageEvent);
        }
        else {
            setErrorLabel(messagesText.getEmptyText(),true);
        }
    }

    public void edit() {
        if (!messageField.getText().isEmpty() && messageField.getText() != null && !messageField.getText().equals("")) {
            NewTextEvent editMessageEvent = new NewTextEvent(this, messageField.getText());
            editMessageListener.eventOccurred(editMessageEvent);
        }
    }

    public void uploadImage(){
        chatListener.stringEventOccurred("UploadImage");
    }

    public void setImage(Image image){
        if (image==null) {
            imageRect.setFill(null);
            imageRect.setVisible(false);
        }
        else {
            imageRect.setFill(new ImagePattern(image));
            imageRect.setVisible(true);
        }
    }


    public void empty(){
        content.getChildren().clear();
    }

    public void setName(String name){
        chatNameLabel.setText(name);
    }

    public void clearTextField(){
        messageField.setText(null);
    }


    public void showMyMessage(String string, String time, Image image,int id) {
            if (string.charAt(string.length() - 1) == '\n') string = string.substring(0, string.length() - 1);

            Rectangle imageRect1 = new Rectangle();
            imageRect1.setX(290);
            imageRect1.setY(0);
            imageRect1.setWidth(300);
            imageRect1.setHeight(300);

            AnchorPane root = new AnchorPane();
            root.setPrefWidth(680);
            root.setPrefHeight(10);

            TextFlow textFlow = new TextFlow();
            textFlow.setLayoutX(290);


            Label label = new Label();

            Text text = new Text(string);
            label.setText(text.getText());


            label.setBackground(new Background(new BackgroundFill(
                    Color.rgb(showChatConfig.getMyMessageR(), showChatConfig.getMyMessageG(), showChatConfig.getMyMessageB()),
                    new CornerRadii(5.0), new Insets(-5, -5, -5, -5))));
            label.setWrapText(true);
            label.setPrefWidth(showChatConfig.getLabelWidth());

            textFlow.getChildren().add(label);

            root.getChildren().add(textFlow);
            int lines = (int) text.getLayoutBounds().getWidth() / (showChatConfig.getLabelWidth()-10);
            double n = text.getLayoutBounds().getHeight() + (lines * 15);

            int imageWidth = 0;
            if (image != null) {
                imageRect1.setVisible(true);
                imageRect1.setFill(new ImagePattern(image));
                imageRect1.setX(290);
                imageRect1.setY(0);
                imageRect1.setWidth(300);
                imageRect1.setHeight(300);
                imageWidth = 300;
                root.getChildren().add(imageRect1);

            }

            textFlow.setLayoutY(12 + imageWidth);
            root.setPrefHeight(n + 40 + imageWidth);



            Button saveButton = new Button();
            saveButton.setLayoutY(n + 20 + imageWidth);
            saveButton.setLayoutX(280);
            saveButton.setPrefHeight(20);
            saveButton.setPrefWidth(70);
            saveButton.setText("Save");
            saveButton.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0), new CornerRadii(5.0), new Insets(0, 0, 0, 0))));
            saveButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    messageButtonListener.eventOccurred(id,"Save");
                }
            });
            root.getChildren().add(saveButton);


            Button editButton = new Button();
            editButton.setLayoutY(n + 20 + imageWidth);
            editButton.setLayoutX(340);
            editButton.setPrefHeight(showChatConfig.getButtonHeight());
            editButton.setPrefWidth(showChatConfig.getButtonWidth());
            editButton.setText("Edit");
            editButton.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0), new CornerRadii(5.0), new Insets(0, 0, 0, 0))));
            editButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    messageButtonListener.eventOccurred(id,"Edit");
                }
            });
            root.getChildren().add(editButton);


            Button deleteButton = new Button();
            deleteButton.setLayoutY(n + 20 + imageWidth);
            deleteButton.setLayoutX(400);
            deleteButton.setPrefHeight(showChatConfig.getButtonHeight());
            deleteButton.setPrefWidth(showChatConfig.getButtonWidth());
            deleteButton.setText("Delete");
            deleteButton.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0), new CornerRadii(5.0), new Insets(0, 0, 0, 0))));
            deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    messageButtonListener.eventOccurred(id,"Delete");
                }
            });
            root.getChildren().add(deleteButton);

            Label dateAndTime = new Label();
            dateAndTime.setText(time);
            dateAndTime.setLayoutY(n + 25 + imageWidth);
            dateAndTime.setLayoutX(470);

            root.getChildren().add(dateAndTime);

            content.getChildren().add(root);
            jumpToBottom();


    }


    public void showOthersMessage(String string,String time,String username,Image image,Image profileImage,int id){

        if(string.charAt(string.length()-1)=='\n')string=string.substring(0,string.length()-1);
        AnchorPane root = new AnchorPane();
        Rectangle imageRect1 = new Rectangle();
        imageRect1.setX(12);
        imageRect1.setY(20);
        imageRect1.setWidth(300);
        imageRect1.setHeight(300);

        Circle circle=new Circle(15,15,15);
        circle.setLayoutX(7);
        circle.setLayoutY(3);
        root.getChildren().add(circle);

        if(profileImage!=null){
            circle.setFill(new ImagePattern(profileImage));
        }

        root.setPrefWidth(600);
        root.setPrefHeight(10);

        Label usernameLabel=new Label();
        usernameLabel.setText(username);
        usernameLabel.setLayoutX(40);
        usernameLabel.setLayoutY(10);
        root.getChildren().add(usernameLabel);


        TextFlow textFlow=new TextFlow();
        textFlow.setLayoutX(12);



        Label label=new Label();


        Text text=new Text(string);
        label.setText(text.getText());


        label.setBackground(new Background(new BackgroundFill(Color.rgb(showChatConfig.getOthersMessageR(), showChatConfig.getOthersMessageG(), showChatConfig.getOthersMessageB()),
                new CornerRadii(5.0), new Insets(-5,-5,-5,-5))));
        label.setWrapText(true);
        label.setPrefWidth(showChatConfig.getLabelWidth());

        root.setPrefHeight(label.getPrefHeight()+40);
        textFlow.getChildren().add(label);

        root.getChildren().add(textFlow);
        int lines=(int)text.getLayoutBounds().getWidth()/showChatConfig.getLabelWidth();
        double n=text.getLayoutBounds().getHeight()+(lines*15);

        int imageWidth = 0;
        if (image != null) {
            System.out.println(image);
            imageRect1.setVisible(true);
            imageRect1.setFill(new ImagePattern(image));
            imageRect1.setX(12);
            imageRect1.setY(40);
            imageRect1.setWidth(300);
            imageRect1.setHeight(300);
            imageWidth = 310;
            root.getChildren().add(imageRect1);
        }



        root.setPrefHeight(n+50+imageWidth);
        textFlow.setLayoutY(45+imageWidth);




        Button saveButton=new Button();
        saveButton.setLayoutY(n+50+imageWidth);
        saveButton.setLayoutX(250);
        saveButton.setPrefHeight(showChatConfig.getButtonHeight());
        saveButton.setPrefWidth(showChatConfig.getButtonWidth());
        saveButton.setText("Save");
        saveButton.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0), new CornerRadii(5.0), new Insets(0,0,0,0))));
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                messageButtonListener.eventOccurred(id,"Save");
            }
        });
        root.getChildren().add(saveButton);


        Label dateAndTime=new Label();
        dateAndTime.setText(time);
        dateAndTime.setLayoutY(n+55+imageWidth);
        dateAndTime.setLayoutX(12);

        root.getChildren().add(dateAndTime);

        content.getChildren().add(root);

        jumpToBottom();

    }

    public void setMessageFieldText(String text){
        messageField.setText(text);
    }
    public void setEditButton(boolean isVisible){
        editButton.setVisible(isVisible);
    }
    public void setSendButton(boolean isVisible){
        sendButton.setVisible(isVisible);
    }
    public void setImageButton(boolean isVisible){
        imageButton.setVisible(isVisible);
    }

    public void jumpToBottom(){
        scroller.setVvalue(scroller.getVmax());
    }

    public void setErrorLabel(String text,boolean isVisible){
        errorLabel.setVisible(isVisible);
        errorLabel.setText(text);
    }

    public void back(){
        MainController.back();
    }
    public void mainMenu(){
        try {
            MainController.loadMainMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
         this.content = new VBox(5);
        scroller.setContent(content);
        scroller.setFitToWidth(true);
        editButton.setVisible(false);
        errorLabel.setVisible(false);
        imageRect.setVisible(false);
    }
}

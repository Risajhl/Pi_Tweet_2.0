package ir.pi.project.controller.messages.groupChat;

import ir.pi.project.controller.MainLogic;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.GroupChat;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.message.groupChats.GroupChatsPageController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupChatsPageLogic extends MainLogic {
    GroupChatsPageController groupChatsPageController;
    public GroupChatsPageLogic(GroupChatsPageController groupChatsPageController){
        this.groupChatsPageController=groupChatsPageController;
        groupChatsPageController.setReloadListener(new StringListener() {
            @Override
            public void stringEventOccurred(String string) {
                if(string.equals("Reload"))
                    showChatBars();
            }
        });
        showChatBars();
    }


    private void showChatBars(){
        User user=context.Users.get(MainController.currentUser.getId());
        List<GroupChat> unreadGroupChats=new ArrayList<>();
        if (!user.getUnreadGroupChats().isEmpty()) {
            for (HashMap.Entry<Integer, Integer> entry : user.getUnreadGroupChats().entrySet()) {
                GroupChat groupChat=context.GroupChats.get(entry.getKey());
                unreadGroupChats.add(groupChat);
            }
        }

        List<GroupChat> groupChats=new ArrayList<>();
        if (!user.getGroupChats().isEmpty()) {
            for (Integer groupChatId :
                    user.getGroupChats()) {
                GroupChat groupChat=context.GroupChats.get(groupChatId);
                if(!isInList(unreadGroupChats,groupChatId)){
                    groupChats.add(groupChat);
                }
            }


            groupChatsPageController.update(context.Users.get(user.getId()).getUnreadGroupChats(),groupChats);
        }


    }


    private boolean isInList(List<GroupChat> unreadGroupChats, Integer groupId){
        for (int i=0;i<unreadGroupChats.size();i++){
            if(unreadGroupChats.get(i).getId()==groupId)return true;
        }
        return false;
    }

}

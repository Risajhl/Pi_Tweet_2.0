package ir.pi.project.controller.messages.groupChat;

import ir.pi.project.controller.MainLogic;
import ir.pi.project.listener.StringListener;
import ir.pi.project.model.GroupChat;
import ir.pi.project.model.User;
import ir.pi.project.view.MainController;
import ir.pi.project.view.message.groupChats.GroupChatBarComponentController;

public class GroupChatBarComponentLogic extends MainLogic {
    GroupChatBarComponentController groupChatBarComponentController;
    Integer groupChatId;
    public GroupChatBarComponentLogic(GroupChatBarComponentController groupChatBarComponentController,int num,Integer groupChatId){
        this.groupChatBarComponentController=groupChatBarComponentController;
        this.groupChatId=groupChatId;
        GroupChat groupChat=context.GroupChats.get(groupChatId);
        if(num!=-1){
            groupChatBarComponentController.updateNum(num,true);
        }else groupChatBarComponentController.updateNum(-1,false);
        groupChatBarComponentController.updateGroupName(groupChat.getGroupName());


        groupChatBarComponentController.setGroupChatBarListener(new StringListener() {
            @Override
            public void stringEventOccurred(String string) {
                if(string.equals("Show")){
                    openChat(groupChatId);
                }
            }
        });

    }


    private void openChat(Integer groupChatId){
        try {
            GroupChat groupChat = context.GroupChats.get(groupChatId);
            MainController.showGroupChatPage(groupChatId);
            User user=context.Users.get(MainController.currentUser.getId());
            user.getUnreadGroupChats().remove(groupChatId);
            context.Users.update(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}

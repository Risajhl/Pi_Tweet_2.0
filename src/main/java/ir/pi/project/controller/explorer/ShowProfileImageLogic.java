package ir.pi.project.controller.explorer;


import ir.pi.project.controller.MainLogic;
import ir.pi.project.model.User;
import ir.pi.project.view.explorer.ShowProfileImageController;

public class ShowProfileImageLogic extends MainLogic {
    ShowProfileImageController showProfileImageController;
    public ShowProfileImageLogic(ShowProfileImageController showProfileImageController,User user){
        this.showProfileImageController=showProfileImageController;
        if (user.getImage()!=null && user.getImage()!=0)
        showProfileImageController.show(context.Images.get(user.getImage()));
    }
}

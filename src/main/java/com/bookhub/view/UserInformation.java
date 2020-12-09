package com.bookhub.view;

import com.bookhub.model.User;

public class UserInformation {
    private User user;
    public UserInformation(User user){
        this.user=user;
    }
    public int getAuthority(){
        return user.getAuthority();
    }
    public String getName(){
        return user.getName();
    }
    public String getId(){
        return user.getId();
    }
    public String getGender() {return user.getGender();}
    public String getEmail(){ return user.getEmail();}
    public Boolean isActive()  { return user.isActive();}
}

package server.model;

import java.util.ArrayList;

public class UsersList
{
    private ArrayList<User> usersList;

    public UsersList(){
        this.usersList = new ArrayList<>();
    }
    public ArrayList<User> getUsersList(){
        return usersList;
    }
    public void addUser(User user){
        usersList.add(user);
    }
    public User getUserByUsername(String username){
        for(User user: usersList){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
    public int size(){
        return size();
    }

    public boolean equals(Object obj){
        if(!(obj instanceof UsersList))
            return false;
        UsersList other = (UsersList) obj;
        if(usersList.size() != other.size()){
            return false;
        }
        for(int index = 0; index < usersList.size(); index++){
            if(!usersList.get(index).equals(other.usersList.get(index))){
                return false;
            }
        }
        return true;
    }
    public String toString(){
        String totalUsers = "";
        for(User user: usersList){
            totalUsers += user + " ";
        }
        return totalUsers;
    }
}

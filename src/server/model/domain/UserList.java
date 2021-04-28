package server.model.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserList implements Serializable
{
    private List<User> userList;
    
    public UserList() {
        this.userList = new ArrayList<>();
    }
    
    public List<User> getUsersList() {
        return userList;
    }
    
    public void addUser(User user) {
        userList.add(user);
    }
    
    // Method compares using only the user's CPR
    public User getUserByCpr(String cpr) {
        for (User user : userList) {
            if (user.getCpr().equals(cpr)) {
                return user;
            }
        }
        return null;
    }
    
    public int size() {
        return userList.size();
    }
    
    public void remove(User user) {
        userList.remove(user);
    }
    
    // Method compares using only the user's CPR
    public boolean contains(String cpr) {
        for (User user : userList) {
            if (user.getCpr().equals(cpr)) {
                return true;
            }
        }
        return false;
    }
    
    // Method compares using only the user's CPR
    public boolean contains(User user) {
        for (User u : userList) {
            if (user.getCpr().equals(u.getCpr())) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UserList))
            return false;
        UserList other = (UserList) obj;
        if (userList.size() != other.size()) {
            return false;
        }
        for (int index = 0; index < userList.size(); index++) {
            if (!userList.get(index).equals(other.userList.get(index))) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        String totalUsers = "";
        for (User user : userList) {
            totalUsers += user + " ";
        }
        return totalUsers;
    }
}

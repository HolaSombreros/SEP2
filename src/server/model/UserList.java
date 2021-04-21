package server.model;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private ArrayList<User> userList;
    
    public UserList() {
        this.userList = new ArrayList<>();
    }
    
    public List<User> getUsersList() {
        return userList;
    }
    
    public void addUser(User user) {
        userList.add(user);
    }
    
    public User getUserByCpr(String cpr) {
        for (User user : userList) {
            if (user.getCpr().equals(cpr)) {
                return user;
            }
        }
        return null;
    }
    
    public List<User> getUserByName(String name) {
        List<User> users = new ArrayList<>();
        for (User user : userList) {
            if (user.getFullName().contains(name)) {
                users.add(user);
            }
        }
        return users;
    }
    
    public int size() {
        return userList.size();
    }
    
    public void remove(User user) {
        userList.remove(user);
    }
    
    public boolean contains(String cpr) {
        for (User user : userList) {
            if (user.getCpr().equals(cpr)) {
                return true;
            }
        }
        return false;
    }
    
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

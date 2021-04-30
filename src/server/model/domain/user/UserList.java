package server.model.domain.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserList implements Serializable
{
    private List<User> users;
    
    public UserList() {
        this.users = new ArrayList<>();
    }
    
    public List<User> getUsersList() {
        return users;
    }
    
    public void addUser(User user) {
        users.add(user);
    }
    
    // Method compares using only the user's CPR
    public User getUserByCpr(String cpr) {
        for (User user : users) {
            if (user.getCpr().equals(cpr)) {
                return user;
            }
        }
        return null;
    }
    
    public int size() {
        return users.size();
    }
    
    public void remove(User user) {
        users.remove(user);
    }
    
    // Method compares using only the user's CPR
    public boolean contains(String cpr) {
        for (User user : users) {
            if (user.getCpr().equals(cpr)) {
                return true;
            }
        }
        return false;
    }
    
    // Method compares using only the user's CPR
    public boolean contains(User user) {
        for (User u : users) {
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
        if (users.size() != other.users.size()) {
            return false;
        }
        for (int index = 0; index < users.size(); index++) {
            if (!users.get(index).equals(other.users.get(index))) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        String totalUsers = "";
        for (User user : users) {
            totalUsers += user + " ";
        }
        return totalUsers;
    }
}

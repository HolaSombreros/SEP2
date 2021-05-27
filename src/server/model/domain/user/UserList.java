package server.model.domain.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserList implements Serializable {
    private List<User> users;

    public UserList() {
        this.users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void add(User user) {
        users.add(user);
    }

    public void add(int index, User user) {
        if (index < 0 || index > users.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        users.add(index, user);
    }

    public User getUserByCpr(String cpr) {
        if (cpr == null || cpr.isEmpty()) {
            return null;
        }
        for (User user : users) {
            if (user.getCpr().equals(cpr)) {
                return user;
            }
        }
        return null;
    }

    public UserList getUsersByCprAndName(String criteria) {
        UserList userList = new UserList();
        if (criteria == null || criteria.isEmpty()) {
            return this;
        } else {
            criteria = criteria.trim().toLowerCase();
            for (User user : users) {
                if (user.getCpr().contains(criteria) || user.getFullName().toLowerCase().contains(criteria))
                    userList.add(user);
            }
            return userList;
        }
    }

    public int size() {
        return users.size();
    }

    public void remove(User user) {
        users.remove(user);
    }

    public void removeByCpr(String cpr) {
        for (int i = 0; i < users.size(); i++)
            if (users.get(i).getCpr().equals(cpr))
                remove(users.get(i));
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

    public Nurse getNurse(String cpr) {
        for (User user : users)
            if (user instanceof Nurse && user.getCpr().equals(cpr))
                return (Nurse) user;
        return null;
    }

    public Patient getPatient(String cpr) {
        for (User user : users)
            if (user instanceof Patient && user.getCpr().equals(cpr))
                return (Patient) user;
        return null;
    }

    public UserList getAdminList() {
        UserList list = new UserList();
        for (User admin : users)
            if (admin instanceof Administrator)
                list.add(admin);
        return list;
    }

    public UserList getNurseList() {
        UserList list = new UserList();
        for (User nurse : users)
            if (nurse instanceof Nurse)
                list.add(nurse);
        return list;
    }

    public UserList getPatientList() {
        UserList list = new UserList();
        for (User patient : users)
            if (patient instanceof Patient)
                list.add(patient);
        return list;
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
            totalUsers += user + " \n";
        }
        return totalUsers;
    }
}

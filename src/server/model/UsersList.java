package server.model;

import java.util.ArrayList;

public class UsersList
{
    private ArrayList<Patient> usersList;

    public UsersList(){
        this.usersList = new ArrayList<>();
    }
    public ArrayList<Patient> getUsersList(){
        return usersList;
    }
    public void addUser(Patient user){
        usersList.add(user);
    }
    public Patient getUserByCpr(String cpr){
        for(Patient user: usersList){
            if(user.getCpr().equals(cpr)){
                return user;
            }
        }
        return null;
    }
    /*public ArrayList<Patient> getPatientByName(String name){
        ArrayList<Patient> patients = new ArrayList<>();
        for(Patient patient : usersList){
            if(patient.getFullName().contains(name)){
                patients.add(patient);
            }
        }
        return patients;
    }*/
    public int size(){
        return size();
    }
    
    public boolean contains(Patient patient) {
        return usersList.contains(patient);
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
        for(Patient user: usersList){
            totalUsers += user + " ";
        }
        return totalUsers;
    }
}
package client.model;


import server.model.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class ModelManager implements Model
{
  private PropertyChangeSupport property;
  private UsersList patients;

  public ModelManager()
  {
    property = new PropertyChangeSupport(this);
    patients = new UsersList();
  }

  /**
   * @param cpr
   * @param address
   * @param email
   * @param firstName
   * @param lastName
   * @param middleName
   * @param password
   * @param phone
   * sends a newly created Patient object to the server
   * */
  @Override public void register(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email)
  {
    // TODO: Communicate with server
    patients.getUsersList().add(new Patient(cpr, password, firstName, middleName, lastName, address, phone, email));
    System.out.println("Registered patient!");
  }

  /**
   * @param password
   * @param cpr
   * checks in the server if a Patient object with the given cpr and password exist
   * if it doesn't exist throws an exception
   * */
  @Override
  public void login(String cpr, String password) {
      // TODO: Communicate with server
      if (cpr == null || password == null) {
        throw new IllegalArgumentException("Input cpr or password");
      }
      else {
        if(getPatientByCpr(cpr) instanceof Nurse || getPatientByCpr(cpr) instanceof Administrator)
          System.out.println("Logged in as a Nurse or Administrator!");
        else
          System.out.println("Logged in as a Patient!");
      }
  }
  
  @Override
  public UsersList getPatients() {
    return patients;
  }

  @Override
  public Patient getPatientByCpr(String CPR) {
    for(Patient patient : patients.getUsersList())
      if(patient.getCpr().equals(CPR))
        return patient;
    return null;
  }

  @Override public void addListener(PropertyChangeListener listener)
  {
    property.addPropertyChangeListener(listener);
  }

  @Override public void removeListener(PropertyChangeListener listener)
  {
    property.removePropertyChangeListener(listener);
  }

}

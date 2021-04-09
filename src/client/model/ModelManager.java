package client.model;


import server.model.Address;
import server.model.Patient;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ModelManager implements Model
{
  private PropertyChangeSupport property;
  private ArrayList<Patient> patients;

  public ModelManager()
  {
    property = new PropertyChangeSupport(this);
    patients = new ArrayList<>();
  }

  @Override public void register(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email)
  {
    //TODO check for CPR
    patients.add(new Patient(cpr, password, firstName, middleName, lastName, address, phone, email));
    System.out.println("Registered patient!");
  }

  @Override
  public ArrayList<Patient> getPatients() {
    return patients;
  }

  @Override
  public Patient getPatientByCpr(String CPR) {
    for(Patient patient : patients)
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

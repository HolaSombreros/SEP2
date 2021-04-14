package client.viewmodel;

import server.model.Patient;

public class ViewState {

    private Patient patient;

    public ViewState() {
        patient = null;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}

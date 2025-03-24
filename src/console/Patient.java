package console;

import java.time.LocalDate;

public class Patient extends Person{
    public int patientId;

    public Patient(String name, String surname, LocalDate dob, String telNo, int patientId) {
        super(name, surname, dob, telNo);
        this.patientId = patientId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}

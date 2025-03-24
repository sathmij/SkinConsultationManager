package console;

import java.time.LocalDate;

public class Doctor extends Person {
    private String licenseNo;
    private String specialisation;

    public Doctor(String name, String surname, LocalDate dob, String telNo, String licenseNo, String specialization) {
        super(name, surname, dob, telNo);
        this.licenseNo = licenseNo;
        this.specialisation =specialization;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    @Override
    public String toString() {
        return  "Name : " + getName() + '\n' +
                "Lastname : " + getSurname() + '\n' +
                "DOB : " + getDateOfBirth() + '\n' +
                "Mobile : " + getTelNo() + '\n' +
                "License : " + getLicenseNo() + '\n' +
                "Specialization : " + getSpecialisation() + '\n' ;
    }
}

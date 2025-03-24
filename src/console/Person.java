package console;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Scanner;

/*
 * console.Person is the super class for doctor and patient classes.
 * Implements serializable to convert its objects to stream of bits. (serialize)
 *
*/
public abstract class Person implements Serializable, Comparable<Person> {

    private static final long serialVersionUTD = 1L;
    private String name;
    private String surname;
    private LocalDate dob;
    private String telNo;
    private static final Scanner sc = new Scanner(System.in);

    public Person(String name, String surname, LocalDate dob, String telNo) {
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.telNo = telNo;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getDateOfBirth() {
        return dob;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    @Override
    public int compareTo(Person person) {
        return this.surname.compareToIgnoreCase(person.getSurname());
    }
}

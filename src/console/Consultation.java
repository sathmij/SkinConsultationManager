package console;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consultation {

    private int cost;
    private LocalDate date;
    private LocalTime time;
    private String notes;

    public Consultation(int cost, LocalDate date, LocalTime time, String notes) {
        this.cost = cost;
        this.date = date;
        this.time = time;
        this.notes = notes;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

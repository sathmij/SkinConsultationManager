package console;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Function;

public class WestminsterSkinConsultationManager implements SkinConsultationManager {

    private static final Scanner sc = new Scanner(System.in); // scanner object for this class
    private ArrayList<Doctor> doctors = new ArrayList<>();
    private static final int MAX_DOCTORS = 10;

    private final String[] SPECIALIZATIONS = {
            "Medical Dermatology",
            "Surgical Dermatology",
            "Pediatric Dermatology",
            "Cosmetic Dermatology",
            "Dermatopathology"
    };

    private static final Map<Integer, String> ERROR_MESSAGES = Map.of(
            1, "Name cannot contain numbers or special characters \n",
            2, "Surname cannot contain numbers or special characters \n",
            3, "Specialisation selection must be between 1 and 5 \n",
            4, "License number (7 characters) cannot contain special characters \n",
            5, "A doctor with the given license number already exists \n",
            6, "DOB should be in the format 'yyyy-MM-dd' \n",
            7, "Mobile number (10 digits) cannot contain letters or special characters \n"
    );

    public static void main(String[] args){
        WestminsterSkinConsultationManager wscm = new WestminsterSkinConsultationManager();
        wscm.loadData();
        wscm.start();
    }

    @Override
    public void displayMenu() {
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("                   Welcome to Westminster Consultation Centre                   ");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("                   1. Add a Doctor                   ");
        System.out.println("                   2. Delete a Doctor                  ");
        System.out.println("                   3. View the list of Doctors                   ");
        System.out.println("                   4. Save Data to a File                   ");
        System.out.println("                   5. View the GUI                  ");
        System.out.println("                   6. Exit                   ");
        System.out.println("----------------------------------------------------------------------------");
    }

    public void start() {
        while (true) {
            displayMenu();
            int choice = getValidChoice();
            switch (choice) {
                case 1 -> addDoctor();
                case 2 -> deleteDoctor();
                case 3 -> printDoctors();
                case 4 -> saveData();
                case 5 -> viewGUI();
                case 6 -> {
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.\n");
            }
        }
    }

    @Override
    public void addDoctor() {
        if (getSize() >= MAX_DOCTORS){
            System.out.println("Cannot exceed the maximum number of doctors.");
        }
        String fname = getValidatedInput("Enter first name: ", ERROR_MESSAGES.get(1), this::isNameValid);

        String sname = getValidatedInput("Enter surname: ", ERROR_MESSAGES.get(2), this::isNameValid);

        String specialisation = getSpecializationFromUser();

        String license = getLicenseFromUser();

        LocalDate dob = getValidDobFromUser();

        String mobile = getValidatedInput("Enter mobile number: ", ERROR_MESSAGES.get(7), this::isMobileValid);

        Doctor doctor = new Doctor(fname, sname, dob, mobile, license, specialisation);
        doctors.add(doctor);

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("                        Doctor added successfully!                          ");
        System.out.println("----------------------------------------------------------------------------");
        System.out.println(doctor);
        System.out.println("----------------------------------------------------------------------------\n");
    }

    @Override
    public void deleteDoctor(){
        Scanner scanner = new Scanner(System.in);
        if (doctors.isEmpty()) {
            System.out.println("There are no doctors added.");
            return;
        }

        System.out.println("Enter the medical license number of the doctor to delete: ");
        String license = scanner.nextLine().trim();
        Doctor deleteDoctor = null;
        for (Doctor doc : doctors) {
            if (doc.getLicenseNo().equalsIgnoreCase(license)) {
                deleteDoctor = doc;
                break;
            }
        }
        if(deleteDoctor!=null){
            doctors.remove(deleteDoctor);
            System.out.println("Doctor removed successfully!");
        }
        else
            System.out.println("No doctor found with the given license number.");
    }

    @Override
    public void printDoctors() {
        if (doctors.isEmpty()) {
            System.out.println("There are no doctors added.");
            return;
        }
        ArrayList<Doctor> doctorsCopy = (ArrayList<Doctor>) doctors.clone();
        Collections.sort(doctorsCopy);
        System.out.println("List of Doctors in the Consultation Centre");
        for (Doctor doc: doctors){
            System.out.println(doc);
        }
    }

    @Override
    public void saveData() {
        try{
            FileOutputStream fo = new FileOutputStream("doctors.ser");
            ObjectOutputStream os = new ObjectOutputStream(fo);
            os.writeObject(doctors);
            os.close();
            System.out.println("Doctor data was sucessfully saved to a file.");
        }
        catch(Exception e){
            System.out.println("Saving data to a file failed");
        }
    }

    @Override
    public void loadData() {
        try{
            FileInputStream fi = new FileInputStream("doctors.ser");
            ObjectInputStream os = new ObjectInputStream(fi);
            doctors = (ArrayList<Doctor>) os.readObject();
            os.close();
            System.out.println("Doctor data was sucessfully loaded.");
        }
        catch(FileNotFoundException fnf){
            System.out.println("File not found");
        }
        catch(Exception e){
            System.out.println("Saving data to a file failed");
        }
    }
    private int getValidChoice() {
        while (true) {
            System.out.print("Enter your choice: ");
            if (sc.hasNextInt()) return sc.nextInt();
            else {
                System.out.println("Your Input is Invalid");
                sc.next();
            }
        }
    }
    private String getSpecializationFromUser() {
        System.out.println("Select Specialization:");
        for (int i = 0; i < SPECIALIZATIONS.length; i++) {
            System.out.printf(i + 1 + "." + SPECIALIZATIONS[i] + "\n");
        }
        while (true) {
            int choice = getValidChoice();
            if (choice >= 1 && choice <= SPECIALIZATIONS.length) {
                return SPECIALIZATIONS[choice - 1];
            }

            System.out.println(ERROR_MESSAGES.get(3));
        }
    }

    private String getLicenseFromUser() {
        String license;
        while(true){
            license = getValidatedInput("Enter medical license number: ", ERROR_MESSAGES.get(4), this::isLicenseValid);
            if (!isDoctorAlreadyAdded(license)) {
                return license;
            }
            System.out.println(ERROR_MESSAGES.get(5));
        }
    }

    private LocalDate getValidDobFromUser() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dob = null;
        Scanner scanner = new Scanner(System.in);
        while (dob == null) {
            System.out.print("Enter DOB (yyyy-MM-dd): ");
            String inputDob = scanner.nextLine().trim();
            try {
                dob = LocalDate.parse(inputDob, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Enter a valid date in 'yyyy-MM-dd' format.");
            }
        }
        return dob;
    }


    /**
     * Helper method to get and validate user input.
     */
    private String getValidatedInput(String prompt, String errorMsg, Function<String, Boolean> validator) {
        String input;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!validator.apply(input)) {
                System.out.println(errorMsg + "Please try again.\n");
            }
        } while (!validator.apply(input));
        return input;
    }

    /**
     * Validation method for name
     */
    private boolean isNameValid(String name) {
        return name.matches("^[A-Za-z]{2,50}$"); // Only letters, 2-50 characters
    }

    private boolean isLicenseValid(String license) {
        return license.matches("^[A-Za-z0-9]{7}$"); // Letters & numbers, 7 characters
    }

    private boolean isMobileValid(String mobile) {
        return mobile.matches("^\\d{10}$"); // Exactly 10 digits
    }

    private boolean isDoctorAlreadyAdded(String license) {
        return doctors.stream().anyMatch(doc -> doc.getLicenseNo().equals(license));
    }


    private int getSize(){
        return doctors.size();
    }

    @Override
    public void viewGUI() {
    }



}

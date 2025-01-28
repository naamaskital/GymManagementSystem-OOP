package gym.customers;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Person {
    private final String name;
    private Balance bankAccount;
    private final Gender gender;
    private final String birthDate;
    private int id;
    private static int lastID = 1111;

    // Constructor to initialize the person with name, money, gender, and birthDate
    public Person(String name, int money, Gender gender, String birthDate) {
        this.name = name;
        this.bankAccount = new Balance(money);
        this.gender = gender;
        this.birthDate = birthDate;
        this.id = lastID;
        lastID++; // Increment lastID for the next person
    }

    // Copy constructor to create a new Person based on another Person object
    public Person(Person person) {
        this.name = person.name;
        this.bankAccount = person.bankAccount;
        this.gender = person.gender;
        this.birthDate = person.birthDate;
        this.id = person.id;
    }

    // Returns the name of the person
    public String getName() {
        return name;
    }

    // Returns the gender of the person
    public Gender getGender() {
        return gender;
    }

    // Returns the bank account associated with the person
    protected Balance getBankAccount() {
        return bankAccount;
    }

    // Calculates and returns the age of the person based on their birth date
    public int getAge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birthDate = LocalDate.parse(this.birthDate, formatter);
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthDate, currentDate);
        return period.getYears();
    }

    // Returns the ID of the person
    public int getId() {
        return id;
    }

    // Returns a string representation of the person with all details
    @Override
    public String toString() {
        return "ID: " + this.id + " | Name: " + this.name + " | Gender: " + this.gender + " | Birthday: " + this.birthDate + " | Age: " + this.getAge() + " | Balance: " + this.bankAccount.getBalance();
    }
}

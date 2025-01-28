package gym.customers;

import gym.management.Secretary;
import gym.management.Sessions.SessionType;

import java.util.List;

public class Instructor extends Person {
    private final int salary;
    private final List<SessionType> qualifiedSessions;

    // Constructor to initialize the instructor with a person, salary, and qualified session types
    private Instructor(Person person, int salary, List<SessionType> qualifiedSessions) {
        super(person);
        this.salary = salary;
        this.qualifiedSessions = qualifiedSessions;
    }

    // Returns the salary of the instructor
    private int getSalary() {
        return salary;
    }

    // Returns a string representation of the instructor, including their role, salary, and qualified sessions
    @Override
    public String toString() {
        String output = super.toString();
        output += " | Role: Instructor | Salary per Hour: " + getSalary() + " | Certified Classes: ";
        for (SessionType session : qualifiedSessions) {
            output += session.name() + ", ";
        }
        return output.substring(0, output.length() - 2);
    }

    // Checks if the instructor is qualified to teach the given session type
    private boolean isQualified(SessionType sessionType) {
        return qualifiedSessions.contains(sessionType);
    }

    public static class SecretaryActionsToInstructor {
        private Secretary secretary;

        // Constructor to initialize the secretary
        public SecretaryActionsToInstructor(Secretary secretary) {
            this.secretary = secretary;
        }

        // Builds a new instructor if the secretary is active
        public Instructor BuildInstructor(Person person, int salary, List<SessionType> qualifiedSessions) {
            if (!this.secretary.isActive()) {
                throw new NullPointerException();
            }
            return new Instructor(person, salary, qualifiedSessions);
        }
        // Returns the salary of the instructor if secretary is active
        public int getSalary(Instructor instructor) {
            if (!this.secretary.isActive()) {
                throw new NullPointerException();
            }
            return instructor.getSalary();
        }
        public Balance getBalance(Instructor instructor) {
            if (!this.secretary.isActive()) {
                throw new NullPointerException();
            }
            return instructor.getBankAccount();
        }
        public boolean isQualified(Instructor instructor, SessionType sessionType) {
            if (!this.secretary.isActive()) {
                throw new NullPointerException();
            }
            return instructor.isQualified(sessionType);
        }
    }
}

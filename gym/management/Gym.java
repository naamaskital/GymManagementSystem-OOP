package gym.management;

import gym.Exception.ClientNotRegisteredException;
import gym.customers.Balance;
import gym.customers.Client;
import gym.customers.Instructor;
import gym.customers.Person;
import gym.management.Sessions.Session;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class Gym {
    private static Gym instance;
    private Secretary activeSecretary;
    private List<Client> clients;
    private List<Instructor> instructors;
    private List<Session> sessions;
    private String name;
    private int balance;
    private Map<LocalDate, HashSet<Session>> sessionsByDate;

    // Private constructor for singleton pattern
    private Gym() {
        this.clients = new ArrayList<Client>();
        this.instructors = new ArrayList<Instructor>();
        this.sessions = new ArrayList<Session>();
        this.sessionsByDate = new HashMap<LocalDate, HashSet<Session>>();
        balance = 0;
    }

    // Returns the singleton instance of the gym
    public static Gym getInstance() {
        if (instance == null) {
            instance = new Gym();
        }
        return instance;
    }

    // Set the gym's active secretary and deactivate the previous one if applicable
    public void setSecretary(Person person, int salary) {
        List<String> actionsHistory = new ArrayList<>();
        if (this.activeSecretary != null) {
            this.activeSecretary.deactivate();
            actionsHistory = this.activeSecretary.getActionsHistory();
        }
        this.activeSecretary = new Secretary(person, salary, actionsHistory);
        this.activeSecretary.activate();
    }

    // Returns the current active secretary
    public Secretary getSecretary() {
        return activeSecretary;
    }

    // Sets the gym's name
    public void setName(String name) {
        this.name = name;
    }

    // Checks if the client is registered in the gym
    protected boolean isRegistered(Client client) {
        return this.clients.contains(client);
    }

    // Overloaded method to check if a person (client) is registered
    public boolean isRegistered(Person person) {
        for (Client client : this.clients) {
            if (client.getId() == person.getId()) {
                return true;
            }
        }
        return false;
    }

    // Registers a new client in the gym
    protected void registerClient(Client client) {
        this.clients.add(client);
    }

    // Returns the list of all clients
    protected List<Client> getClients() {
        return clients;
    }

    // Unregisters a client from the gym
    protected void unregisterClient(Client client) throws ClientNotRegisteredException {
        if (!isRegistered(client)) {
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
        }
        this.clients.remove(client);
    }

    // Adds an instructor to the gym
    protected void addInstructor(Instructor instructor) {
        instructors.add(instructor);
    }

    // Adds a session to the gym
    protected void addSession(Session session) {
        sessions.add(session);
    }

    // Adds a session on a specific date
    protected void addSession(Session session, LocalDate date) {
        sessionsByDate.putIfAbsent(date, new HashSet<>());
        sessionsByDate.get(date).add(session);
    }

    // Returns the set of sessions on a specific date
    protected HashSet<Session> getSessionsByDate(LocalDate date) {
        return sessionsByDate.getOrDefault(date, new HashSet<>());
    }

    // Adds money to the gym's balance
    protected void addMoney(int money) {
        this.balance += money;
    }
    protected void subMoney(int money){
        this.balance -= money;
    }

    protected Map<LocalDate, HashSet<Session>> getSessionsByDate() {
        return sessionsByDate;
    }

    // Returns a string representation of the gym's data
    @Override
    public String toString() {
        String output = "Gym Name: " + name + "\n";
        output += "Gym Secretary: " + this.activeSecretary.toString();
        output += "Gym Balance: " + balance + "\n";
        output += "\n";
        output += "Clients Data:\n";
        for (Client client : clients) {
            output += client.toString() + "\n";
        }
        output += "\nEmployees Data:\n";
        for (Instructor instructor : instructors) {
            output += instructor.toString() + "\n";
        }
        output += this.activeSecretary.toString();
        output += "\nSessions Data:\n";
        for (Session session : sessions) {
            output += session.toString() + "\n";
        }
        return output.substring(0, output.length() - 1);
    }
}

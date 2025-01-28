package gym.customers;
import gym.Exception.DuplicateClientException;
import gym.Exception.InvalidAgeException;
import gym.management.Gym;
import gym.management.Secretary;
import gym.management.Sessions.Session;

import java.util.ArrayList;
import java.util.List;

public class Client extends Person implements Observer{
    private Person person;
    private List<String> notifications;
    private boolean isSecretary;

    // Constructor to initialize the client with a Person object
    private Client(Person person) {
        super(person);
        this.notifications = new ArrayList<>();
        this.isSecretary= false;
    }

    // Returns the list of notifications for the client
    public List<String> getNotifications() {
        return notifications;
    }

    // Adds a new notification to the client's list of notifications
    @Override
    public void update(String notification) {
        if(isSecretary) notifications.add(notification);
    }

    public static class SecretaryActionsToClient {
        private Secretary secretary;

        // Constructor to initialize the secretary
        public SecretaryActionsToClient(Secretary secretary) {
            this.secretary = secretary;
        }

        // Registers a new client if the secretary is active and all conditions are met
        public Client registerClient(Person person) throws DuplicateClientException, InvalidAgeException {
            if (!this.secretary.isActive()) {
                throw new NullPointerException();
            }
            if (person.getAge() < 18) {
                throw new InvalidAgeException("Error: Client must be at least 18 years old to register");
            }
            if (Gym.getInstance().isRegistered(person)) {
                throw new DuplicateClientException("Error: The client is already registered");
            }
            return new Client(person);
        }

        // Sends a notification to the specified client
        public void receiveNotification(Client client, String s) {
            if (!this.secretary.isActive()) {
                throw new NullPointerException();
            }
            client.isSecretary= true;
            client.update(s);
            client.isSecretary= false;
        }
        public Balance getBalance(Client client) {
            if (!this.secretary.isActive()) {
                throw new NullPointerException();
            }
            return client.getBankAccount();
        }
        public int getMoney(Client client) {
            if (!this.secretary.isActive()) {
                throw new NullPointerException();
            }
            return client.getBankAccount().getBalance();
        }
    }
}

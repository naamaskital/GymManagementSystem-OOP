package gym.management;
import gym.Exception.*;
import gym.customers.*;
import gym.management.Sessions.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Secretary extends Person{
    private final int salary;
    private boolean isActive;  // האם המזכירה פעילה כעת
    private List<String> actionsHistory;  // היסטוריית פעולות המזכירה
    private Session.SecretaryActionsToSession sessionActions;
    private Instructor.SecretaryActionsToInstructor instructorActions;
    private Client.SecretaryActionsToClient clientActions;
    private Balance.SecretaryActions balanceActions;
    private Gym gym;

    // Constructor for creating a new secretary
    protected Secretary(Person person, int salary, List<String> history) {
        super(person);
        this.salary = salary;
        this.isActive = false;
        this.actionsHistory = history;
        sessionActions = new Session.SecretaryActionsToSession(this);
        instructorActions = new Instructor.SecretaryActionsToInstructor(this);
        clientActions = new Client.SecretaryActionsToClient(this);
        balanceActions= new Balance.SecretaryActions(this);
        gym = Gym.getInstance();
    }

    // Method to activate the secretary
    protected void activate() {
        this.isActive = true;
        actionsHistory.add("A new secretary has started working at the gym: " + getName());
    }

    // Method to deactivate the secretary
    protected void deactivate() {
        this.isActive = false;
    }

    // Returns the salary of the secretary
    protected int getSalary() { return salary; }

    // Checks if the secretary is active
    public boolean isActive() {
        return isActive;
    }

    // Registers a new client if the secretary is active
    public Client registerClient(Person personToReg) throws InvalidAgeException, DuplicateClientException {
        Client newClient = clientActions.registerClient(personToReg);
        actionsHistory.add("Registered new client: " + personToReg.getName());
        gym.registerClient(newClient);
        return newClient;
    }

    // Unregisters an existing client
    public void unregisterClient(Client client) throws ClientNotRegisteredException {
        if(!this.isActive) throw new NullPointerException();
        gym.unregisterClient(client);
        actionsHistory.add("Unregistered client: " + client.getName());
    }

    // Hires a new instructor and adds to the gym
    public Instructor hireInstructor(Person p, int salary, ArrayList<SessionType> sessionTypes) {
        Instructor instructor = instructorActions.BuildInstructor(p, salary, sessionTypes);
        actionsHistory.add("Hired new instructor: " + instructor.getName() + " with salary per hour: " + instructorActions.getSalary(instructor));
        gym.addInstructor(instructor);
        return instructor;
    }

    // Adds a new session to the gym
    public Session addSession(SessionType sessionType, String stringDateTime, ForumType forumType, Instructor instructor) throws InstructorNotQualifiedException {
        if(!instructorActions.isQualified(instructor, sessionType)) throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");
        Session session = sessionActions.addSession(sessionType, stringDateTime, forumType, instructor);
        gym.addSession(session);
        LocalDateTime date = sessionActions.getDate(session);
        actionsHistory.add("Created new session: " + sessionType + " on " + date + " with instructor: " + instructor.getName());
        gym.addSession(session, date.toLocalDate());
        return session;
    }

    // Registers a client to a session
    public void registerClientToLesson(Client client, Session session) throws DuplicateClientException, ClientNotRegisteredException {
        ArrayList<String> str = sessionActions.registerClient(client, session,clientActions.getMoney(client));
        if(str.isEmpty()) {
            balanceActions.subBalance(clientActions.getBalance(client),session.getSessionType().getPrice());
            Gym.getInstance().addMoney(session.getSessionType().getPrice());
            actionsHistory.add("Registered client: " + client.getName() + " to session: " + session.getSessionType() + " on " + sessionActions.getDate(session) + " for price: " + session.getSessionType().getPrice());
        }
        else actionsHistory.addAll(str);
    }

    // Sends a notification to all clients in a specific session
    public void notify(Session session, String message) {
        if(!this.isActive) throw new NullPointerException();
        List<Client> clients = sessionActions.getClients(session);
        for(Client c : clients) { clientActions.receiveNotification(c, message); }
        actionsHistory.add("A message was sent to everyone registered for session " + session.getSessionType() + " on " + sessionActions.getDate(session) + " : " + message);
    }

    // Sends a notification to all clients of the gym
    public void notify(String message) {
        if(!this.isActive) throw new NullPointerException();
        for(Client client : gym.getClients()) { clientActions.receiveNotification(client, message); }
        actionsHistory.add("A message was sent to all gym clients: " + message);
    }

    // Sends a notification to clients on sessions scheduled for a specific date
    public void notify(String stringDate, String message) {
        if(!this.isActive) throw new NullPointerException();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(stringDate, formatter);
        HashSet<Session> sessions = gym.getSessionsByDate(date);
        HashSet<Client> clients = new HashSet<>();
        for(Session session : sessions) {
            clients.addAll(sessionActions.getClients(session));
        }
        notifyForList(clients, message);
        actionsHistory.add("A message was sent to everyone registered for a session on " + date + " : " + message);
    }

    // Sends a notification to a specific list of clients
    private void notifyForList(HashSet<Client> clients, String message) {
        for(Client client : clients) { clientActions.receiveNotification(client, message); }
    }

    // Prints the action history of the secretary
    public void printActions() {
        if(!this.isActive) throw new NullPointerException();
        for (String action : actionsHistory) {
            System.out.println(action);
        }
    }

    // Returns the action history of the secretary
    public List<String> getActionsHistory() {
        return actionsHistory;
    }

    // Pays the salaries of all employees
    public void paySalaries() {
        if (!this.isActive) throw new NullPointerException();
        Map<LocalDate, HashSet<Session>> sesionsByDate=gym.getSessionsByDate();
        for (LocalDate date :sesionsByDate.keySet()) {
            for (Session session : sesionsByDate.get(date)) {
                Instructor instructor = sessionActions.getInstructor(session);
                gym.subMoney(instructorActions.getSalary(instructor));
                balanceActions.addBalance(instructorActions.getBalance(instructor),instructorActions.getSalary(instructor));
            }
        }
        balanceActions.addBalance(this.getBankAccount(),salary);
        gym.subMoney(salary);
        actionsHistory.add("Salaries have been paid to all employees");
    }

    // String representation of the secretary object
    @Override
    public String toString(){
        String output = super.toString();
        output += " | Role: Secretary | Salary per Month: " + this.salary + "\n";
        return output;
    }
}

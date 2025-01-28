package gym.management.Sessions;
import gym.Exception.*;
import gym.customers.*;
import gym.management.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class Session {
    protected LocalDateTime date;
    protected Instructor instructor;
    protected ForumType forumType;
    protected List<Client> clients;

    // Constructor for initializing a session with date, forum type, and instructor
    protected Session(LocalDateTime date, ForumType forumType, Instructor instructor) {
        this.date = date;
        this.instructor = instructor;
        this.forumType = forumType;
        this.clients = new ArrayList<>();
    }

    // Abstract method to get the session type
    abstract public SessionType getSessionType();


    // Checks if the client's details match the session's requirements
    private ArrayList<String> isMatchForSession(Client client){
        ArrayList<String> str = new ArrayList<>();
        if ((this.forumType == ForumType.Male && client.getGender() != Gender.Male)||(this.forumType == ForumType.Female && client.getGender() != Gender.Female)) {
            str.add("Failed registration: Client's gender doesn't match the session's gender requirements");
        } else if (this.forumType == ForumType.Seniors && client.getAge() < 65) {
            str.add("Failed registration: Client doesn't meet the age requirements for this session (Seniors)");
        }
        return str;
    }

    // Provides a string representation of the session
    @Override
    public String toString(){
        return "Session Type: "+this.getClass().getSimpleName()+" | Date: "+this.getDateTime()+" | Forum: "+forumType+" | Instructor: "+instructor.getName()+" | Participants: "+clients.size()+"/"+getSessionType().getMaxParticipants();
    }

    // Formats the date and time of the session
    private String getDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return date.format(formatter);
    }

    // Inner class for secretary actions related to the session
    public static class SecretaryActionsToSession{
        private Secretary secretary;

        // Constructor for initializing SecretaryActionsToSession with a specific secretary
        public SecretaryActionsToSession(Secretary s) {
            this.secretary = s;
        }

        // Adds a new session to the gym
        public Session addSession(SessionType sessionType, String stringDateTime, ForumType forumType, Instructor instructor)throws InstructorNotQualifiedException {
            if(!this.secretary.isActive()) throw new NullPointerException();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(stringDateTime,formatter);
            return SessionFactory.createSession(sessionType,dateTime,forumType,instructor);
        }

        // Registers a client for a session, ensuring all conditions are met
        public ArrayList<String> registerClient(Client client, Session session,int clientBalance) throws DuplicateClientException, ClientNotRegisteredException {
            if(!this.secretary.isActive()) throw new NullPointerException();
            ArrayList<String> str = new ArrayList<>();
            if (session.clients.contains(client)) throw new DuplicateClientException("Error: The client is already registered for this lesson");
            if(!Gym.getInstance().isRegistered(client)) throw new ClientNotRegisteredException("Error: The client is not registered with the gym and cannot enroll in lessons");
            if(session.date.isBefore(LocalDateTime.now())) str.add("Failed registration: Session is not in the future");
            str.addAll(session.isMatchForSession(client));
            if(clientBalance<session.getSessionType().getPrice()) str.add("Failed registration: Client doesn't have enough balance");
            if(session.getSessionType().getMaxParticipants()<=session.clients.size()) str.add("Failed registration: No available spots for session");
            if(str.isEmpty()){session.clients.add(client);}
            return str;
        }

        // Returns the list of clients registered for a session
        public List<Client> getClients(Session session) {
            if(!this.secretary.isActive()) throw new NullPointerException();
            return session.clients;
        }
        public Instructor getInstructor(Session session) {
            if(!this.secretary.isActive()) throw new NullPointerException();
            return session.instructor;
        }
        public LocalDateTime getDate(Session session) {
            if(!this.secretary.isActive()) throw new NullPointerException();
            return session.date;
        }
    }

}

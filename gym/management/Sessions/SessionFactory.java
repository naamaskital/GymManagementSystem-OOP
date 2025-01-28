package gym.management.Sessions;

import gym.Exception.InstructorNotQualifiedException;
import gym.customers.Instructor;

import java.time.LocalDateTime;

public class SessionFactory {

    // Creates a session based on the session type, date, forum type, and instructor
    protected static Session createSession(SessionType type, LocalDateTime dateTime, ForumType forumType, Instructor instructor) {
        // Creates a session based on the specified session type
        switch (type) {
            case Pilates:
                return new Pilates(dateTime, forumType, instructor);
            case ThaiBoxing:
                return new ThaiBoxing(dateTime, forumType, instructor);
            case Ninja:
                return new Ninja(dateTime, forumType, instructor);
            case MachinePilates:
                return new MachinePilates(dateTime, forumType, instructor);
            // If an unknown session type is provided, throws an exception
            default:
                throw new IllegalArgumentException("Unknown session type: " + type);
        }
    }
}

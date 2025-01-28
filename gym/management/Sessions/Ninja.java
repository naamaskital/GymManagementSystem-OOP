package gym.management.Sessions;

import gym.customers.Instructor;

import java.time.LocalDateTime;

public class Ninja extends Session {
    public Ninja(LocalDateTime dateTime, ForumType forumType, Instructor instructor) {
        super( dateTime, forumType, instructor);
    }
    public SessionType getSessionType() {
        return SessionType.Ninja;
    }
}

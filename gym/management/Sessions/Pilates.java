package gym.management.Sessions;

import gym.customers.Instructor;

import java.time.LocalDateTime;

public class Pilates extends Session {
    public Pilates(LocalDateTime dateTime, ForumType forumType, Instructor instructor) {
        super(dateTime,forumType,instructor);
    }

    @Override
    public SessionType getSessionType() {
        return SessionType.Pilates;
    }

}

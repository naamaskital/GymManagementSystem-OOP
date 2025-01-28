package gym.management.Sessions;

import gym.customers.Instructor;

import java.time.LocalDateTime;

public class ThaiBoxing extends Session {
    public ThaiBoxing(LocalDateTime dateTime, ForumType forumType, Instructor instructor) {
        super(dateTime,forumType,instructor);
    }

    @Override
    public SessionType getSessionType() {
        return SessionType.ThaiBoxing;
    }

}

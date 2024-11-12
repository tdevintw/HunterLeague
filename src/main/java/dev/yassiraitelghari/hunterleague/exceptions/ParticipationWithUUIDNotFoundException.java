package dev.yassiraitelghari.hunterleague.exceptions;

public class ParticipationWithUUIDNotFoundException extends RuntimeException {

    public ParticipationWithUUIDNotFoundException() {
        super("There is no participation with this uuid");
    }
}

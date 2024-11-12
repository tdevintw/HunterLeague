package dev.yassiraitelghari.hunterleague.exceptions;

public class UserNotParticipateInCompetitionException extends RuntimeException{
    public UserNotParticipateInCompetitionException(){
        super("The user didnt participate in this competition");
    }
}

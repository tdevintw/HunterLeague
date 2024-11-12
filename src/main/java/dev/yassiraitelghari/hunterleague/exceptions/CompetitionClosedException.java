package dev.yassiraitelghari.hunterleague.exceptions;

public class CompetitionClosedException extends RuntimeException{
    public CompetitionClosedException(String message){
        super(message);
    }
}

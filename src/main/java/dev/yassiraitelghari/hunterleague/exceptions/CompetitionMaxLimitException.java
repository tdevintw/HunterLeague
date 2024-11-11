package dev.yassiraitelghari.hunterleague.exceptions;

public class CompetitionMaxLimitException extends RuntimeException {
    public CompetitionMaxLimitException(String message){
        super(message);
    }
}

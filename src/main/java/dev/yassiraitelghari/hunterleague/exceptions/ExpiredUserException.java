package dev.yassiraitelghari.hunterleague.exceptions;

public class ExpiredUserException extends RuntimeException{
    public ExpiredUserException(String message){
        super(message);
    }
}

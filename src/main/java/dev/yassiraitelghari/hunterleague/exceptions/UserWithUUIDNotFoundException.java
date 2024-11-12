package dev.yassiraitelghari.hunterleague.exceptions;

import java.util.UUID;

public class UserWithUUIDNotFoundException extends RuntimeException{
    public UserWithUUIDNotFoundException(UUID id){
        super("There is no user with this UUID : " + id);
    }
}

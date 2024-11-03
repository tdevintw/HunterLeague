package dev.yassiraitelghari.hunterleague.exceptions;

import java.util.UUID;

public class UserWithUUIDNotFound extends RuntimeException{
    public UserWithUUIDNotFound(UUID id){
        super("There is no user with this UUID : " + id);
    }
}

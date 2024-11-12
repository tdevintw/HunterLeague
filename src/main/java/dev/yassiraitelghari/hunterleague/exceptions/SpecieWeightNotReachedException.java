package dev.yassiraitelghari.hunterleague.exceptions;

public class SpecieWeightNotReachedException extends RuntimeException {
    public SpecieWeightNotReachedException(){
        super("Hunt weight is less then minimum weight");
    }
}

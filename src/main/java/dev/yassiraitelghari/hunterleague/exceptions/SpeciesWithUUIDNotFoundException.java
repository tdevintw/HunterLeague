package dev.yassiraitelghari.hunterleague.exceptions;

public class SpeciesWithUUIDNotFoundException extends  RuntimeException{
    public SpeciesWithUUIDNotFoundException(){
        super("There is no specie With this uuid");
    }
}

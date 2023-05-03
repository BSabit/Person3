package ru.batyrkhanov.springsourse.FirstRestApp.util;

public class PersonNotCreatedException extends RuntimeException{
    public  PersonNotCreatedException(String message) {
        super(message);
    }
}

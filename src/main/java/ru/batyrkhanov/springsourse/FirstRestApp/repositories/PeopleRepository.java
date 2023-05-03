package ru.batyrkhanov.springsourse.FirstRestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.batyrkhanov.springsourse.FirstRestApp.models.Person;

import java.util.List;

public interface PeopleRepository extends JpaRepository<Person, Integer> {

    List<Person> findByFullName(String name);
    List<Person> findByHobby(String hobby);
    List<Person> findByFullNameStartingWith(String startingWithName);
    List<Person> findByHobbyStartingWith(String startingWithHobby);
}

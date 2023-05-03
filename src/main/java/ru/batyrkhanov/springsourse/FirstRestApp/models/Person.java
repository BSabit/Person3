package ru.batyrkhanov.springsourse.FirstRestApp.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Введите имя и фамилию")
    @Size(min = 2, max = 120, message = "Минимальное количество символов- 2, максимальное количество символов- 120")
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @Column(name = "hobby")
    @NotEmpty(message = "Введите хобби")
    @Size(min = 2, max = 120, message = "Минимальное количество символов- 2, максимальное количество символов- 120")
    private String hobby;

    @Column(updatable = false, name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;


    public Person() {}

    public Person(int id, String fullName, Date dateOfBirth, String hobby) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.hobby = hobby;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", hobby='" + hobby + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(fullName, person.fullName) && Objects.equals(dateOfBirth, person.dateOfBirth) && Objects.equals(hobby, person.hobby) && Objects.equals(createdAt, person.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, dateOfBirth, hobby, createdAt);
    }
}

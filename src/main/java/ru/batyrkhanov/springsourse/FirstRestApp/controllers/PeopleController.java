package ru.batyrkhanov.springsourse.FirstRestApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.batyrkhanov.springsourse.FirstRestApp.models.Person;
import ru.batyrkhanov.springsourse.FirstRestApp.services.PeopleService;
import ru.batyrkhanov.springsourse.FirstRestApp.util.PersonErrorResponse;
import ru.batyrkhanov.springsourse.FirstRestApp.util.PersonNotCreatedException;
import ru.batyrkhanov.springsourse.FirstRestApp.util.PersonNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public List<Person> findAll() {
        return peopleService.findAll(); //Jackson конвертирует эти объекты в JSON
    }

    @GetMapping("/{id}")
    public Person findOne(@PathVariable("id")int id) {
        return peopleService.findOne(id);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
           StringBuilder errorMessage = new StringBuilder();

           List<FieldError> errors = bindingResult.getFieldErrors();
           for (FieldError error : errors) {
               errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
           }

           throw new PersonNotCreatedException(errorMessage.toString());
        }

        peopleService.save(person);

        //Отправляем HTTP ответ с пустым телом и со статусом 200. С HttpStatus.OK мы просто сообщаем, что все OK, человек сохранился.
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid Person person, @PathVariable("id") int id,
                                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }

            throw new PersonNotCreatedException(errorMessage.toString());
        }

        peopleService.update(id, person);

        return ResponseEntity.ok(HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        peopleService.delete(id);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handlerException(PersonNotFoundException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                "Person with this id wasn't found!",
                System.currentTimeMillis()
        );

        //В HTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); //NOT_FOUND - статус 404
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handlerException(PersonNotCreatedException e) {
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        //В HTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //NOT_FOUND - статус 404
    }
}

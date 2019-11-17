package com.dev.tech.skills.app.resource;


import com.dev.tech.skills.app.model.Address;
import com.dev.tech.skills.app.model.Person;
import com.dev.tech.skills.app.repository.PersonRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Api(value="PersonResource")
@RestController
@RequestMapping("/api/v1/persons")
public class PersonResource {

    private PersonRepository personRepository;

    @Autowired
    public PersonResource(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @ApiOperation(value = "View a list of available persons", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    ResponseEntity<List<Person>> list(){
        return ResponseEntity.ok(personRepository.findAll());
    }


    @ApiOperation(value = "View a list of person for a given id", response = Person.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a person"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<Person> getPersonById(@PathVariable long id) throws ResourceNotFoundException{
        Objects.requireNonNull(id);
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + id));
        return ResponseEntity.ok(person);
    }

    @ApiOperation(value = "Add a person")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Person> create(@RequestBody Person person, UriComponentsBuilder builder){
        personRepository.saveAndFlush(person);
        UriComponents uriComponents = builder.path("/api/v1/persons/{id}").buildAndExpand(person.getId());
        return ResponseEntity.created(uriComponents.toUri()).body(person);
    }

    @ApiOperation(value = "Update a person's address")
    @PutMapping("/{id}/address")
    public ResponseEntity<Object> updatePerson(@RequestBody Address address, @PathVariable long id) {

        Optional<Person> personOptional = personRepository.findById(id);

        if (!personOptional.isPresent())
            return ResponseEntity.notFound().build();
        Person person = personOptional.get();
        person.setAddress(address);
        personRepository.saveAndFlush(person);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Search a person with a given first name and/or lastname")
    @GetMapping("/search")
    public ResponseEntity<Person> search(@RequestParam @Nullable String firstName,@RequestParam @Nullable String lastName) {

        if(StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName)){
            List<Person> persons = personRepository.findByFirstNameIsOrLastNameIs(firstName, lastName);
            if(persons.size()>1){
                throw new DuplicateFirstNameException();
            }
            return ResponseEntity.ok(persons.get(0));
        }else{
           return ResponseEntity.ok(personRepository.findByFirstNameIsAndLastNameIs(firstName, lastName));
        }

    }

}

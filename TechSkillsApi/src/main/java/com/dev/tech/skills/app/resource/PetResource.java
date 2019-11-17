package com.dev.tech.skills.app.resource;


import com.dev.tech.skills.app.model.Person;
import com.dev.tech.skills.app.model.Pet;
import com.dev.tech.skills.app.repository.PersonRepository;
import com.dev.tech.skills.app.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pets")
public class PetResource {

    private PetRepository petRepository;

    private PersonRepository personRepository;

    @Autowired
    public PetResource(PetRepository petRepository, PersonRepository personRepository){
        this.petRepository = petRepository;
        this.personRepository = personRepository;
    }

    @GetMapping
    ResponseEntity<List<Pet>> list(){
        return ResponseEntity.ok(petRepository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Pet> getPetById(@PathVariable long id){
        Objects.requireNonNull(id);
        return ResponseEntity.ok(petRepository.findById(id).get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Pet> create(@RequestBody Pet Pet, UriComponentsBuilder builder){
        petRepository.saveAndFlush(Pet);
        UriComponents uriComponents = builder.path("/api/v1/pets/{id}").buildAndExpand(Pet.getId());
        return ResponseEntity.created(uriComponents.toUri()).body(Pet);
    }

    @PutMapping("/{id}/person")
    public ResponseEntity<Object> updatePet(@RequestBody Long personId, @PathVariable Long id) {

        Optional<Person> personOptional = personRepository.findById(personId);

        if (!personOptional.isPresent())
            return ResponseEntity.notFound().build();

        System.out.println("***************"+id);
        Pet pet = petRepository.findPetById(id);

       /* if (!petOptional.isPresent())
            return ResponseEntity.notFound().build();

        Pet pet = petOptional.get();
       */ pet.setPerson(personOptional.get());
        petRepository.saveAndFlush(pet);

        return ResponseEntity.noContent().build();
    }

}

package com.dev.tech.skills.app.repository;

import com.dev.tech.skills.app.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByFirstNameIsOrLastNameIs(String firstName, String lastName);

    Person findByFirstNameIsAndLastNameIs(String firstName, String lastName);
}

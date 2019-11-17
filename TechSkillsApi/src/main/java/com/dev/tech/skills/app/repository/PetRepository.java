package com.dev.tech.skills.app.repository;

import com.dev.tech.skills.app.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("select p from Pet p where p.id = ?1")
    public Pet findPetById(Long id);
}

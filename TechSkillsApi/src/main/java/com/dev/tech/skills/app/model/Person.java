package com.dev.tech.skills.app.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@Data
@Getter
@Setter
@ToString
@Entity
@Table(name = "persons",
        uniqueConstraints = @UniqueConstraint(name = "UC_PERSON",
                columnNames = {"FIRST_NAME", "LAST_NAME"})
)
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_id_generator")
    @SequenceGenerator(name = "person_id_generator", sequenceName = "PERSON_ID_SEQ", allocationSize = 10)
    @Column(name = "PERSON_ID")
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "FIRST_NAME")
    private String firstName;


    @NotBlank
    @Size(max = 50)
    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "DOB")
    private LocalDate dob;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    private Address address;


   /* @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    private Set<Pet> pets = new HashSet<>();*/
}

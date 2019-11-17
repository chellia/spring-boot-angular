package com.dev.tech.skills.app.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@ToString
@Entity
@Getter
@Setter
@Table(name = "pets")
public class Pet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_id_generator")
    @SequenceGenerator(name = "pet_id_generator", sequenceName = "PET_ID_SEQ", allocationSize=10)
    @Column(name = "PET_ID")
    private Long id;

    //@Size(max = 50)
    @Column(name = "NAME")
    private String name;


    //@Size(max = 50)
    @Column(name = "age")
    private Integer age;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "PERSON_PET",
               joinColumns = @JoinColumn(name = "PET_ID"),
               inverseJoinColumns = @JoinColumn(name = "PERSON_ID", nullable = false))
    private Person person;

}

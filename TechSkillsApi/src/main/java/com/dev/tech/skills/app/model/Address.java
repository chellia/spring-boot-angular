package com.dev.tech.skills.app.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;


@AllArgsConstructor
@ToString
@Data
@Getter
@Setter
@Entity
@Table(name = "Address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_id_generator")
    @SequenceGenerator(name = "address_id_generator", sequenceName = "ADDRESS_ID_SEQ", allocationSize=10)
    @Column(name = "ADDRESS_ID")
    private Long id;

    @Size(max = 50)
    @Column(name = "STREET")
    private String street;

    @Size(max = 50)
    @Column(name = "CITY")
    private String city;

    @Size(max = 50)
    @Column(name = "STATE")
    private String state;

    @Size(max = 50)
    @Column(name = "ZIPCODE")
    private String zipcode;

}

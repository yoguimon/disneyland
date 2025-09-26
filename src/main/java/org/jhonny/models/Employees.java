package org.jhonny.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Employees extends Persons {

    @OneToMany(mappedBy = "employee")
    private Set<Games> games;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Users user;
}

package org.jhonny.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.inject.Named;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jhonny.dto.PersonRequest;
import org.jhonny.dto.PersonResponse;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Employees extends Persons {

    @OneToMany(mappedBy = "employee")
    private Set<Games> games = new HashSet<>();

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Users user;
}

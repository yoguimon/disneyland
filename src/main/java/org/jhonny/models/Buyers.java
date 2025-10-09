package org.jhonny.models;

import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jhonny.dto.PersonRequest;
import org.jhonny.dto.PersonResponse;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Buyers extends Persons {

    @OneToMany(mappedBy = "buyer")
    private List<Tickets> tickets;
}

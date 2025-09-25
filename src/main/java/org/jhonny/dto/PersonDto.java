package org.jhonny.dto;

import lombok.Data;
import org.jhonny.utils.TypePerson;

@Data
public class PersonDto {
    private String ci;
    private String firstName;
    private String lastName;
    private String email;
    private TypePerson type;

}

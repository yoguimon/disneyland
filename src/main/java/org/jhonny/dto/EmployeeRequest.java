package org.jhonny.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import org.jhonny.utils.EmployeeType;

import java.util.List;

import static org.jhonny.utils.Contants.ADMIN;
import static org.jhonny.utils.Contants.GAME;
import static org.jhonny.utils.Contants.TYPE;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = TYPE,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(
                value = EmployeeAdminRequest.class,
                name = ADMIN),
        @JsonSubTypes.Type(
                value = EmployeeGameRequest.class,
                name = GAME)
})
@Getter
@Setter
public abstract class EmployeeRequest {
    private String ci;
    private String firstName;
    private String lastName;
    private String email;
    private EmployeeType type;
    private List<Long> gameIds;
}

package org.jhonny.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeAdminRequest extends EmployeeRequest{
    private String level;
}

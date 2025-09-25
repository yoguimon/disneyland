package org.jhonny.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jhonny.utils.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {
    private String message;
    private Status status;
    private Object object;
}

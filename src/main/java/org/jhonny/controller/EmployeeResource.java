package org.jhonny.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jhonny.dto.EmployeeRequest;
import org.jhonny.dto.EmployeeResponse;
import org.jhonny.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/v1/employees")
@RequestScoped
public class EmployeeResource {

    private final Logger LOGGER =  LoggerFactory.getLogger(EmployeeResource.class);

    private final EmployeeService employeeService;

    @Inject
    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @POST
    public Response addEmployee(EmployeeRequest employee) {
        try {
            LOGGER.info("Registering employee {}",  employee);
            EmployeeResponse newEmployee = employeeService.addEmployee(employee);
            return Response.status(Response.Status.CREATED)
                    .entity(newEmployee)
                    .build();

        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }
}

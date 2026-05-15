package org.jhonny.strategy;

import org.jhonny.dto.EmployeeRequest;
import org.jhonny.models.Employee;

public interface EmployeeMapperStrategy<T extends EmployeeRequest> {
    Class<T> supports();
    Employee toEntity(T request);

    default boolean supportsRequest(EmployeeRequest request) {
        return supports()
            .isAssignableFrom(
                    request.getClass()
            );
    }

    default Employee map(EmployeeRequest request) {
        return toEntity(supports().cast(request));
    }
}

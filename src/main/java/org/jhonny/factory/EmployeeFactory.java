package org.jhonny.factory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import org.jhonny.repository.EmployeeRepository;
import org.jhonny.utils.EmployeeType;


import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class EmployeeFactory {

    private final Map<EmployeeType, EmployeeRepository> allInstancesMapOfPersonRepository = new HashMap<>();

    @Inject
    public EmployeeFactory(Instance<EmployeeRepository> personRepositoryInstances) {
        for (EmployeeRepository instance : personRepositoryInstances) {
            allInstancesMapOfPersonRepository.put(instance.getType(), instance);
        }
    }

    public EmployeeRepository getEmployee(EmployeeType type){
        return allInstancesMapOfPersonRepository.get(type);
    }
}

package com.example.stream.Servise;


import com.example.stream.Exeption.EmployeeAlreadyAddedException;
import com.example.stream.Exeption.EmployeeNotFoundException;
import com.example.stream.Exeption.EmployeeStorageIsFullException;
import com.example.stream.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeService {

    private static final int MAX_SIZE = 100;

    private final Map<String, Employee> employees = new HashMap<>(MAX_SIZE);

    public Employee add(String firstName, String lastName) {
        if (employees.size() > MAX_SIZE) {
            throw new EmployeeStorageIsFullException();
        }
        var key = (firstName + "_" + lastName).toLowerCase();
        if (employees.containsKey(key)) {
            throw new EmployeeAlreadyAddedException();
        }
        var employee = new Employee(firstName, lastName, 0, 0);
        employees.put(key, employee);
        return employee;
    }

    public Employee delete(String firstName, String lastName) {
        var key = (firstName + "_" + lastName).toLowerCase();
        var removed = employees.remove(key);
        if (removed == null) {
            throw new EmployeeNotFoundException();
        }
        return removed;
    }

    public Employee find(String firstName, String lastName) {
        var key = (firstName + "_" + lastName).toLowerCase();
        var employee = employees.get(key);
        if (employee == null) {
            throw new EmployeeNotFoundException();
        }
        return employee;
    }

    public Collection<Employee> getEmployees() {
        return employees.values();
    }
}

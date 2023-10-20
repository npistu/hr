package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.exception.NonUniqueIdException;
import hu.webuni.hr.npistu.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EmployeeAbstractService implements EmployeeService {

    private Map<Long, Employee> employees = new HashMap<>();

    public Employee create(Employee employee) {
        throwIfNonUniqueId(employee);
        return save(employee);
    }

    public Employee update(Employee employee) {
        if (findById(employee.getId()) == null) {
            return null;
        }

        return save(employee);
    }

    public List<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }

    public Employee findById(long id) {
        return employees.get(id);
    }

    public void delete(long id) {
        employees.remove(id);
    }

    private Employee save(Employee employee) {
        employees.put(employee.getId(), employee);

        return employee;
    }

    private void throwIfNonUniqueId(Employee employee) {
        if (employees.values().stream().anyMatch(a -> a.getId().equals(employee.getId()))) {
            throw new NonUniqueIdException();
        }
    }
}

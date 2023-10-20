package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.model.Employee;

import java.util.List;

public interface EmployeeService {
    int getPayRaisePercent(Employee employee);

    public Employee create(Employee employee);

    public Employee update(Employee employee);

    public List<Employee> findAll();

    public Employee findById(long id);

    public void delete(long id);
}

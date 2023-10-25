package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.model.Employee;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeService {
    int getPayRaisePercent(Employee employee);

    public Employee create(Employee employee);

    public Employee update(Employee employee);

    public List<Employee> findAll();

    public Employee findById(long id);

    public void delete(long id);

    public List<Employee> findByJob(String job);

    public List<Employee> findByNameStartingWithIgnoreCase(String name);

    public List<Employee> findByStartedBetween(LocalDateTime started, LocalDateTime end);
}

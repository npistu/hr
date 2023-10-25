package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public abstract class EmployeeAbstractService implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee create(Employee employee) {
        return save(employee);
    }

    public Employee update(Employee employee) {
        if (findById(employee.getId()) == null) {
            return null;
        }

        return save(employee);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public void delete(long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> findByJob(String job) {
        return employeeRepository.findByJob(job);
    }

    public List<Employee> findByNameStartingWithIgnoreCase(String name) {
        return employeeRepository.findByNameStartingWithIgnoreCase(name);
    }

    public List<Employee> findByStartedBetween(LocalDateTime started, LocalDateTime end) {
        return employeeRepository.findByStartedBetween(started, end);
    }

    private Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
}

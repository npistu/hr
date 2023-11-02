package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.model.Employee;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    int getPayRaisePercent(Employee employee);

    public Employee create(Employee employee);

    public Employee update(Employee employee);

    public List<Employee> findAll();

    public Page<Employee> findAllWithPage(Optional<String> sort, Optional<String> orderby, int pagenumber, int pagesize);

    public Employee findById(long id);

    public void delete(long id);

    public List<Employee> findByJob(String job);

    public List<Employee> findByNameStartingWithIgnoreCase(String name);

    public List<Employee> findByStartedBetween(LocalDateTime started, LocalDateTime end);
}

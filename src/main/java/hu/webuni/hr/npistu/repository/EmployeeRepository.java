package hu.webuni.hr.npistu.repository;

import hu.webuni.hr.npistu.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByJob(String job);

    List<Employee> findByNameStartingWithIgnoreCase(String name);

    List<Employee> findByStartedBetween(LocalDateTime started, LocalDateTime end);
}

package hu.webuni.hr.npistu.repository;

import hu.webuni.hr.npistu.model.Company;
import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByJob(String job);

    List<Employee> findByNameStartingWithIgnoreCase(String name);

    List<Employee> findByStartedBetween(LocalDateTime started, LocalDateTime end);

    @Query(value = "select job, avg(salary) average from employee where company_id = :companyId group by job order by average desc", nativeQuery = true)
    List<Object[]> findJobAndAvgSalaryByCompany_Id(long companyId);

    List<Employee> findByPositionAndSalaryLessThan(Position position, Integer salary);

    @Modifying
    @Query(value = "update Employee emp set emp.salary = :salary where emp.position = :position and emp.salary < :salary")
    void updateByPositionAndSalaryLessThan(Position position, Integer salary);

    @Modifying
    @Query(value = "update Employee emp set emp.salary = :salary where emp.position = :position and emp.company = :company and emp.salary < :salary")
    void updateByPositionAndCompanyAndSalaryLessThan(Position position, Integer salary, Company company);
}

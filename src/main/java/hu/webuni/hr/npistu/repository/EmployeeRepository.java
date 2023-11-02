package hu.webuni.hr.npistu.repository;

import hu.webuni.hr.npistu.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByJob(String job);

    List<Employee> findByNameStartingWithIgnoreCase(String name);

    List<Employee> findByStartedBetween(LocalDateTime started, LocalDateTime end);

    @Query(value = "select job, avg(salary) average from employee where company_id = :companyId group by job order by average desc", nativeQuery = true)
    List<Object[]> findJobAndAvgSalaryByCompany_Id(long companyId);
}

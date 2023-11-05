package hu.webuni.hr.npistu.repository;

import hu.webuni.hr.npistu.model.Company;
import hu.webuni.hr.npistu.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByEmployees_SalaryIsGreaterThan(Integer salary);

    @Query(value = "SELECT co.* from company co where (select count(emp.*) from employee emp where co.id = emp.company_id) > :size", nativeQuery = true)
    List<Company> findByEmployees_sizeIsGreaterThan(Integer size);
}

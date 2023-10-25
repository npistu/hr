package hu.webuni.hr.npistu.repository;

import hu.webuni.hr.npistu.model.Company;
import hu.webuni.hr.npistu.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}

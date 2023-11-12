package hu.webuni.hr.npistu.repository;

import hu.webuni.hr.npistu.model.Company;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findByEmployees_SalaryIsGreaterThan(Integer salary);

    @Query(value = "SELECT co.* from company co where (select count(emp.*) from employee emp where co.id = emp.company_id) > :size", nativeQuery = true)
    List<Company> findByEmployees_sizeIsGreaterThan(Integer size);

//    @Query(value = "Select distinct c from Company c left join fetch c.employees")
    @Query(value = "SELECT c from Company c")
//    @EntityGraph(attributePaths = {"employees"})
    @EntityGraph("withEmployees")
    List<Company> findAllWithEmployees();

    @Query(value = "select c from Company c where c.id = :id")
    @EntityGraph("withEmployees")
    Optional<Company> findByIdWithEmployees(Long id);
}

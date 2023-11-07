package hu.webuni.hr.npistu.repository;

import hu.webuni.hr.npistu.model.Company;
import hu.webuni.hr.npistu.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    // A company miatt már többet is visszaadhat
//    Optional<Position> findByName(String name);

    Optional<Position> findByNameAndCompany(String name, Company company);
}

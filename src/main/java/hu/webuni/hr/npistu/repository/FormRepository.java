package hu.webuni.hr.npistu.repository;

import hu.webuni.hr.npistu.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FormRepository extends JpaRepository<Form, Long> {

    Optional<Form> findByNameEqualsIgnoreCase(String name);
}

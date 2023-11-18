package hu.webuni.hr.npistu.repository;

import hu.webuni.hr.npistu.enums.TimeoffStatus;
import hu.webuni.hr.npistu.model.Timeoff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TimeoffRepository extends JpaRepository<Timeoff, Long>, JpaSpecificationExecutor<Timeoff> {

    Optional<Timeoff> findByIdAndStatus(Long id, TimeoffStatus status);
}

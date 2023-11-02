package hu.webuni.hr.npistu.repository;

import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {
}

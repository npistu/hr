package hu.webuni.hr.npistu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hu.webuni.hr.npistu.enums.TimeoffStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TimeoffDto(@Positive Long id,
                         EmployeeDto employee,
                         EmployeeDto approver,
                         @JsonFormat(pattern = "yyyy-MM-dd") @Future LocalDate started,
                         @JsonFormat(pattern = "yyyy-MM-dd") @Future LocalDate ended,
                         TimeoffStatus status,
                         @ReadOnlyProperty LocalDateTime createdAt) {

    public TimeoffDto() {
        this(1L, null, null, null, null, null, null);
    }
}
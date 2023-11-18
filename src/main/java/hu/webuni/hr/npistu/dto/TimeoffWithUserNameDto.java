package hu.webuni.hr.npistu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hu.webuni.hr.npistu.enums.TimeoffStatus;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TimeoffWithUserNameDto(@Positive Long id,
                                     String employee,
                                     String approver,
                                     @JsonFormat(pattern = "yyyy-MM-dd") LocalDate started,
                                     @JsonFormat(pattern = "yyyy-MM-dd") LocalDate ended,
                                     TimeoffStatus status,
                                     @ReadOnlyProperty LocalDateTime createdAt) {

    public TimeoffWithUserNameDto() {
        this(1L, null, null, null, null, null, null);
    }
}
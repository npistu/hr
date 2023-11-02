package hu.webuni.hr.npistu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record EmployeeDto(@Positive Long id, @NotEmpty String name, @NotEmpty String job, @Positive Integer salary,
                          @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") @Past LocalDateTime started, PositionDto position) {
    public EmployeeDto() {
        this(1L, null, null, null, null, null);
    }

    public EmployeeDto withId(long id) {
        return new EmployeeDto(id, name(), job(), salary(), started(), position());
    }
}
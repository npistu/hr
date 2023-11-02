package hu.webuni.hr.npistu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record PositionDto(@Positive Long id, @NotEmpty String name, @NotEmpty String minNeededQualification, @Positive Integer minSalary) {
    public PositionDto() {
        this(1L, null, null, null);
    }

    public PositionDto withId(long id) {
        return new PositionDto(id, name(), minNeededQualification(), minSalary());
    }
}
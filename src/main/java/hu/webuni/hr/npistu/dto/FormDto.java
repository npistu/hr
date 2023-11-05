package hu.webuni.hr.npistu.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record FormDto(@Positive Long id, @NotEmpty String name) {
    public FormDto() {
        this(1L, null);
    }

    public FormDto withId(long id) {
        return new FormDto(id, name());
    }
}
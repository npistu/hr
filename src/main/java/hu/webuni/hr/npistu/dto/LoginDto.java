package hu.webuni.hr.npistu.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoginDto(@NotEmpty String username, @NotEmpty String password) {
    public LoginDto() {
        this(null, null);
    }
}

package hu.webuni.hr.npistu.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.Map;

public record CompanyDto(@JsonView(Views.BaseData.class) @Positive Long id,
                         @JsonView(Views.BaseData.class) @NotEmpty String registrationNumber,
                         @JsonView(Views.BaseData.class) @NotEmpty String name,
                         @JsonView(Views.BaseData.class) String address, Map<Long, EmployeeDto> employees) {
    public CompanyDto() {
        this(1L, null, null, null, null);
    }

    public CompanyDto withId(long id) {
        return new CompanyDto(id, registrationNumber(), name(), address(), employees());
    }
}
package hu.webuni.hr.npistu.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.Map;

public class CompanyDto {
    @JsonView(Views.BaseData.class)
    @Positive
    private Long id;
    @JsonView(Views.BaseData.class)
    @NotEmpty
    private String registrationNumber;
    @JsonView(Views.BaseData.class)
    @NotEmpty
    private String name;
    @JsonView(Views.BaseData.class)
    private String address;

    private Map<Long, EmployeeDto> employees;

    public CompanyDto() {
    }

    public CompanyDto(Long id, String registrationNumber, String name, String address, Map<Long, EmployeeDto> employees) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<Long, EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(Map<Long, EmployeeDto> employees) {
        this.employees = employees;
    }
}

package hu.webuni.hr.npistu.model;

import com.fasterxml.jackson.annotation.JsonView;
import hu.webuni.hr.npistu.dto.EmployeeDto;
import hu.webuni.hr.npistu.dto.Views;

import java.util.Map;

public class Company {
    private Long id;
    private String registrationNumber;
    private String name;
    private String address;
    private Map<Long, Employee> employees;

    public Company() {
    }

    public Company(Long id, String registrationNumber, String name, String address, Map<Long, Employee> employees) {
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

    public Map<Long, Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Map<Long, Employee> employees) {
        this.employees = employees;
    }
}

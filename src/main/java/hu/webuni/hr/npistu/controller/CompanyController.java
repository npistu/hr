package hu.webuni.hr.npistu.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import hu.webuni.hr.npistu.dto.AvgSalaryDto;
import hu.webuni.hr.npistu.dto.CompanyDto;
import hu.webuni.hr.npistu.dto.EmployeeDto;
import hu.webuni.hr.npistu.dto.Views;
import hu.webuni.hr.npistu.mapper.CompanyMapper;
import hu.webuni.hr.npistu.mapper.EmployeeMapper;
import hu.webuni.hr.npistu.model.Company;
import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping(params = "full=true")
    public List<CompanyDto> findAllWithEmployees() {
        return companyMapper.companiesToDtos(companyService.findAll());
    }

    @GetMapping()
    @JsonView(Views.BaseData.class)
    public List<CompanyDto> findAllWithoutEmployees() {
        return companyMapper.companiesToDtos(companyService.findAll());
    }

    @GetMapping("/{id}")
    public CompanyDto getById(@PathVariable long id) {
        Company company = companyService.findById(id);

        if (company == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return companyMapper.companyToDto(company);
    }

    @PostMapping
    public CompanyDto create(@RequestBody @Valid CompanyDto companyDto) {
        Company company = companyMapper.dtoToCompany(companyDto);
        Company savedCompany = companyService.create(company);

        if (savedCompany == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return companyMapper.companyToDto(savedCompany);
    }

    @PutMapping("/{id}")
    public CompanyDto update(@PathVariable long id, @RequestBody @Valid CompanyDto companyDto) {
        companyDto = companyDto.withId(id);

        Company company = companyMapper.dtoToCompany(companyDto);
        Company updateCompany = companyService.update(company);

        if (updateCompany == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return companyMapper.companyToDto(updateCompany);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        companyService.delete(id);
    }

    @PutMapping("/{id}/addemployee")
    public CompanyDto addEmployee(@PathVariable long id, @RequestBody @Valid EmployeeDto employeeDto) {
        return companyMapper.companyToDto(companyService.addEmployee(id, employeeMapper.dtoToEmployee(employeeDto)));
    }

    @PutMapping("/{id}/replaceemployees")
    public CompanyDto replaceEmployees(@PathVariable long id, @RequestBody List<EmployeeDto> employeeDtos) {
        return companyMapper.companyToDto(companyService.replaceEmployees(id, employeeMapper.dtosToEmployees(employeeDtos)));
    }

    @DeleteMapping("/{id}/deleteemployee/{employeeId}")
    public CompanyDto deleteEmployee(@PathVariable long id, @PathVariable long employeeId ) {
        return companyMapper.companyToDto(companyService.deleteEmployee(id, employeeId));
    }

    @GetMapping("/salary/{salary}")
    public List<CompanyDto> getByEmployeesSalaryIsGreaterThan(@PathVariable int salary) {
        return companyMapper.companiesToDtos(companyService.getByEmployeesSalaryIsGreaterThan(salary));
    }

    @GetMapping("/employeessize/{size}")
    public List<CompanyDto> getByEmployeesSizeIsGreaterThan(@PathVariable int size) {
        return companyMapper.companiesToDtos(companyService.getByEmployeesSizeIsGreaterThan(size));
    }

    @GetMapping("/{id}/avgsalary")
    public List<AvgSalaryDto> getJobAndAvgSalaryByCompanyId(@PathVariable long id) {
        return companyMapper.avgSalariesToDtos(companyService.getJobAndAvgSalaryByCompanyId(id));
    }
}

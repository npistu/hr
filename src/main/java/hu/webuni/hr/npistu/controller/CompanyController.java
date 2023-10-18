package hu.webuni.hr.npistu.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import hu.webuni.hr.npistu.dto.CompanyDto;
import hu.webuni.hr.npistu.dto.EmployeeDto;
import hu.webuni.hr.npistu.dto.Views;
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

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private Map<Long, EmployeeDto> employees = new HashMap<>();

    {
        employees.put(5L, new EmployeeDto(5L, "Juli", "Takarító nő", 1000, LocalDateTime.parse("2021-01-04 12:00", formatter)));
        employees.put(6L, new EmployeeDto(6L, "Jocó", "Takarító", 2000, LocalDateTime.parse("2021-08-04 12:00", formatter)));
    }

    private Map<Long, CompanyDto> companies = new HashMap<>();

    {
        companies.put(1L, new CompanyDto(1L, "123456", "Teszt Kft", "1000 Teszt, teszt utca 34", employees));
    }


//    @GetMapping
//    public MappingJacksonValue getAll(@RequestParam Optional<Boolean> full) {
//        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(companies.values());
//
//        if (full.isEmpty() || !full.get()) {
//            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("companyDto", SimpleBeanPropertyFilter.serializeAllExcept("employees"));
//
//            mappingJacksonValue.setFilters(filterProvider);
//        }
//
//        return mappingJacksonValue;
//    }

    @GetMapping(params = "full=true")
    public List<CompanyDto> findAllWithEmployees() {
        return new ArrayList<>(companies.values());
    }

    @GetMapping()
    @JsonView(Views.BaseData.class)
    public List<CompanyDto> findAllWithoutEmployees() {
        return new ArrayList<>(companies.values());
    }

    @GetMapping("/all")
    public List<CompanyDto> findAll(@RequestParam Optional<Boolean> full) {
        if (full.orElse(false)) {
            return new ArrayList<>(companies.values());
        }
        else {
            return companies.values().stream().map(this::mapCompanyWithoutEmaployees).toList();
        }
    }

    @GetMapping("/{id}")
    public CompanyDto getById(@PathVariable long id, @RequestParam Optional<Boolean> full) {
        CompanyDto companyDto = getCompanyDtoOrThrow(id);

        if (full.orElse(false)) {
            return companyDto;
        }
        else {
            return mapCompanyWithoutEmaployees(companyDto);
        }
    }

    @PostMapping
    public ResponseEntity<CompanyDto> create(@RequestBody CompanyDto company) {
        if (companies.containsKey(company.getId())) {
            return ResponseEntity.badRequest().build();
        }

        companies.put(company.getId(), company);

        return ResponseEntity.ok(company);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> update(@PathVariable long id, @RequestBody CompanyDto company) {
        if (!companies.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        company.setId(id);
        companies.put(id, company);

        return ResponseEntity.ok(company);
    }

    @PutMapping("/{id}/addemployee")
    public ResponseEntity<CompanyDto> addEmployee(@PathVariable long id, @RequestBody EmployeeDto employee) {
        CompanyDto companyDto = getCompanyDtoOrThrow(id);

        Map<Long, EmployeeDto> employeeDtos = companyDto.getEmployees();
        if (!employeeDtos.containsKey(employee.getId())) {
            employeeDtos.put(employee.getId(), employee);
            companyDto.setEmployees(employeeDtos);
        }

        return ResponseEntity.ok(companyDto);
    }

    @PutMapping("/{id}/replaceemployees")
    public ResponseEntity<CompanyDto> replaceEmployees(@PathVariable long id, @RequestBody Map<Long, EmployeeDto> employees) {
        CompanyDto companyDto = getCompanyDtoOrThrow(id);

        companyDto.setEmployees(employees);

        return ResponseEntity.ok(companyDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        companies.remove(id);
    }

    @DeleteMapping("/{id}/deleteemployee/{employeeId}")
    public ResponseEntity<CompanyDto> deleteEmployee(@PathVariable long id, @PathVariable long employeeId ) {
        CompanyDto companyDto = getCompanyDtoOrThrow(id);

        Map<Long, EmployeeDto> employeeDtos = companyDto.getEmployees();
        if (employeeDtos.containsKey(employeeId)) {
            employeeDtos.remove(employeeId);
            companyDto.setEmployees(employeeDtos);
        }

        return ResponseEntity.ok(companyDto);
    }

    private CompanyDto getCompanyDtoOrThrow(long id) {
        if (!companies.containsKey(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return companies.get(id);
    }

    private CompanyDto mapCompanyWithoutEmaployees(CompanyDto c) {
        return new CompanyDto(c.getId(), c.getRegistrationNumber(), c.getName(), c.getAddress(), null);
    }
}

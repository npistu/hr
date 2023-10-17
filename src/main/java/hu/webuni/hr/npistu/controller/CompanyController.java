package hu.webuni.hr.npistu.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import hu.webuni.hr.npistu.dto.CompanyDto;
import hu.webuni.hr.npistu.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping
    public MappingJacksonValue getAll(@RequestParam Optional<Boolean> full) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(companies.values());

        if (full.isEmpty() || !full.get()) {
            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("companyDto", SimpleBeanPropertyFilter.serializeAllExcept("employees"));

            mappingJacksonValue.setFilters(filterProvider);
        }

        return mappingJacksonValue;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getById(@PathVariable long id) {
        CompanyDto companyDto = companies.get(id);

        if (companyDto == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(companyDto);
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
        if (!companies.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        CompanyDto companyDto = companies.get(id);
        Map<Long, EmployeeDto> employeeDtos = companyDto.getEmployees();
        if (!employees.containsKey(employee.getId())) {
            employeeDtos.put(employee.getId(), employee);
            companyDto.setEmployees(employeeDtos);
        }

        return ResponseEntity.ok(companyDto);
    }

    @PutMapping("/{id}/replaceemployees")
    public ResponseEntity<CompanyDto> replaceEmployees(@PathVariable long id, @RequestBody Map<Long, EmployeeDto> employees) {
        if (!companies.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        CompanyDto companyDto = companies.get(id);
        companyDto.setEmployees(employees);

        return ResponseEntity.ok(companyDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        companies.remove(id);
    }

    @DeleteMapping("/{id}/deleteemployee/{employeeId}")
    public ResponseEntity<CompanyDto> deleteEmployee(@PathVariable long id, @PathVariable long employeeId ) {
        if (!companies.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        CompanyDto companyDto = companies.get(id);
        Map<Long, EmployeeDto> employeeDtos = companyDto.getEmployees();
        if (employeeDtos.containsKey(employeeId)) {
            employeeDtos.remove(employeeId);
            companyDto.setEmployees(employeeDtos);
        }

        return ResponseEntity.ok(companyDto);
    }
}

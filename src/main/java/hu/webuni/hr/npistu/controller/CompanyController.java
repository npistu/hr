package hu.webuni.hr.npistu.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import hu.webuni.hr.npistu.dto.CompanyDto;
import hu.webuni.hr.npistu.dto.EmployeeDto;
import hu.webuni.hr.npistu.util.CompanyMapping;
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
    public ResponseEntity<MappingJacksonValue> getAll(Optional<Boolean> full) {
        List<CompanyDto> companyList = new ArrayList<>(companies.values());

        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept("employees");

        if (full.isPresent() && full.get()) {
            simpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept();
        }

        return ResponseEntity.ok(CompanyMapping.companyListMappingJackson(Optional.of(simpleBeanPropertyFilter), companyList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MappingJacksonValue> getById(@PathVariable long id) {
        CompanyDto companyDto = companies.get(id);

        if (companyDto == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(CompanyMapping.companyMappingJackson(Optional.empty(), companyDto));
        }
    }

    @PostMapping
    public ResponseEntity<MappingJacksonValue> create(@RequestBody CompanyDto company) {
        if (companies.containsKey(company.getId())) {
            return ResponseEntity.badRequest().build();
        }

        companies.put(company.getId(), company);

        return ResponseEntity.ok(CompanyMapping.companyMappingJackson(Optional.empty(), company));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MappingJacksonValue> update(@PathVariable long id, @RequestBody CompanyDto company) {
        if (!companies.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        company.setId(id);
        companies.put(id, company);

        return ResponseEntity.ok(CompanyMapping.companyMappingJackson(Optional.empty(), company));
    }

    @PutMapping("/{id}/addemployee")
    public ResponseEntity<MappingJacksonValue> addEmployee(@PathVariable long id, @RequestBody EmployeeDto employee) {
        if (!companies.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        CompanyDto companyDto = companies.get(id);
        Map<Long, EmployeeDto> employeeDtos = companyDto.getEmployees();
        if (!employees.containsKey(employee.getId())) {
            employeeDtos.put(employee.getId(), employee);
            companyDto.setEmployees(employeeDtos);
        }

        return ResponseEntity.ok(CompanyMapping.companyMappingJackson(Optional.empty(), companyDto));
    }

    @PutMapping("/{id}/replaceemployees")
    public ResponseEntity<MappingJacksonValue> replaceEmployees(@PathVariable long id, @RequestBody Map<Long, EmployeeDto> employees) {
        if (!companies.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        CompanyDto companyDto = companies.get(id);
        companyDto.setEmployees(employees);

        return ResponseEntity.ok(CompanyMapping.companyMappingJackson(Optional.empty(), companyDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        companies.remove(id);
    }

    @DeleteMapping("/{id}/deleteemployee/{employeeId}")
    public ResponseEntity<MappingJacksonValue> deleteEmployee(@PathVariable long id, @PathVariable long employeeId ) {
        if (!companies.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        CompanyDto companyDto = companies.get(id);
        Map<Long, EmployeeDto> employeeDtos = companyDto.getEmployees();
        if (employeeDtos.containsKey(employeeId)) {
            employeeDtos.remove(employeeId);
            companyDto.setEmployees(employeeDtos);
        }

        return ResponseEntity.ok(CompanyMapping.companyMappingJackson(Optional.empty(), companyDto));
    }
}

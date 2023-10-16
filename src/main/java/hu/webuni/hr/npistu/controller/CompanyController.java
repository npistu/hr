package hu.webuni.hr.npistu.controller;

import hu.webuni.hr.npistu.dto.CompanyDto;
import hu.webuni.hr.npistu.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private Map<Long, CompanyDto> companies = new HashMap<>();

    {
        companies.put(1L, new CompanyDto(1L, "123456", "Teszt Kft", "1000 Teszt, teszt utca 34", new ArrayList<>()));
    }


    @GetMapping
    public ResponseEntity<List<CompanyDto>> getAll() {
        return ResponseEntity.ok(new ArrayList<>(companies.values()));
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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        companies.remove(id);
    }
}

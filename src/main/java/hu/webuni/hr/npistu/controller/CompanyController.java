package hu.webuni.hr.npistu.controller;

import hu.webuni.hr.npistu.dto.AvgSalaryDto;
import hu.webuni.hr.npistu.dto.CompanyDto;
import hu.webuni.hr.npistu.dto.EmployeeDto;
import hu.webuni.hr.npistu.mapper.CompanyMapper;
import hu.webuni.hr.npistu.mapper.EmployeeMapper;
import hu.webuni.hr.npistu.model.Company;
import hu.webuni.hr.npistu.repository.CompanyRepository;
import hu.webuni.hr.npistu.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping
    public List<CompanyDto> findAll(@RequestParam Optional<Boolean> full){
        if(full.orElse(false)) {
            return companyMapper.entitiesToDtos(companyRepository.findAllWithEmployees());
        } else {
            return companyMapper.companiesToSummaryDtos(companyService.findAll());
        }
    }

//    @GetMapping(params = "full=true")
//    public List<CompanyDto> findAllWithEmployees() {
//        return companyMapper.entitiesToDtos(companyService.findAll());
//    }
//
//    @GetMapping()
//    @JsonView(Views.BaseData.class)
//    public List<CompanyDto> findAllWithoutEmployees() {
//        return companyMapper.entitiesToDtos(companyService.findAll());
//    }

    @GetMapping("/{id}")
    public CompanyDto getById(@PathVariable long id, @RequestParam Optional<Boolean> full) {
        if(full.orElse(false)) {
            return companyMapper.entityToDto(companyRepository.findByIdWithEmployees(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        } else {

            return companyMapper.companyToSummaryDto(companyService.getCompanyIfExists(id));
        }
    }

    @PostMapping
    public CompanyDto create(@RequestBody @Valid CompanyDto companyDto) {
        Company company = companyMapper.dtoToEntity(companyDto);
        Company savedCompany = companyService.create(company);

        if (savedCompany == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return companyMapper.entityToDto(savedCompany);
    }

    @PutMapping("/{id}")
    public CompanyDto update(@PathVariable long id, @RequestBody @Valid CompanyDto companyDto) {
        companyDto = companyDto.withId(id);

        Company company = companyMapper.dtoToEntity(companyDto);
        Company updateCompany = companyService.update(company);

        if (updateCompany == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return companyMapper.entityToDto(updateCompany);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        companyService.delete(id);
    }

    @PutMapping("/{id}/addemployee")
    public CompanyDto addEmployee(@PathVariable long id, @RequestBody @Valid EmployeeDto employeeDto) {
        return companyMapper.entityToDto(companyService.addEmployee(id, employeeMapper.dtoToEntity(employeeDto)));
    }

    @PutMapping("/{id}/replaceemployees")
    public CompanyDto replaceEmployees(@PathVariable long id, @RequestBody List<EmployeeDto> employeeDtos) {
        return companyMapper.entityToDto(companyService.replaceEmployees(id, employeeMapper.dtosToEntities(employeeDtos)));
    }

    @DeleteMapping("/{id}/deleteemployee/{employeeId}")
    public CompanyDto deleteEmployee(@PathVariable long id, @PathVariable long employeeId ) {
        return companyMapper.entityToDto(companyService.deleteEmployee(id, employeeId));
    }

    @GetMapping("/salary/{salary}")
    public List<CompanyDto> getByEmployeesSalaryIsGreaterThan(@PathVariable int salary) {
        return companyMapper.entitiesToDtos(companyService.getByEmployeesSalaryIsGreaterThan(salary));
    }

    @GetMapping("/employeessize/{size}")
    public List<CompanyDto> getByEmployeesSizeIsGreaterThan(@PathVariable int size) {
        return companyMapper.entitiesToDtos(companyService.getByEmployeesSizeIsGreaterThan(size));
    }

    @GetMapping("/{id}/avgsalary")
    public List<AvgSalaryDto> getJobAndAvgSalaryByCompanyId(@PathVariable long id) {
        return companyMapper.avgSalariesToDtos(companyService.getJobAndAvgSalaryByCompanyId(id));
    }
}

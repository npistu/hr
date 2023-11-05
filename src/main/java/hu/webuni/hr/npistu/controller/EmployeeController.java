package hu.webuni.hr.npistu.controller;

import hu.webuni.hr.npistu.dto.EmployeeDto;
import hu.webuni.hr.npistu.mapper.EmployeeMapper;
import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping("/payraisepercent")
    public ResponseEntity<String> getPayRaisePercent(@RequestBody Employee employee){
        if (employee.getStarted() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (employee.getStarted().isAfter(LocalDateTime.now())) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(employeeService.getPayRaisePercent(employee) + " %");
    }

    @GetMapping
    public List<EmployeeDto> findAll() {
        return employeeMapper.entitiesToDtos(employeeService.findAll());
    }

    @GetMapping("/withpage")
    public Page<EmployeeDto> findAllWithPage(@PageableDefault(size = 5) Pageable pageable) {
        return employeeMapper.pageEmployeesToPageDtos(employeeService.findAllWithPage(pageable));
    }

    @GetMapping("/{id}")
    public EmployeeDto findById(@PathVariable long id) {
        Employee employee = employeeService.findById(id);

        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return employeeMapper.entityToDto(employee);
    }

    @PostMapping
    public EmployeeDto create(@RequestBody @Valid EmployeeDto employeeDto) {
        Employee employee = employeeMapper.dtoToEntity(employeeDto);
        Employee saveEmployee = employeeService.create(employee);

        if (saveEmployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return employeeMapper.entityToDto(saveEmployee);
    }

    @PutMapping("/{id}")
    public EmployeeDto update(@PathVariable long id, @RequestBody @Valid EmployeeDto employeeDto) {
        employeeDto = employeeDto.withId(id);

        Employee employee = employeeMapper.dtoToEntity(employeeDto);
        Employee updateEmployee = employeeService.update(employee);

        if (updateEmployee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return employeeMapper.entityToDto(updateEmployee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        employeeService.delete(id);
    }

    @GetMapping("/job/{job}")
    public List<Employee> findByJob(@PathVariable String job) {
        return employeeService.findByJob(job);
    }

    @GetMapping("/name/{name}")
    public List<Employee> findByNameStartingWithIgnoreCase(@PathVariable String name) {
        return employeeService.findByNameStartingWithIgnoreCase(name);
    }

    @GetMapping("/start/{started}/{end}")
    public List<Employee> findByStartedBetween(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime started,
                                               @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return employeeService.findByStartedBetween(started, end);
    }

}

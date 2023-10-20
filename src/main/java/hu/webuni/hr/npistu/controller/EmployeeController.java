package hu.webuni.hr.npistu.controller;

import hu.webuni.hr.npistu.dto.EmployeeDto;
import hu.webuni.hr.npistu.mapper.EmployeeMapper;
import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        return employeeMapper.employeesToDtos(employeeService.findAll());
    }

    @GetMapping("/{id}")
    public EmployeeDto findById(@PathVariable long id) {
        Employee employee = employeeService.findById(id);

        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return employeeMapper.employeeToDto(employee);
    }

    @PostMapping
    public EmployeeDto create(@RequestBody @Valid EmployeeDto employeeDto) {
        Employee employee = employeeMapper.dtoToEmployee(employeeDto);
        Employee saveEmployee = employeeService.create(employee);

        if (saveEmployee == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return employeeMapper.employeeToDto(saveEmployee);
    }

    @PutMapping("/{id}")
    public EmployeeDto update(@PathVariable long id, @RequestBody @Valid EmployeeDto employeeDto) {
        employeeDto.setId(id);

        Employee employee = employeeMapper.dtoToEmployee(employeeDto);
        Employee updateEmployee = employeeService.update(employee);

        if (updateEmployee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return employeeMapper.employeeToDto(updateEmployee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        employeeService.delete(id);
    }
}

package hu.webuni.hr.npistu.controller;

import hu.webuni.hr.npistu.dto.EmployeeDto;
import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

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
}

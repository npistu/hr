package hu.webuni.hr.npistu.controller;

import hu.webuni.hr.npistu.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class HrController {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private Map<Long, EmployeeDto> employees = new HashMap<>();

    {
        employees.put(5L, new EmployeeDto(5L, "Juli", "Takarító nő", 1000, LocalDateTime.parse("2021-01-04 12:00", formatter)));
        employees.put(6L, new EmployeeDto(6L, "Jocó", "Takarító", 2000, LocalDateTime.parse("2021-08-04 12:00", formatter)));
    }


    @GetMapping
    public ResponseEntity<List<EmployeeDto>> findAll(@RequestParam(required = false) Integer salary) {
        if (salary == null) {
            return ResponseEntity.ok(new ArrayList<>(employees.values()));
        }
        else {
            List<EmployeeDto> result = new ArrayList<>();

            for (EmployeeDto value: employees.values()) {
                if (value.getSalary() >= salary) {
                    result.add(value);
                }
            }

            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable long id) {
        EmployeeDto employeeDto = employees.get(id);

        if (employeeDto == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(employeeDto);
        }
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> create(@RequestBody EmployeeDto employee) {
        if (employees.containsKey(employee.getId())) {
            return ResponseEntity.badRequest().build();
        }

        employees.put(employee.getId(), employee);

        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable long id, @RequestBody EmployeeDto employee) {
        if (!employees.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        employee.setId(id);
        employees.put(id, employee);

        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        employees.remove(id);
    }
}

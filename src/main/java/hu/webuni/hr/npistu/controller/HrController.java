package hu.webuni.hr.npistu.controller;

import hu.webuni.hr.npistu.dto.EmployeeDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/employee")
public class HrController {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private Map<Long, EmployeeDto> employees = new HashMap<>();

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> findAll(Optional<Integer> salary) {
        return salary.map(integer -> ResponseEntity.ok(employees.values().stream().filter(employeeDto -> employeeDto.salary() > integer).toList())).orElseGet(() -> ResponseEntity.ok(new ArrayList<>(employees.values())));
    }

    @GetMapping(params = "minSalary")
    public ResponseEntity<List<EmployeeDto>> findBySalary(@RequestParam int minSalary) {
        return ResponseEntity.ok(employees.values().stream().filter(employeeDto -> employeeDto.salary() > minSalary).toList());
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
    public ResponseEntity<EmployeeDto> create(@RequestBody @Valid EmployeeDto employee) {
        if (employees.containsKey(employee.id())) {
            return ResponseEntity.badRequest().build();
        }

        employees.put(employee.id(), employee);

        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable long id, @RequestBody @Valid EmployeeDto employee) {
        if (!employees.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        employee = employee.withId(id);
        employees.put(id, employee);

        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        employees.remove(id);
    }
}

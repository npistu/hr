package hu.webuni.hr.npistu.controller;

import hu.webuni.hr.npistu.dto.EmployeeDto;
import hu.webuni.hr.npistu.model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HrTLController {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private Map<Long, Employee> employees = new HashMap<>();

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        model.put("employees", employees.values().stream().toList());
        model.put("newEmployee", new Employee());

        return "index";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(Map<String, Object> model, @PathVariable Long id) {
        model.put("editEmployee", employees.get(id));

        return "edit";
    }

    @PostMapping("/employee")
    public String createEmployee(@Valid Employee employee) {
        employees.put(employee.getId(), employee);

        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(@Valid Employee employee) {
        employees.put(employee.getId(), employee);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        employees.remove(id);

        return "redirect:/";
    }
}

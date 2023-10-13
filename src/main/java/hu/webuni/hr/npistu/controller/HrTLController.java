package hu.webuni.hr.npistu.controller;

import hu.webuni.hr.npistu.model.Employee;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HrTLController {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private List<Employee> employees = new ArrayList<>();

    {
        employees.add(new Employee(5L, "Juli", "Takarító nő", 1000, LocalDateTime.parse("2021-01-04 12:00", formatter)));
        employees.add(new Employee(6L, "Jocó", "Takarító", 2000, LocalDateTime.parse("2021-08-04 12:00", formatter)));
    }

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        model.put("employees", employees);
        model.put("newEmployee", new Employee());

        return "index";
    }

    @PostMapping("/employee")
    public String createEmployee(@Valid Employee employee) {
        employee.setStarted(LocalDateTime.now());
        employees.add(employee);

        return "redirect:/";
    }
}

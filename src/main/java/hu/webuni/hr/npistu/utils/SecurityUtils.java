package hu.webuni.hr.npistu.utils;

import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.repository.EmployeeRepository;
import hu.webuni.hr.npistu.security.EmployeeUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Employee getCurrentEmployee(EmployeeRepository employeeRepository) {
        return ((EmployeeUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmployee();
    }
}

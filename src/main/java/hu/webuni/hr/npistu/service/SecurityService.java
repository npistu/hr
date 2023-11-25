package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.repository.EmployeeRepository;
import hu.webuni.hr.npistu.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee getCurrentEmployee() {
        return SecurityUtils.getCurrentEmployee(employeeRepository);
    }
}

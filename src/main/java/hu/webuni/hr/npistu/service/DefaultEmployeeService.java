package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class DefaultEmployeeService implements EmployeeService{
    @Override
    public int getPayRaisePercent(Employee employee) {
        return 5;
    }
}

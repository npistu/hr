package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaryService {

    @Autowired
    private EmployeeService employeeService;

    public void setNewSalary(Employee employee) {
        employee.setSalary((int) (employee.getSalary()*((employeeService.getPayRaisePercent(employee)/100.0)+1)));
    }
}

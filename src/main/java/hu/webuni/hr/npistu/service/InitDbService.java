package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.model.Company;
import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.repository.CompanyRepository;
import hu.webuni.hr.npistu.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InitDbService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public void clearDB() {
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    public void insertTestData() {
        Company company1 = new Company("reg001", "company 01", "address 01");
        company1 = companyRepository.saveAndFlush(company1);
        Company company2 = new Company("reg002", "company 02", "address 02");
        company2 = companyRepository.saveAndFlush(company2);

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Employee01", "job01", 1000, LocalDateTime.of(2020, 1, 1, 1, 1, 1), company1));
        employees.add(new Employee("Employee02", "job02", 2000, LocalDateTime.of(2023, 1, 1, 1, 1, 1), company1));
        employees.add(new Employee("Employee03", "job03", 2000, LocalDateTime.of(2022, 1, 1, 1, 1, 1), company2));
        employees.add(new Employee("Employee04", "job02", 3000, LocalDateTime.of(2019, 1, 1, 1, 1, 1), company2));
        employees.add(new Employee("Employee05", "job05", 4000, LocalDateTime.of(2016, 1, 1, 1, 1, 1), company2));

        employeeRepository.saveAll(employees);
    }
}

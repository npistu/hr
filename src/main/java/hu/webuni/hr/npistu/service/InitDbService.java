package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.model.Company;
import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.model.Form;
import hu.webuni.hr.npistu.model.Position;
import hu.webuni.hr.npistu.repository.CompanyRepository;
import hu.webuni.hr.npistu.repository.EmployeeRepository;
import hu.webuni.hr.npistu.repository.FormRepository;
import hu.webuni.hr.npistu.repository.PositionRepository;
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

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    FormRepository formRepository;

    public void clearDB() {
        employeeRepository.deleteAllInBatch();
        positionRepository.deleteAllInBatch();
        companyRepository.deleteAllInBatch();
        formRepository.deleteAllInBatch();
    }

    public void insertTestData() {
        Form form1 = formRepository.saveAndFlush(new Form("Corporation"));
        Form form2 = formRepository.saveAndFlush( new Form("LLC"));

        Company company1 = companyRepository.saveAndFlush(new Company("re01", "comp01", "address 01", form1));
        Company company2 = companyRepository.saveAndFlush(new Company("reg002", "ompany 02", "address 02", form2));

        Position position1 = positionRepository.saveAndFlush(new Position("Position01", "none", 1000, company1));
        Position position2 = positionRepository.saveAndFlush(new Position("Position02", "college", 2000, company1));
        Position position3 = positionRepository.saveAndFlush(new Position("Position01", "college", 3000, company2));
        Position position4 = positionRepository.saveAndFlush(new Position("Position03", "university", 4000, company2));
        Position position5 = positionRepository.saveAndFlush(new Position("Position02", "PhD", 5000, company2));

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Employee01", "Job1",1000, LocalDateTime.of(2020, 1, 1, 1, 1, 1), company1, position1));
        employees.add(new Employee("Employee02", "Job2",2000, LocalDateTime.of(2023, 1, 1, 1, 1, 1), company1, position2));
        employees.add(new Employee("Employee03", "Job3",2000, LocalDateTime.of(2022, 1, 1, 1, 1, 1), company2, position5));
        employees.add(new Employee("Employee04", "Job1",3000, LocalDateTime.of(2019, 1, 1, 1, 1, 1), company2, position3));
        employees.add(new Employee("Employee05", "Job3",4000, LocalDateTime.of(2016, 1, 1, 1, 1, 1), company2, position3));
        employees.add(new Employee("Employee06", "Job2",5500, LocalDateTime.of(2012, 1, 1, 1, 1, 1), company2, position5));
        employees.add(new Employee("Employee07", "Job1",5000, LocalDateTime.of(2021, 1, 1, 1, 1, 1), company2, position4));
        employees.add(new Employee("Employee08", "Job5",7000, LocalDateTime.of(2013, 1, 1, 1, 1, 1), company2, position5));

        employeeRepository.saveAll(employees);
    }
}

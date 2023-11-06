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
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
        positionRepository.deleteAll();
        formRepository.deleteAll();
    }

    public void insertTestData() {
        Form form1 = new Form("Corporation");
        form1 = formRepository.saveAndFlush(form1);
        Form form2 = new Form("LLC");
        form2 = formRepository.saveAndFlush(form2);


        Company company1 = new Company("reg001", "company 01", "address 01", form1);
        company1 = companyRepository.saveAndFlush(company1);
        Company company2 = new Company("reg002", "company 02", "address 02", form2);
        company2 = companyRepository.saveAndFlush(company2);

        Position position1 = new Position("Position01", "none", 1000);
        position1 = positionRepository.saveAndFlush(position1);
        Position position2 = new Position("Position02", "high school", 2000);
        position2 = positionRepository.saveAndFlush(position2);
        Position position3 = new Position("Position03", "college", 3000);
        position3 = positionRepository.saveAndFlush(position3);
        Position position4 = new Position("Position04", "university", 4000);
        position4 = positionRepository.saveAndFlush(position4);
        Position position5 = new Position("Position05", "PhD", 5000);
        position5 = positionRepository.saveAndFlush(position5);

        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Employee01", "Job1",1000, LocalDateTime.of(2020, 1, 1, 1, 1, 1), company1, position5));
        employees.add(new Employee("Employee02", "Job2",2000, LocalDateTime.of(2023, 1, 1, 1, 1, 1), company1, position2));
        employees.add(new Employee("Employee03", "Job3",2000, LocalDateTime.of(2022, 1, 1, 1, 1, 1), company2, position1));
        employees.add(new Employee("Employee04", "Job1",3000, LocalDateTime.of(2019, 1, 1, 1, 1, 1), company2, position2));
        employees.add(new Employee("Employee05", "Job3",4000, LocalDateTime.of(2016, 1, 1, 1, 1, 1), company2, position1));
        employees.add(new Employee("Employee06", "Job2",5500, LocalDateTime.of(2012, 1, 1, 1, 1, 1), company2, position5));
        employees.add(new Employee("Employee07", "Job1",5000, LocalDateTime.of(2021, 1, 1, 1, 1, 1), company2, position4));
        employees.add(new Employee("Employee08", "Job5",7000, LocalDateTime.of(2013, 1, 1, 1, 1, 1), company2, position5));

        employeeRepository.saveAll(employees);
    }
}

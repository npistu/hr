package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.enums.TimeoffStatus;
import hu.webuni.hr.npistu.model.*;
import hu.webuni.hr.npistu.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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

    @Autowired
    TimeoffRepository timeoffRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void clearDB() {
        timeoffRepository.deleteAllInBatch();
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
        Employee employee1 = new Employee("abEmployee01", "Job1", 1000, LocalDateTime.of(2020, 1, 1, 1, 1, 1), company1, position1, null, "emp01", passwordEncoder.encode("pass"), new HashSet<>(Arrays.asList("user", "admin")), null);
        employees.add(employee1);
        Employee employee2 = new Employee("abEmployee02", "Job2", 2000, LocalDateTime.of(2023, 1, 1, 1, 1, 1), company1, position2, null, "emp02", passwordEncoder.encode("pass"), new HashSet<>(Arrays.asList("user", "admin")), null);
        employees.add(employee2);
        employees.add(new Employee("abEmployee03", "Job3",2000, LocalDateTime.of(2022, 1, 1, 1, 1, 1), company2, position5, null, "emp03", passwordEncoder.encode("pass"), new HashSet<>(List.of("user")), employee1));
        employees.add(new Employee("cdEmployee04", "Job1",3000, LocalDateTime.of(2019, 1, 1, 1, 1, 1), company2, position3, null, "emp04", passwordEncoder.encode("pass"), new HashSet<>(List.of("user")), employee1));
        employees.add(new Employee("cdEmployee05", "Job3",4000, LocalDateTime.of(2016, 1, 1, 1, 1, 1), company2, position3, null, "emp05", passwordEncoder.encode("pass"), new HashSet<>(List.of("user")), employee1));
        employees.add(new Employee("efEmployee06", "Job2",5500, LocalDateTime.of(2012, 1, 1, 1, 1, 1), company2, position5, null, "emp06", passwordEncoder.encode("pass"), new HashSet<>(List.of("user")), employee2));
        employees.add(new Employee("efEmployee07", "Job1",5000, LocalDateTime.of(2021, 1, 1, 1, 1, 1), company2, position4, null, "emp07", passwordEncoder.encode("pass"), new HashSet<>(List.of("user")), employee2));
        employees.add(new Employee("ghEmployee08", "Job5",7000, LocalDateTime.of(2013, 1, 1, 1, 1, 1), company2, position5, null, "emp08", passwordEncoder.encode("pass"), new HashSet<>(List.of("user")), employee2));

        employeeRepository.saveAll(employees);

        timeoffRepository.save(new Timeoff(employee1, employee2, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 5), TimeoffStatus.requested));
        timeoffRepository.save(new Timeoff(employee1, employee2, LocalDate.of(2024, 3, 10), LocalDate.of(2024, 3, 11), TimeoffStatus.accepted));
        timeoffRepository.save(new Timeoff(employee1, employee2, LocalDate.of(2024, 2, 25), LocalDate.of(2024, 3, 4), TimeoffStatus.denied));
    }
}

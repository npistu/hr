package hu.webuni.hr.npistu.service;


import hu.webuni.hr.npistu.model.Company;
import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.model.Form;
import hu.webuni.hr.npistu.model.Position;
import hu.webuni.hr.npistu.repository.CompanyRepository;
import hu.webuni.hr.npistu.repository.EmployeeRepository;
import hu.webuni.hr.npistu.repository.FormRepository;
import hu.webuni.hr.npistu.repository.PositionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
public class EmployeeServiceITTest {

    @Autowired
    InitDbService initDbService;

    @Autowired
    FormRepository formRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    @BeforeEach
    public void init() {
        initDbService.clearDB();
    }

    @Test
    @Transactional
    void testEmployeeSearchByStarted() {
        Form form1 = formRepository.saveAndFlush(new Form("Corporation"));

        Company company1 = companyRepository.saveAndFlush(new Company("ABC01", "ABC01", "address 01", form1));
        Company company2 = companyRepository.saveAndFlush(new Company("ADE01", "ADE01", "address 02", form1));

        Position position1 = positionRepository.saveAndFlush(new Position("Pos01", "none", 1000, company1));

        Employee employee1 = employeeRepository.saveAndFlush(new Employee("ABC01", "Job1",2000, LocalDateTime.of(2023, 10, 1, 1, 1, 1), company1, position1));
        Employee employee2 = employeeRepository.saveAndFlush(new Employee("DEF01", "Job2",2040, LocalDateTime.of(2023, 10, 1, 1, 1, 1), company1, position1));
        Employee employee3 = employeeRepository.saveAndFlush(new Employee("DEF01", "Job2",3000, LocalDateTime.of(2022, 9, 1, 1, 1, 1), company2, position1));
        Employee employee4 = employeeRepository.saveAndFlush(new Employee("DEF01", "Job2",4000, LocalDateTime.of(2020, 1, 1, 1, 1, 1), company2, position1));

        Employee example = new Employee(null, null, null,  LocalDateTime.of(2022, 9, 1, 1, 1, 1), null, null);

        List<Employee> foundEmployees = employeeService.findEmployeesBySpecification(example);

        assertThat(foundEmployees).containsExactlyInAnyOrder(employee3);
    }

    @Test
    @Transactional
    void testEmployeeSearchByPositionAndCompany() {
        Form form1 = formRepository.saveAndFlush(new Form("Corporation"));

        Company company1 = companyRepository.saveAndFlush(new Company("ABC01", "ABC01", "address 01", form1));
        Company company2 = companyRepository.saveAndFlush(new Company("ADE01", "ADE01", "address 02", form1));

        Position position1 = positionRepository.saveAndFlush(new Position("Pos01", "none", 1000, company1));

        Employee employee1 = employeeRepository.saveAndFlush(new Employee("ABC01", "Job1",2000, LocalDateTime.of(2023, 10, 1, 1, 1, 1), company1, position1));
        Employee employee2 = employeeRepository.saveAndFlush(new Employee("DEF01", "Job2",2040, LocalDateTime.of(2023, 10, 1, 1, 1, 1), company1, position1));
        Employee employee3 = employeeRepository.saveAndFlush(new Employee("DEF01", "Job2",3000, LocalDateTime.of(2022, 9, 1, 1, 1, 1), company2, position1));
        Employee employee4 = employeeRepository.saveAndFlush(new Employee("DEF01", "Job2",4000, LocalDateTime.of(2020, 1, 1, 1, 1, 1), company2, position1));

        Employee example = new Employee(null, null, null,  null, new Company(null, "AB", null, null), new Position("Pos01", null, null, null));

        List<Employee> foundEmployees = employeeService.findEmployeesBySpecification(example);

        assertThat(foundEmployees).containsExactlyInAnyOrder(employee1, employee2);
    }

    @Test
    @Transactional
    void testEmployeeSearchByCompanyAndSalary() {
        Form form1 = formRepository.saveAndFlush(new Form("Corporation"));

        Company company1 = companyRepository.saveAndFlush(new Company("ABC01", "ABC01", "address 01", form1));
        Company company2 = companyRepository.saveAndFlush(new Company("ADE01", "ADE01", "address 02", form1));

        Position position1 = positionRepository.saveAndFlush(new Position("Pos01", "none", 1000, company1));

        Employee employee1 = employeeRepository.saveAndFlush(new Employee("ABC01", "Job1",2000, LocalDateTime.of(2023, 10, 1, 1, 1, 1), company1, position1));
        Employee employee2 = employeeRepository.saveAndFlush(new Employee("DEF01", "Job2",2040, LocalDateTime.of(2023, 10, 1, 1, 1, 1), company1, position1));
        Employee employee3 = employeeRepository.saveAndFlush(new Employee("DEF01", "Job2",3000, LocalDateTime.of(2022, 9, 1, 1, 1, 1), company2, position1));
        Employee employee4 = employeeRepository.saveAndFlush(new Employee("DEF01", "Job2",3202, LocalDateTime.of(2020, 1, 1, 1, 1, 1), company2, position1));

        Employee example = new Employee(null, null, 3050,  null, new Company(null, "AD", null, null), null);

        List<Employee> foundEmployees = employeeService.findEmployeesBySpecification(example);

        assertThat(foundEmployees).containsExactlyInAnyOrder(employee3, employee4);
    }
}

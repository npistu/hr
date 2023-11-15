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
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
public class CompanyServiceITTest {

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
    CompanyService companyService;

    @BeforeEach
    public void init() {
        initDbService.clearDB();
    }

    @Test
    @Transactional
    void addEmployee() {
        Form form1 = formRepository.saveAndFlush(new Form("Corporation"));
        Company company1 = companyRepository.saveAndFlush(new Company("reg001", "company 01", "address 01", form1));
        Position position1 = positionRepository.saveAndFlush(new Position("Position01", "none", 1000, company1));
        Employee employee1 = employeeRepository.saveAndFlush(new Employee("Employee01", "Job1",1000, LocalDateTime.of(2020, 1, 1, 1, 1, 1), company1, position1));
        Employee employee2 = employeeRepository.saveAndFlush(new Employee("Employee02", "Job2",1000, LocalDateTime.of(2020, 1, 1, 1, 1, 1), company1, position1));

        company1 = companyService.addEmployee(company1.getId(), employee1);
        company1 = companyService.addEmployee(company1.getId(), employee2);

        companyRepository.findById(company1.getId()).ifPresent(savedCompany -> {
            assertThat(savedCompany.getEmployees().size()).isEqualTo(2);
        });
    }

    @Test
    @Transactional
    void deleteEmployee() {
        Form form1 = formRepository.saveAndFlush(new Form("Corporation"));
        Company company1 = companyRepository.saveAndFlush(new Company("reg001", "company 01", "address 01", form1));
        Position position1 = positionRepository.saveAndFlush(new Position("Position01", "none", 1000, company1));
        Employee employee1 = employeeRepository.saveAndFlush(new Employee("Employee01", "Job1",1000, LocalDateTime.of(2020, 1, 1, 1, 1, 1), company1, position1));
        Employee employee2 = employeeRepository.saveAndFlush(new Employee("Employee02", "Job2",1000, LocalDateTime.of(2020, 1, 1, 1, 1, 1), company1, position1));

        company1 = companyService.addEmployee(company1.getId(), employee1);
        company1 = companyService.addEmployee(company1.getId(), employee2);

        company1 = companyService.deleteEmployee(company1.getId(), employee1.getId());

        companyRepository.findById(company1.getId()).ifPresent(savedCompany -> {
            assertThat(savedCompany.getEmployees().size()).isEqualTo(1);
            assertThat(savedCompany.getEmployees().get(0).getId()).isEqualTo(employee2.getId());
            assertThat(savedCompany.getEmployees().get(0).getName()).isEqualTo("Employee02");
        });
    }

    @Test
    @Transactional
    void replaceEmployees() {
        Form form1 = formRepository.saveAndFlush(new Form("Corporation"));
        Company company1 = companyRepository.saveAndFlush(new Company("reg001", "company 01", "address 01", form1));
        Position position1 = positionRepository.saveAndFlush(new Position("Position01", "none", 1000, company1));
        Employee employee1 = employeeRepository.saveAndFlush(new Employee("Employee01", "Job1",1000, LocalDateTime.of(2020, 1, 1, 1, 1, 1), company1, position1));
        Employee employee2 = employeeRepository.saveAndFlush(new Employee("Employee02", "Job2",1000, LocalDateTime.of(2020, 1, 1, 1, 1, 1), company1, position1));
        Employee employee3 = employeeRepository.saveAndFlush(new Employee("Employee03", "Job3",1000, LocalDateTime.of(2020, 1, 1, 1, 1, 1), company1, position1));

        company1 = companyService.addEmployee(company1.getId(), employee1);
        company1 = companyService.addEmployee(company1.getId(), employee2);

        company1 = companyService.replaceEmployees(company1.getId(), Collections.singletonList(employee3));

        companyRepository.findById(company1.getId()).ifPresent(savedCompany -> {
            assertThat(savedCompany.getEmployees().size()).isEqualTo(1);
            assertThat(savedCompany.getEmployees().get(0).getId()).isEqualTo(employee3.getId());
            assertThat(savedCompany.getEmployees().get(0).getJob()).isEqualToIgnoringCase("job3");
            assertThat(savedCompany.getEmployees().get(0).getJob()).isNotEqualTo("Job1");
        });
    }
}

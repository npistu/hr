package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.dto.CompanyDto;
import hu.webuni.hr.npistu.dto.EmployeeDto;
import hu.webuni.hr.npistu.exception.NonUniqueIdException;
import hu.webuni.hr.npistu.model.Company;
import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EmployeeService employeeService;

    public Company create(Company company) {
        return save(company);
    }

    public Company update(Company company) {
        if (findById(company.getId()) == null) {
            return null;
        }

        return save(company);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findById(long id) {
        return companyRepository.findById(id).orElse(null);
    }

    public void delete(long id) {
        companyRepository.deleteById(id);
    }

    @Transactional
    public Company addEmployee(long companyId, Employee employee) {
        Company company = getCompanyIfExists(companyId);

        Employee storedEmployee = getStoredEmployee(employee, company);

        return company;
    }

//TODO Még a törlés nem stimmel
    @Transactional
    public Company replaceEmployees(long companyId, List<Employee> employees) {
        Company company = getCompanyIfExists(companyId);

        List<Employee> storedEmployees = new ArrayList<>();

        for (Employee employee: employees) {
            storedEmployees.add(getStoredEmployee(employee, company));
        }

        company.setEmployees(storedEmployees);

        return save(company);
    }

//TODO ez még nincs kész
    @Transactional
    public Company deleteEmployee(long companyId, long employeeId ) {
        Company company = getCompanyIfExists(companyId);

        Optional<Employee> optional = company.getEmployees().stream().filter(employee -> employee.getId() == employeeId).findFirst();

        if (optional.isPresent()) {
            List<Employee> employees = company.getEmployees();

            employees.remove(optional.get());

            company.setEmployees(employees);

            save(company);
        }

        return company;
    }

    private Company save(Company company) {
        return companyRepository.save(company);
    }

    private Company getCompanyIfExists(long companyId) {
        Company company = findById(companyId);

        if (company == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return company;
    }

    private Employee getStoredEmployee(Employee employee, Company company) {
        Employee storedEmployee = null;

        if (employee.getId() != null) {
            storedEmployee = employeeService.findById(employee.getId());
        }

        if (storedEmployee == null) {
            employee.setCompany(company);
            storedEmployee = employeeService.create(employee);
        }

        return storedEmployee;
    }

}

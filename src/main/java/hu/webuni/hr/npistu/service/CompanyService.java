package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.model.Company;
import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EmployeeService employeeService;

    @Transactional
    public Company create(Company company) {
        return save(company);
    }

    @Transactional
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

    @Transactional
    public void delete(long id) {
        companyRepository.deleteById(id);
    }

    @Transactional
    public Company addEmployee(long companyId, Employee employee) {
        Company company = getCompanyIfExists(companyId);

        getStoredEmployee(employee, company);

        return company;
    }

    @Transactional
    public Company replaceEmployees(long companyId, List<Employee> employees) {
        Company company = getCompanyIfExists(companyId);

        List<Employee> actualEmployees = company.getEmployees();

        if (actualEmployees != null) {
            for (Employee employee: actualEmployees) {
                employee.setCompany(null);
                employeeService.update(employee);
            }
        }

        List<Employee> storeEmployees = new ArrayList<>();

        if (employees != null) {
            for (Employee employee: employees) {
                storeEmployees.add(getStoredEmployee(employee, company));
            }
        }

        company.setEmployees(storeEmployees);

        return save(company);
    }

    @Transactional
    public Company deleteEmployee(long companyId, long employeeId ) {
        Company company = getCompanyIfExists(companyId);

        employeeService.delete(employeeId);

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

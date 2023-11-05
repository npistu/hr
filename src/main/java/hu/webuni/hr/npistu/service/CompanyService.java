package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.model.Company;
import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.model.Form;
import hu.webuni.hr.npistu.repository.CompanyRepository;
import hu.webuni.hr.npistu.repository.EmployeeRepository;
import hu.webuni.hr.npistu.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    FormRepository formRepository;

    @Transactional
    public Company create(Company company) {
        return save(company);
    }

    @Transactional
    public Company update(Company company) {
        if (!companyRepository.existsById(company.getId())) {
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

        company.addEmployee(employee);
        employeeService.create(employee);

        return company;
    }

    @Transactional
    public Company replaceEmployees(long companyId, List<Employee> employees) {
        Company company = getCompanyIfExists(companyId);

        company.getEmployees().forEach(employee -> employee.setCompany(null));
        company.getEmployees().clear();

        employees.forEach(employee -> {
            company.addEmployee(employee);
            employeeService.create(employee);
        });

        return company;
    }

    @Transactional
    public Company deleteEmployee(long companyId, long employeeId ) {
        Company company = getCompanyIfExists(companyId);

        Employee employee = employeeService.findById(employeeId);
        employee.setCompany(null);
        company.getEmployees().remove(employee);
        employeeService.update(employee);

        return company;
    }

    public List<Company> getByEmployeesSalaryIsGreaterThan(Integer salary) {
        return companyRepository.findByEmployees_SalaryIsGreaterThan(salary);
    }

    public List<Company> getByEmployeesSizeIsGreaterThan(Integer size) {
        return companyRepository.findByEmployees_sizeIsGreaterThan(size);
    }

    public List<Object[]> getJobAndAvgSalaryByCompanyId(Long companyId) {
        return employeeRepository.findJobAndAvgSalaryByCompany_Id(companyId);
    }

    private Company save(Company company) {
        Optional<Form> optional = formRepository.findByNameEqualsIgnoreCase(company.getForm().getName());

        if (optional.isPresent()) {
            company.setForm(optional.get());
        }
        else {
            company.setForm(formRepository.saveAndFlush(company.getForm()));
        }

        return companyRepository.saveAndFlush(company);
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

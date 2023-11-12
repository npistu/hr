package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.model.Company;
import hu.webuni.hr.npistu.model.Position;
import hu.webuni.hr.npistu.repository.CompanyRepository;
import hu.webuni.hr.npistu.repository.EmployeeRepository;
import hu.webuni.hr.npistu.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Transactional
    public void setNewMinSalary(String name, Integer minSalary) {
//        Position position = positionRepository.findByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        position.setMinSalary(minSalary);
//        position = positionRepository.saveAndFlush(position);

        /*Első megoldás*/
//        List<Employee> employees = employeeRepository.findByPositionAndSalaryLessThan(position, minSalary);
//
//        employees.forEach(employee -> {
//            employee.setSalary(minSalary);
//            employeeRepository.save(employee);
//        });
        /*Második megoldás*/

//        employeeRepository.updateByPositionAndSalaryLessThan(position, minSalary);
    }

    @Transactional
    public void setNewMinSalaryByCompany(String name, Integer minSalary, Long companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Position position = positionRepository.findByNameAndCompany(name, company).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        position.setMinSalary(minSalary);
        position = positionRepository.saveAndFlush(position);

        employeeRepository.updateByPositionAndCompanyAndSalaryLessThan(position, minSalary, company);
    }
}

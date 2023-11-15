package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.repository.EmployeeRepository;
import hu.webuni.hr.npistu.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static hu.webuni.hr.npistu.specification.EmployeeSpecifications.*;

public abstract class EmployeeAbstractService implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PositionRepository positionRepository;

    @Transactional
    public Employee create(Employee employee) {
        return save(employee);
    }

    @Transactional
    public Employee update(Employee employee) {
        findById(employee.getId());

        return save(employee);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Page<Employee> findAllWithPage(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Employee findById(long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public void delete(long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> findByJob(String job) {
        return employeeRepository.findByJob(job);
    }

    public List<Employee> findByNameStartingWithIgnoreCase(String name) {
        return employeeRepository.findByNameStartingWithIgnoreCase(name);
    }

    public List<Employee> findByStartedBetween(LocalDateTime started, LocalDateTime end) {
        return employeeRepository.findByStartedBetween(started, end);
    }

    public List<Employee> findEmployeesBySpecification(Employee employee) {
        long id = employee.getId();
        String name = employee.getName();
        String positionName = employee.getPosition() != null ? employee.getPosition().getName():"";
        String companyName = employee.getCompany() != null ? employee.getCompany().getName():"";
        Integer salary = employee.getSalary();
        LocalDateTime started = employee.getStarted();

        Specification<Employee> specs = Specification.where(null);

        if (id > 0) {
            specs = specs.and(hasId(id));
        }

        if (StringUtils.hasLength(name)) {
            specs = specs.and(nameStartsWith(name));
        }

        if (StringUtils.hasLength(positionName)) {
            specs = specs.and(positionName(positionName));
        }

        if (StringUtils.hasLength(companyName)) {
            specs = specs.and(companyNameStartsWith(companyName));
        }

        if (salary != null && salary > 0) {
            specs = specs.and(salaryBetween((int) (salary*0.95), (int) (salary*1.05)));
        }

        if (started != null) {
            specs = specs.and(started(started));
        }

        return employeeRepository.findAll(specs);
    }

    private Employee save(Employee employee) {
        if (employee.getPosition() != null) {
            if (employee.getPosition().getId() == null) {
                employee.setPosition(positionRepository.saveAndFlush(employee.getPosition()));
            }
        }

        return employeeRepository.save(employee);
    }
}

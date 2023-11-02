package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public abstract class EmployeeAbstractService implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Transactional
    public Employee create(Employee employee) {
        return save(employee);
    }

    @Transactional
    public Employee update(Employee employee) {
        if (findById(employee.getId()) == null) {
            return null;
        }

        return save(employee);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Page<Employee> findAllWithPage(Optional<String> sort, Optional<String> orderby, int pagenumber, int pagesize) {
        Sort orders = Sort.unsorted();

        if (sort.isPresent()) {
            orders = Sort.by(sort.get());

            if (orderby.isPresent() && orderby.get().equals("desc")) {
                orders = orders.descending();
            }
        }

        Pageable sortedByName = PageRequest.of(pagenumber, pagesize, orders);

        return employeeRepository.findAll(sortedByName);
    }

    public Employee findById(long id) {
        return employeeRepository.findById(id).orElse(null);
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

    private Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
}

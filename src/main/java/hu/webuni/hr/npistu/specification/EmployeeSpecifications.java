package hu.webuni.hr.npistu.specification;

import hu.webuni.hr.npistu.model.Company_;
import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.model.Employee_;
import hu.webuni.hr.npistu.model.Position_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class EmployeeSpecifications {

    public static Specification<Employee> hasId(long id) {
        return (root, cq, cb) -> cb.equal(root.get(Employee_.ID), id);
    }

    public static Specification<Employee> nameStartsWith(String prefix) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Employee_.NAME)), prefix.toLowerCase() + "%");
    }

    public static Specification<Employee> positionName(String name) {
        return (root, cq, cb) -> cb.equal(root.join("position").get(Position_.NAME), name);
    }

    public static Specification<Employee> salaryBetween(int salaryMinus, int salaryPlus) {
//        return (root, cq, cb) -> cb.between(root.get(Employee_.SALARY), salaryMinus, salaryPlus);
        return (root, cq, cb) -> cb.and(cb.greaterThanOrEqualTo(root.get(Employee_.SALARY), salaryMinus), cb.lessThanOrEqualTo(root.get(Employee_.SALARY), salaryPlus));
    }

    public static Specification<Employee> started(LocalDateTime started) {
        return (root, cq, cb) -> cb.between(root.get(Employee_.STARTED), LocalDateTime.of(started.toLocalDate(), LocalTime.MIDNIGHT), LocalDateTime.of(started.toLocalDate().plusDays(1), LocalTime.MIDNIGHT));
    }

    public static Specification<Employee> companyNameStartsWith(String prefix) {
        return (root, cq, cb) -> cb.like(cb.lower(root.join("company").get(Company_.NAME)), prefix.toLowerCase() + "%");
    }
}

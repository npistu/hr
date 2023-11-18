package hu.webuni.hr.npistu.specification;

import hu.webuni.hr.npistu.enums.TimeoffStatus;
import hu.webuni.hr.npistu.model.Employee_;
import hu.webuni.hr.npistu.model.Timeoff;
import hu.webuni.hr.npistu.model.Timeoff_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeoffSpecifications {

    public static Specification<Timeoff> hasId(long id) {
        return (root, cq, cb) -> cb.equal(root.get(Timeoff_.ID), id);
    }

    public static Specification<Timeoff> status(TimeoffStatus timeoffStatus) {
        return (root, cq, cb) -> cb.equal(root.get(Timeoff_.STATUS), timeoffStatus);
    }

    public static Specification<Timeoff> employeeNameStartsWith(String employeePrefix) {
        return (root, cq, cb) -> cb.like(cb.lower(root.join("employee").get(Employee_.NAME)), employeePrefix.toLowerCase() + "%");
    }

    public static Specification<Timeoff> approverNameStartsWith(String approverPrefix) {
        return (root, cq, cb) -> cb.like(cb.lower(root.join("approver").get(Employee_.NAME)), approverPrefix.toLowerCase() + "%");
    }

    public static Specification<Timeoff> createdAtBetween(LocalDate createdAtFrom, LocalDate createdAtTo) {
        return (root, cq, cb) -> cb.between(root.get(Timeoff_.CREATED_AT), LocalDateTime.of(createdAtFrom, LocalTime.MIDNIGHT), LocalDateTime.of(createdAtTo.plusDays(1), LocalTime.MIDNIGHT));
    }

    public static Specification<Timeoff> createdAtGreaterThanOrEqualTo(LocalDate createdAtFrom) {
        return (root, cq, cb) -> cb.greaterThanOrEqualTo(root.get(Timeoff_.CREATED_AT), LocalDateTime.of(createdAtFrom, LocalTime.MIDNIGHT));
    }

    public static Specification<Timeoff> createdAtLessThanOrEqualTo(LocalDate createdAtTo) {
        return (root, cq, cb) -> cb.lessThanOrEqualTo(root.get(Timeoff_.CREATED_AT), LocalDateTime.of(createdAtTo.plusDays(1), LocalTime.MIDNIGHT));
    }

    public static Specification<Timeoff> timeoffInRange(LocalDate timeoffFrom, LocalDate timeoffTo) {
        return (root, cq, cb) -> cb.and(
                    cb.lessThanOrEqualTo(root.get(Timeoff_.STARTED), timeoffTo),
                    cb.greaterThanOrEqualTo(root.get(Timeoff_.ENDED), timeoffFrom)
                );
    }

    public static Specification<Timeoff> timeoffGreaterThanOrEqualTo(LocalDate timeoffFrom) {
        return (root, cq, cb) -> cb.greaterThanOrEqualTo(root.get(Timeoff_.ENDED), timeoffFrom);
    }

    public static Specification<Timeoff> timeoffLessThanOrEqualTo(LocalDate timeoffTo) {
        return (root, cq, cb) -> cb.lessThanOrEqualTo(root.get(Timeoff_.STARTED), timeoffTo);
    }
}

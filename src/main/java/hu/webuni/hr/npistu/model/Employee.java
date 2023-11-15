package hu.webuni.hr.npistu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String job;
    private Integer salary;
    private LocalDateTime started;
    @ManyToOne
    @ToString.Exclude
    private Company company;
    @ManyToOne
    @ToString.Exclude
    private Position position;

    public Employee(String name, String job, Integer salary, LocalDateTime started, Company company, Position position) {
        this.name = name;
        this.job = job;
        this.salary = salary;
        this.started = started;
        this.company = company;
        this.position = position;
    }
}

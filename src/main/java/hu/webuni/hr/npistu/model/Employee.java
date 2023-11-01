package hu.webuni.hr.npistu.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String job;
    private Integer salary;
    private LocalDateTime started;
    @ManyToOne
    @ToString.Exclude
    private Company company;

    public Employee(String name, String job, Integer salary, LocalDateTime started, Company company) {
        this.name = name;
        this.job = job;
        this.salary = salary;
        this.started = started;
        this.company = company;
    }
}

package hu.webuni.hr.npistu.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
    private List<Timeoff> timeoffs;

    @Column(unique = true)
    private String username;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    @ManyToOne
    @ToString.Exclude
    private Employee manager;

    @OneToMany(mappedBy = "manager")
    private List<Employee> managedEmployees;

    public Employee(String name, String job, Integer salary, LocalDateTime started, Company company, Position position, List<Timeoff> timeoffs, String username, String password, Set<String> roles, Employee manager) {
        this.name = name;
        this.job = job;
        this.salary = salary;
        this.started = started;
        this.company = company;
        this.position = position;
        this.timeoffs = timeoffs;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.manager = manager;
    }

    public void addTimeoff(Timeoff timeoff) {
        timeoff.setEmployee(this);
        getTimeoffs().add(timeoff);
    }

    public List<Timeoff> getTimeoffs() {
        if(this.timeoffs == null)
            this.timeoffs = new ArrayList<>();
        return this.timeoffs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

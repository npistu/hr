package hu.webuni.hr.npistu.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public Employee(String name, String job, Integer salary, LocalDateTime started, Company company, Position position) {
        this.name = name;
        this.job = job;
        this.salary = salary;
        this.started = started;
        this.company = company;
        this.position = position;
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
}

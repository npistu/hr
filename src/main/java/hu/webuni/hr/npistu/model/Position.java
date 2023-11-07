package hu.webuni.hr.npistu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Position {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String minNeededQualification;
    private Integer minSalary;
    @ManyToOne
    @ToString.Exclude
    private Company company;

    public Position(String name, String minNeededQualification, Integer minSalary, Company company) {
        this.name = name;
        this.minNeededQualification = minNeededQualification;
        this.minSalary = minSalary;
        this.company = company;
    }
}

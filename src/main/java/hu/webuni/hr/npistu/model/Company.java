package hu.webuni.hr.npistu.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company {
    @Id
    @GeneratedValue
    private Long id;
    private String registrationNumber;
    private String name;
    private String address;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Employee> employees;
}

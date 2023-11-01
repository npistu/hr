package hu.webuni.hr.npistu.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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

    public Company(String registrationNumber, String name, String address) {
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
    }
}

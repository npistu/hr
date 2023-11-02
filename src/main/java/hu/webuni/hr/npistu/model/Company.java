package hu.webuni.hr.npistu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

//    @Enumerated(EnumType.STRING)
//    private CompanyForm form;
    private String form;

    private String address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Employee> employees;

    public Company(String registrationNumber, String name, String address, String form) {
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.form = form;
    }
}

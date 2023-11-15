package hu.webuni.hr.npistu.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "withEmployees",
                attributeNodes = {
                        @NamedAttributeNode("employees")
                }
        ),
        @NamedEntityGraph(
                name = "withForm",
                attributeNodes = {
                        @NamedAttributeNode("form")
                }
        )
})
@Entity
public class Company {
    @Id
    @GeneratedValue
    private Long id;
    private String registrationNumber;
    private String name;

    @ManyToOne
    @ToString.Exclude
    private Form form;

    private String address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Employee> employees;

    public Company(String registrationNumber, String name, String address, Form form) {
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.form = form;
    }

    public void addEmployee(Employee employee) {
        employee.setCompany(this);
        getEmployees().add(employee);
    }

    public List<Employee> getEmployees() {
        if(this.employees == null)
            this.employees = new ArrayList<>();
        return this.employees;
    }
}

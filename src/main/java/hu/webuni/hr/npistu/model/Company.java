package hu.webuni.hr.npistu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    private Long id;
    private String registrationNumber;
    private String name;
    private String address;
    private Map<Long, Employee> employees;
}

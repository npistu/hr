package hu.webuni.hr.npistu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    private Long id;
    private String registrationNumber;
    private String name;
    private String address;
    private List<EmployeeDto> employees;
}

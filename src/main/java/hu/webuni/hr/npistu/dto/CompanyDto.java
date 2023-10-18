package hu.webuni.hr.npistu.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("companyDto")
public class CompanyDto {
    private Long id;
    private String registrationNumber;
    private String name;
    private String address;
    private Map<Long, EmployeeDto> employees;
}

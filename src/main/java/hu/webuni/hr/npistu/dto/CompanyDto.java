package hu.webuni.hr.npistu.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonView;
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
//@JsonFilter("companyDto")
public class CompanyDto {
    @JsonView(Views.BaseData.class)
    private Long id;
    @JsonView(Views.BaseData.class)
    private String registrationNumber;
    @JsonView(Views.BaseData.class)
    private String name;
    @JsonView(Views.BaseData.class)
    private String address;

    private Map<Long, EmployeeDto> employees;
}

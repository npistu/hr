package hu.webuni.hr.npistu.mapper;

import hu.webuni.hr.npistu.dto.AvgSalaryDto;
import hu.webuni.hr.npistu.dto.CompanyDto;
import hu.webuni.hr.npistu.dto.EmployeeDto;
import hu.webuni.hr.npistu.model.Company;
import hu.webuni.hr.npistu.model.Employee;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyDto companyToDto(Company company);

    List<CompanyDto> companiesToDtos(List<Company> companies);

    Company dtoToCompany(CompanyDto companyDto);

    default List<AvgSalaryDto> avgSalariesToDtos(List<Object[]> avgSalaries) {
        List<AvgSalaryDto> result = new ArrayList<>();

        if (avgSalaries != null) {
            for (Object[] objects: avgSalaries) {
                result.add(new AvgSalaryDto(objects[0].toString(), ((BigDecimal) objects[1]).doubleValue()));
            }
        }

        return result;
    }
}

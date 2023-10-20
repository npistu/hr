package hu.webuni.hr.npistu.mapper;

import hu.webuni.hr.npistu.dto.CompanyDto;
import hu.webuni.hr.npistu.dto.EmployeeDto;
import hu.webuni.hr.npistu.model.Company;
import hu.webuni.hr.npistu.model.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyDto companyToDto(Company company);

    List<CompanyDto> companiesToDtos(List<Company> companies);

    Company dtoToCompany(CompanyDto companyDto);
}

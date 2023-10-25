package hu.webuni.hr.npistu.mapper;

import hu.webuni.hr.npistu.dto.EmployeeDto;
import hu.webuni.hr.npistu.model.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDto employeeToDto(Employee employee);

    List<EmployeeDto> employeesToDtos(List<Employee> employees);

    Employee dtoToEmployee(EmployeeDto employeeDto);

    List<Employee> dtosToEmployees(List<EmployeeDto> employeeDtos);
}

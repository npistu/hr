package hu.webuni.hr.npistu.mapper;

import hu.webuni.hr.npistu.dto.EmployeeDto;
import hu.webuni.hr.npistu.model.Employee;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PositionMapper.class})
public interface EmployeeMapper extends EntityMapper<EmployeeDto, Employee>{

    default Page<EmployeeDto> pageEmployeesToPageDtos(Page<Employee> employees) {
        if (employees == null) {
            return null;
        }

        return employees.map(this::entityToDto);
    }
}

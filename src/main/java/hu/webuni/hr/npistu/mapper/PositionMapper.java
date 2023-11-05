package hu.webuni.hr.npistu.mapper;

import hu.webuni.hr.npistu.dto.EmployeeDto;
import hu.webuni.hr.npistu.dto.PositionDto;
import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.model.Position;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PositionMapper extends EntityMapper<PositionDto, Position> {
//    @Mapping(target = "id", source = "positionId")
//    PositionDto positionToDto(Position position);
//
//    List<PositionDto> positionsToDtos(List<Position> positions);

//    @InheritInverseConfiguration
//    Position dtoToPosition(PositionDto positionDto);
//
//    List<Position> dtosToPositions(List<PositionDto> positionDtos);
}

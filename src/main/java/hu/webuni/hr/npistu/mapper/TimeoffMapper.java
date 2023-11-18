package hu.webuni.hr.npistu.mapper;

import hu.webuni.hr.npistu.dto.TimeoffDto;
import hu.webuni.hr.npistu.dto.TimeoffWithUserNameDto;
import hu.webuni.hr.npistu.model.Timeoff;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface TimeoffMapper extends EntityMapper<TimeoffDto, Timeoff> {

    default Page<TimeoffWithUserNameDto> pageTimeoffsToPageDtos(Page<Timeoff> timeoffs) {
        if (timeoffs == null) {
            return null;
        }

        return timeoffs.map(this::timeoffToDtoWithOnlyUserName);
    }

    @Mapping(source = "employee.name", target = "employee")
    @Mapping(source = "approver.name", target = "approver")
    @Named("onlyUserName")
    public TimeoffWithUserNameDto timeoffToDtoWithOnlyUserName(Timeoff timeoff);

    @IterableMapping(qualifiedByName = "onlyUserName")
    public List<TimeoffWithUserNameDto> timeoffsToDtosWithOnlyUserName(List<Timeoff> timeoffs);
}

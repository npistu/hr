package hu.webuni.hr.npistu.mapper;

import hu.webuni.hr.npistu.dto.FormDto;
import hu.webuni.hr.npistu.model.Form;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FormMapper extends EntityMapper<FormDto, Form> {
}

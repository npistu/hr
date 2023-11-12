package hu.webuni.hr.npistu.mapper;

import hu.webuni.hr.npistu.dto.AvgSalaryDto;
import hu.webuni.hr.npistu.dto.CompanyDto;
import hu.webuni.hr.npistu.model.Company;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {FormMapper.class})
public interface CompanyMapper extends EntityMapper<CompanyDto, Company> {

    default List<AvgSalaryDto> avgSalariesToDtos(List<Object[]> avgSalaries) {
        List<AvgSalaryDto> result = new ArrayList<>();

        if (avgSalaries != null) {
            for (Object[] objects: avgSalaries) {
                result.add(new AvgSalaryDto(objects[0].toString(), ((BigDecimal) objects[1]).doubleValue()));
            }
        }

        return result;
    }

    @Mapping(target = "employees", ignore = true)
    @Named("summary")
    public CompanyDto companyToSummaryDto(Company company);

    @IterableMapping(qualifiedByName = "summary")
    public List<CompanyDto> companiesToSummaryDtos(List<Company> companies);
}

package hu.webuni.hr.npistu.mapping;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import hu.webuni.hr.npistu.dto.CompanyDto;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyMapping {
    public static MappingJacksonValue companyListMappingJackson(Optional<SimpleBeanPropertyFilter> simpleBeanPropertyFilter, List<CompanyDto> companyList) {
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("companyFilter", simpleBeanPropertyFilter.orElse(SimpleBeanPropertyFilter.serializeAllExcept()));

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(companyList);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    public static MappingJacksonValue companyMappingJackson(Optional<SimpleBeanPropertyFilter> simpleBeanPropertyFilter, CompanyDto company) {
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("companyFilter", simpleBeanPropertyFilter.orElse(SimpleBeanPropertyFilter.serializeAllExcept()));

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(company);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }
}

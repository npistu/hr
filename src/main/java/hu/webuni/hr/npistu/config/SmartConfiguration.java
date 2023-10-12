package hu.webuni.hr.npistu.config;

import hu.webuni.hr.npistu.service.DefaultEmployeeService;
import hu.webuni.hr.npistu.service.EmployeeService;
import hu.webuni.hr.npistu.service.SmartEmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("smart")
public class SmartConfiguration {
    @Bean
    public EmployeeService employeeService () {
        return new SmartEmployeeService();
    }
}

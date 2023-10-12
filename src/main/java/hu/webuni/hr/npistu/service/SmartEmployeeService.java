package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.config.HrConfigurationProperties;
import hu.webuni.hr.npistu.config.HrConfigurationProperties.Smart;
import hu.webuni.hr.npistu.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Service
public class SmartEmployeeService implements EmployeeService{

    @Autowired
    private HrConfigurationProperties properties;

    @Override
    public int getPayRaisePercent(Employee employee) {
        double years = Duration.between(employee.getStarted(), LocalDateTime.now()).toDays()/365.0;

        for (Smart smart: properties.getSmart()) {
            if (years >= smart.getLimit()) return smart.getPercent();
        }

        return 0;
    }
}

package hu.webuni.hr.npistu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record AvgSalaryDto(String job, Double avgSalary) {
    public AvgSalaryDto() {
        this(null, null);
    }

    public AvgSalaryDto(String job, Double avgSalary) {
        this.job = job;
        this.avgSalary = avgSalary;
    }
}
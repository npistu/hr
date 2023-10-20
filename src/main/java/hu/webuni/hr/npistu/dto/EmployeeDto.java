package hu.webuni.hr.npistu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class EmployeeDto {
    @Positive
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String job;
    @Positive
    private Integer salary;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Past
    private LocalDateTime started;

    public EmployeeDto() {
    }

    public EmployeeDto(Long id, String name, String job, Integer salary, LocalDateTime started) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.salary = salary;
        this.started = started;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public LocalDateTime getStarted() {
        return started;
    }

    public void setStarted(LocalDateTime started) {
        this.started = started;
    }
}

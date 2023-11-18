package hu.webuni.hr.npistu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hu.webuni.hr.npistu.enums.TimeoffStatus;

import java.time.LocalDate;

public record TimeoffSearchDto(Long id,
                               String employeeName,
                               String approverName,
                               TimeoffStatus status,
                               @JsonFormat(pattern = "yyyy-MM-dd") LocalDate createdAtFrom,
                               @JsonFormat(pattern = "yyyy-MM-dd") LocalDate createdAtTo,
                               @JsonFormat(pattern = "yyyy-MM-dd") LocalDate timeoffFrom,
                               @JsonFormat(pattern = "yyyy-MM-dd") LocalDate timeoffTo) {

    public TimeoffSearchDto() {
        this(1L, null, null, null, null, null, null, null);
    }
}
package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.dto.TimeoffSearchDto;
import hu.webuni.hr.npistu.enums.TimeoffStatus;
import hu.webuni.hr.npistu.model.Timeoff;
import hu.webuni.hr.npistu.repository.TimeoffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static hu.webuni.hr.npistu.specification.TimeoffSpecifications.*;

@Service
public class TimeoffService {

    @Autowired
    private TimeoffRepository timeoffRepository;

    @Autowired
    private EmployeeService employeeService;

    public List<Timeoff> findAll() {
        return timeoffRepository.findAll();
    }

    public Page<Timeoff> findAllWithPage(Pageable pageable) {
        return timeoffRepository.findAll(pageable);
    }

    public Timeoff findById(Long id) {
        return timeoffRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Timeoff create(Long employeeId, Timeoff timeoff) {
        timeoff.setEmployee(employeeService.findById(employeeId));
        timeoff.setStatus(TimeoffStatus.requested);

        return timeoffRepository.saveAndFlush(timeoff);
    }

    @Transactional
    public Timeoff denied(Long id, Long employeeId) {
        Timeoff timeoff = findByIdAndStatus(id, TimeoffStatus.requested);

        timeoff.setApprover(employeeService.findById(employeeId));
        timeoff.setStatus(TimeoffStatus.denied);

        return timeoff;
    }

    @Transactional
    public Timeoff accepted(Long id, Long employeeId) {
        Timeoff timeoff = findByIdAndStatus(id, TimeoffStatus.requested);

        timeoff.setApprover(employeeService.findById(employeeId));
        timeoff.setStatus(TimeoffStatus.accepted);

        return timeoff;
    }

    @Transactional
    public Timeoff update(Long id, Timeoff timeoff){
        Timeoff updatedTimeoff = findByIdAndStatus(id, TimeoffStatus.requested);

        updatedTimeoff.setStarted(timeoff.getStarted());
        updatedTimeoff.setEnded(timeoff.getEnded());

        return  updatedTimeoff;
    }

    @Transactional
    public void delete(Long id){
        Timeoff timeoff = findByIdAndStatus(id, TimeoffStatus.requested);

        timeoffRepository.delete(timeoff);
    }

    public Timeoff findByIdAndStatus(Long id, TimeoffStatus status) {
        return timeoffRepository.findByIdAndStatus(id, status).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Timeoff> findTimeoffsBySpecification(TimeoffSearchDto timeoffSearchDto) {
        Long id = timeoffSearchDto.id();
        TimeoffStatus status = timeoffSearchDto.status();
        String employeeName = timeoffSearchDto.employeeName();
        String approverName = timeoffSearchDto.approverName();
        LocalDate createAtFrom = timeoffSearchDto.createdAtFrom();
        LocalDate createAtTo = timeoffSearchDto.createdAtTo();
        LocalDate timeoffFrom = timeoffSearchDto.timeoffFrom();
        LocalDate timeoffTo = timeoffSearchDto.timeoffTo();

        Specification<Timeoff> specs = Specification.where(null);

        if (id != null && id > 0) {
            specs = specs.and(hasId(id));
        }

        if (status != null) {
            specs = specs.and(status(status));
        }

        if (StringUtils.hasLength(employeeName)) {
            specs = specs.and(employeeNameStartsWith(employeeName));
        }

        if (StringUtils.hasLength(approverName)) {
            specs = specs.and(approverNameStartsWith(approverName));
        }

        if (createAtFrom != null && createAtTo != null) {
            if (createAtFrom.isAfter(createAtTo)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            
            specs = specs.and(createdAtBetween(createAtFrom, createAtTo));
        }

        if (createAtFrom != null && createAtTo == null) {
            specs = specs.and(createdAtGreaterThanOrEqualTo(createAtFrom));
        }

        if (createAtFrom == null && createAtTo != null) {
            specs = specs.and(createdAtLessThanOrEqualTo(createAtTo));
        }

        if (timeoffFrom != null && timeoffTo != null) {
            if (timeoffFrom.isAfter(timeoffTo)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            specs = specs.and(timeoffInRange(timeoffFrom, timeoffTo));
        }

        if (timeoffFrom != null && timeoffTo == null) {
            specs = specs.and(timeoffGreaterThanOrEqualTo(timeoffFrom));
        }

        if (timeoffFrom == null && timeoffTo != null) {
            specs = specs.and(timeoffLessThanOrEqualTo(timeoffTo));
        }

        return timeoffRepository.findAll(specs);
    }
}

package hu.webuni.hr.npistu.controller;

import hu.webuni.hr.npistu.dto.TimeoffDto;
import hu.webuni.hr.npistu.dto.TimeoffSearchDto;
import hu.webuni.hr.npistu.dto.TimeoffWithUserNameDto;
import hu.webuni.hr.npistu.mapper.TimeoffMapper;
import hu.webuni.hr.npistu.service.TimeoffService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timeoff")
public class TimeoffController {

    @Autowired
    private TimeoffService timeoffService;

    @Autowired
    private TimeoffMapper timeoffMapper;

    @GetMapping
    public List<TimeoffDto> findAll() {
        return timeoffMapper.entitiesToDtos(timeoffService.findAll());
    }

    @GetMapping("/withpage")
    public Page<TimeoffWithUserNameDto> findAllWithPage(@PageableDefault(size = 5) Pageable pageable) {
        return timeoffMapper.pageTimeoffsToPageDtos(timeoffService.findAllWithPage(pageable));
    }

    @GetMapping("/{id}")
    public TimeoffDto findById(@PathVariable Long id) {
        return timeoffMapper.entityToDto(timeoffService.findById(id));
    }

    @GetMapping("/searchbyspecification")
    public List<TimeoffDto> findTimeoffsBySpecification(@RequestBody TimeoffSearchDto timeoffSearchDto) {
        return timeoffMapper.entitiesToDtos(timeoffService.findTimeoffsBySpecification(timeoffSearchDto));
    }

    @PostMapping("/{employeeId}")
    public TimeoffDto create(@PathVariable Long employeeId, @RequestBody @Valid TimeoffDto timeoffDto) {
        return timeoffMapper.entityToDto(timeoffService.create(employeeId, timeoffMapper.dtoToEntity(timeoffDto)));
    }

    @PutMapping("/denied/{id}/{employeeId}")
    public TimeoffDto denied(@PathVariable Long id, @PathVariable Long employeeId) {
        return timeoffMapper.entityToDto(timeoffService.denied(id, employeeId));
    }

    @PutMapping("/accepted/{id}/{employeeId}")
    public TimeoffDto accepted(@PathVariable Long id, @PathVariable Long employeeId) {
        return timeoffMapper.entityToDto(timeoffService.accepted(id, employeeId));
    }

    @PutMapping("/{id}")
    public TimeoffDto update(@PathVariable Long id, @RequestBody @Valid TimeoffDto timeoffDto) {
        return timeoffMapper.entityToDto(timeoffService.update(id, timeoffMapper.dtoToEntity(timeoffDto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        timeoffService.delete(id);
    }

}

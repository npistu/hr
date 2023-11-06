package hu.webuni.hr.npistu.controller;

import hu.webuni.hr.npistu.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/position")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @PutMapping("/{name}/{minSalary}")
    public void delete(@PathVariable String name, @PathVariable Integer minSalary) {
        positionService.setNewMinSalary(name, minSalary);
    }
}

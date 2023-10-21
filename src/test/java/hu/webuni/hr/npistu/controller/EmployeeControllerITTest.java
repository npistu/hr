package hu.webuni.hr.npistu.controller;

import hu.webuni.hr.npistu.dto.EmployeeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerITTest {

    private static final String API_EMPLOYEES = "/api/employees";

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testValidCreated() {
        EmployeeDto newEmployee = new EmployeeDto(100L, "István", "Fejlesztő", 300000, LocalDateTime.of(2020, 1, 1, 12, 12, 12));

        testCreateEmployee(newEmployee);
    }

    @Test
    void testInvalidCreated() {
        EmployeeDto newEmployee = new EmployeeDto(-100L, "", "Fejlesztő", 300000, LocalDateTime.of(2020, 1, 1, 12, 12, 12));

        testCreateEmployee(newEmployee);
    }

    private void testCreateEmployee(EmployeeDto newEmployee) {
        List<EmployeeDto> employeesBefore = getAllEmployees();

        createEmployee(newEmployee);

        List<EmployeeDto> employeesAfter = getAllEmployees();

        assertThat(employeesAfter.subList(0, employeesBefore.size())).usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(employeesBefore);
        assertThat(employeesAfter.get(employeesAfter.size() - 1)).usingRecursiveComparison()
                .isEqualTo(newEmployee);
    }

    private void createEmployee(EmployeeDto newEmployee) {
        webTestClient.post().uri(API_EMPLOYEES).bodyValue(newEmployee).exchange().expectStatus().isOk();
    }

    private void updateEmployee(EmployeeDto updatedEmployee) {
        webTestClient.put().uri(API_EMPLOYEES).attribute("id", updatedEmployee.id()).bodyValue(updatedEmployee).exchange().expectStatus().isOk();
    }

    private List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> allEmployees = webTestClient.get().uri(API_EMPLOYEES).exchange().expectStatus().isOk()
                .expectBodyList(EmployeeDto.class).returnResult().getResponseBody();

        assert allEmployees != null;
        allEmployees.sort(Comparator.comparing(EmployeeDto::id));

        return allEmployees;
    }

}
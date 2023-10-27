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
        EmployeeDto newEmployee = new EmployeeDto(null, "István", "Fejlesztő", 300000, LocalDateTime.of(2020, 1, 1, 12, 12, 12));

        List<EmployeeDto> employeesBefore = getAllEmployees();

        createEmployee(newEmployee);

        List<EmployeeDto> employeesAfter = getAllEmployees();

        assertThat(employeesAfter.subList(0, employeesBefore.size())).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyElementsOf(employeesBefore);
//        assertThat(employeesAfter.get(employeesAfter.size() - 1)).usingRecursiveComparison()
//                .isEqualTo(newEmployee);
    }

    @Test
    void testInvalidCreated() {
        EmployeeDto newEmployee = new EmployeeDto(-101L, "", "Fejlesztő", 300000, LocalDateTime.of(2020, 1, 1, 12, 12, 12));

        webTestClient.post().uri(API_EMPLOYEES).bodyValue(newEmployee).exchange().expectStatus().isBadRequest();
    }

//    @Test
    void testValidUpdate() {
        EmployeeDto updatedEmployee = new EmployeeDto(103L, "Béla", "Fejlesztő", 350000, LocalDateTime.of(2023, 1, 1, 12, 12, 12));

        testUpdateEmployee(updatedEmployee);
    }

    @Test
    void testInvalidUpdate() {
        EmployeeDto updatedEmployee = new EmployeeDto(106L, "Béla", "Fejlesztő", 350000, LocalDateTime.of(2023, 1, 1, 12, 12, 12));

        webTestClient.put().uri(API_EMPLOYEES+"/{id}", updatedEmployee.id()).bodyValue(updatedEmployee).exchange().expectStatus().isNotFound();
    }

    private void testUpdateEmployee(EmployeeDto updateEmployee) {

        EmployeeDto employee1 = new EmployeeDto(103L, "István", "Fejlesztő", 300000, LocalDateTime.of(2020, 1, 1, 12, 12, 12));
        EmployeeDto employee2 = new EmployeeDto(104L, "János", "Vezető", 400000, LocalDateTime.of(2020, 1, 1, 12, 12, 12));

        createEmployee(employee1);
        createEmployee(employee2);

        List<EmployeeDto> employeesBefore = getAllEmployees();

        updateEmployee(updateEmployee);

        List<EmployeeDto> employeesAfter = getAllEmployees();

        assertThat(employeesAfter.get(0)).usingRecursiveComparison()
                .isNotEqualTo(employee1);
        assertThat(employeesAfter).usingRecursiveFieldByFieldElementComparator()
                .isNotEqualTo(employeesBefore);
    }

    private void createEmployee(EmployeeDto newEmployee) {
        webTestClient.post().uri(API_EMPLOYEES).bodyValue(newEmployee).exchange().expectStatus().isOk();
    }

    private void updateEmployee(EmployeeDto updatedEmployee) {
        webTestClient.put().uri(API_EMPLOYEES+"/{id}", updatedEmployee.id()).bodyValue(updatedEmployee).exchange().expectStatus().isOk();
    }

    private List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> allEmployees = webTestClient.get().uri(API_EMPLOYEES).exchange().expectStatus().isOk()
                .expectBodyList(EmployeeDto.class).returnResult().getResponseBody();

        assert allEmployees != null;
        allEmployees.sort(Comparator.comparing(EmployeeDto::id));

        return allEmployees;
    }

}

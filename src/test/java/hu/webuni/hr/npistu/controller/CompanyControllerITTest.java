package hu.webuni.hr.npistu.controller;

import hu.webuni.hr.npistu.dto.CompanyDto;
import hu.webuni.hr.npistu.dto.EmployeeDto;
import hu.webuni.hr.npistu.service.InitDbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyControllerITTest {
    private static final String API_COMPANIES = "/api/companies";

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    InitDbService initDbService;

    @BeforeEach
    public void init() {
        initDbService.clearDB();
        initDbService.insertTestData();
    }

    @Test
    void addEmployeeNotFound() {
        EmployeeDto newEmployee = new EmployeeDto(null, "István", "Fejlesztő", 300000, LocalDateTime.of(2020, 1, 1, 12, 12, 12), null);

        webTestClient.put().uri(API_COMPANIES+"/{id}/addemployee", 9999).bodyValue(newEmployee).exchange().expectStatus().isNotFound();
    }

    @Test
    void addEmployee() {
        CompanyDto companyDto = getFirstCompanyFromAll();

        if (companyDto != null) {
            EmployeeDto newEmployee = new EmployeeDto(null, "István", "Fejlesztő", 300000, LocalDateTime.of(2020, 1, 1, 12, 12, 12), null);

            List<EmployeeDto> employeeDtosBefore = companyDto.employees();

            companyDto = webTestClient.put().uri(API_COMPANIES+"/{id}/addemployee", companyDto.id()).bodyValue(newEmployee)
                    .exchange().expectStatus().isOk()
                    .expectBody(CompanyDto.class).returnResult().getResponseBody();

            assert companyDto != null;
            List<EmployeeDto> employeeDtosAfter = companyDto.employees();

            assertThat(employeeDtosAfter.size()).isEqualTo(employeeDtosBefore.size()+1);
        }
    }

    @Test
    void deleteEmployee() {
        CompanyDto companyDto = getFirstCompanyFromAll();

        if (companyDto != null) {
            List<EmployeeDto> employeeDtosBefore = companyDto.employees();

            if (employeeDtosBefore != null && employeeDtosBefore.size() > 0) {
                EmployeeDto employeeDto = employeeDtosBefore.get(0);
                companyDto = webTestClient.delete().uri(API_COMPANIES+"/{id}/deleteemployee/{employeeId}", companyDto.id(), employeeDto.id())
                        .exchange().expectStatus().isOk()
                        .expectBody(CompanyDto.class).returnResult().getResponseBody();

                assert companyDto != null;
                List<EmployeeDto> employeeDtosAfter = companyDto.employees();

                assertThat(employeeDtosAfter.size()).isEqualTo(employeeDtosBefore.size()-1);
                assertThat(employeeDtosAfter).doesNotContain(employeeDto);
            }
        }
    }

    @Test
    void replaceEmployee() {
        CompanyDto companyDto = getFirstCompanyFromAll();

        if (companyDto != null) {
            EmployeeDto newEmployee2 = new EmployeeDto(null, "István", "Fejlesztő", 300000, LocalDateTime.of(2020, 1, 1, 12, 12, 12), null);
            EmployeeDto newEmployee3 = new EmployeeDto(null, "István", "Fejlesztő", 300000, LocalDateTime.of(2020, 1, 1, 12, 12, 12), null);
            EmployeeDto newEmployee4 = new EmployeeDto(null, "István", "Fejlesztő", 300000, LocalDateTime.of(2020, 1, 1, 12, 12, 12), null);

            List<EmployeeDto> employeeDtos = Arrays.asList(newEmployee2, newEmployee3, newEmployee4);

            List<EmployeeDto> employeeDtosBefore = companyDto.employees();

            companyDto = webTestClient.put().uri(API_COMPANIES+"/{id}/replaceemployees", companyDto.id()).bodyValue(employeeDtos)
                    .exchange().expectStatus().isOk()
                    .expectBody(CompanyDto.class).returnResult().getResponseBody();

            assert companyDto != null;
            List<EmployeeDto> employeeDtosAfter = companyDto.employees();

            assertThat(employeeDtosAfter.size()).isEqualTo(employeeDtos.size());
            assertThat(employeeDtosAfter).usingElementComparatorIgnoringFields("id")
                    .containsExactlyElementsOf(employeeDtos);
        }
    }

    private CompanyDto getFirstCompanyFromAll() {
        List<CompanyDto> companyDtos = webTestClient.get().uri(API_COMPANIES+"/withemployees").exchange().expectStatus().isOk()
                .expectBodyList(CompanyDto.class).returnResult().getResponseBody();

        assert companyDtos != null;
        if (companyDtos.size() > 0) {
            return companyDtos.get(0);
        }

        return null;
    }
}

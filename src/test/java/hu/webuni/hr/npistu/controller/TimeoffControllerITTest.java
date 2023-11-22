package hu.webuni.hr.npistu.controller;

import hu.webuni.hr.npistu.dto.EmployeeDto;
import hu.webuni.hr.npistu.dto.TimeoffDto;
import hu.webuni.hr.npistu.dto.TimeoffSearchDto;
import hu.webuni.hr.npistu.enums.TimeoffStatus;
import hu.webuni.hr.npistu.service.InitDbService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TimeoffControllerITTest {
    private static final String API_TIMEOFF = "/api/timeoff";
    private static final String API_EMPLOYEE = "/api/employees";

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
    void findByIdNotFound() {
        webTestClient.get().uri(API_TIMEOFF +"/{id}", 999999).exchange().expectStatus().isNotFound();
    }

    @Test
    void findTimeoffsBySpecificationBadRequest() {
        TimeoffSearchDto searchDto = new TimeoffSearchDto(null, null, null, null, LocalDate.MAX, LocalDate.MIN, null, null);

        webTestClient.post().uri(API_TIMEOFF +"/searchbyspecification").bodyValue(searchDto).exchange().expectStatus().isBadRequest();
    }

    @Test
    void create(){
        EmployeeDto employeeDto =  getFirstEmployeeFromAll();

        if (employeeDto != null) {
            List<TimeoffDto> timeoffDtosBefore = getAllTimeoffs();

            LocalDate started = LocalDate.of(2024, 5, 1);
            LocalDate ended = LocalDate.of(2024, 5, 5);
            TimeoffDto newTimeoffDto = new TimeoffDto(started, ended);

            TimeoffDto created = createTimeoff(newTimeoffDto, employeeDto.id());

            List<TimeoffDto> timeoffDtosAfter = getAllTimeoffs();

            assertThat(timeoffDtosAfter.size()).isEqualTo(timeoffDtosBefore.size()+1);
            assertThat(timeoffDtosAfter.get(timeoffDtosAfter.size()-1).started()).isEqualTo(started);
            assertThat(timeoffDtosAfter.get(timeoffDtosAfter.size()-1).employee()).isEqualTo(employeeDto);
        }
    }

    @Test
    void denied() {
        EmployeeDto employeeDto =  getFirstEmployeeFromAll();

        if (employeeDto != null) {
            LocalDate started = LocalDate.of(2024, 5, 1);
            LocalDate ended = LocalDate.of(2024, 5, 5);
            TimeoffDto newTimeoffDto = new TimeoffDto(started, ended);

            TimeoffDto timeoffDto = createTimeoff(newTimeoffDto, employeeDto.id());

            timeoffDto = webTestClient.put().uri(API_TIMEOFF+"/denied/{id}/{employeeId}", timeoffDto.id(), employeeDto.id()).exchange().expectStatus().isOk()
                    .expectBody(TimeoffDto.class).returnResult().getResponseBody();

            assert timeoffDto != null;
            assertThat(timeoffDto.status()).isEqualTo(TimeoffStatus.denied);
            assertThat(timeoffDto.approver()).isEqualTo(employeeDto);
        }
    }

    @Test
    void accepted() {
        EmployeeDto employeeDto =  getFirstEmployeeFromAll();

        if (employeeDto != null) {
            LocalDate started = LocalDate.of(2024, 5, 1);
            LocalDate ended = LocalDate.of(2024, 5, 5);
            TimeoffDto newTimeoffDto = new TimeoffDto(started, ended);

            TimeoffDto timeoffDto = createTimeoff(newTimeoffDto, employeeDto.id());

            timeoffDto = webTestClient.put().uri(API_TIMEOFF+"/accepted/{id}/{employeeId}", timeoffDto.id(), employeeDto.id()).exchange().expectStatus().isOk()
                    .expectBody(TimeoffDto.class).returnResult().getResponseBody();

            assert timeoffDto != null;
            assertThat(timeoffDto.status()).isEqualTo(TimeoffStatus.accepted);
            assertThat(timeoffDto.approver()).isNotEqualTo(null);
        }
    }

    @Test
    void updateValid() {
        EmployeeDto employeeDto =  getFirstEmployeeFromAll();

        if (employeeDto != null) {
            LocalDate started = LocalDate.of(2024, 5, 1);
            LocalDate ended = LocalDate.of(2024, 5, 5);
            TimeoffDto newTimeoffDto = new TimeoffDto(started, ended);

            TimeoffDto timeoffDto = createTimeoff(newTimeoffDto, employeeDto.id());

            LocalDate updatedStarted = LocalDate.of(2024, 5, 3);
            LocalDate updatedEnded = LocalDate.of(2024, 5, 10);
            TimeoffDto updatedTimeoffDto = new TimeoffDto(updatedStarted, updatedEnded);

            timeoffDto = webTestClient.put().uri(API_TIMEOFF+"/{id}", timeoffDto.id()).bodyValue(updatedTimeoffDto).exchange().expectStatus().isOk()
                    .expectBody(TimeoffDto.class).returnResult().getResponseBody();

            assert timeoffDto != null;
            assertThat(timeoffDto.status()).isEqualTo(TimeoffStatus.requested);
            assertThat(timeoffDto.started()).isEqualTo(updatedStarted);
        }
    }

    @Test
    void updateNotFound() {
        EmployeeDto employeeDto =  getFirstEmployeeFromAll();

        if (employeeDto != null) {
            LocalDate started = LocalDate.of(2024, 5, 1);
            LocalDate ended = LocalDate.of(2024, 5, 5);
            TimeoffDto newTimeoffDto = new TimeoffDto(started, ended);

            TimeoffDto created = createTimeoff(newTimeoffDto, employeeDto.id());

            Optional<TimeoffDto> optional = getAllTimeoffs().stream().filter(timeoffDto -> timeoffDto.status().equals(TimeoffStatus.accepted)).findFirst();

            if (optional.isPresent()) {
                TimeoffDto timeoffDto = optional.get();

                LocalDate updatedStarted = LocalDate.of(2024, 5, 3);
                LocalDate updatedEnded = LocalDate.of(2024, 5, 10);
                TimeoffDto updatedTimeoffDto = new TimeoffDto(updatedStarted, updatedEnded);

                webTestClient.put().uri(API_TIMEOFF+"/{id}", timeoffDto.id()).bodyValue(updatedTimeoffDto).exchange().expectStatus().isNotFound();
            }
        }
    }

    @Test
    void delete(){
        EmployeeDto employeeDto =  getFirstEmployeeFromAll();

        if (employeeDto != null) {
            List<TimeoffDto> timeoffDtosBefore = getAllTimeoffs();

            LocalDate started = LocalDate.of(2024, 5, 1);
            LocalDate ended = LocalDate.of(2024, 5, 5);
            TimeoffDto newTimeoffDto = new TimeoffDto(started, ended);

            TimeoffDto timeoffDto = createTimeoff(newTimeoffDto, employeeDto.id());

            webTestClient.delete().uri(API_TIMEOFF+"/{id}", timeoffDto.id()).exchange().expectStatus().isOk();

            List<TimeoffDto> timeoffDtosAfter = getAllTimeoffs();

            assertThat(timeoffDtosAfter.size()).isEqualTo(timeoffDtosBefore.size());
        }
    }

    @Test
    void deleteNotFound(){
        Optional<TimeoffDto> optional = getAllTimeoffs().stream().filter(timeoffDto -> timeoffDto.status().equals(TimeoffStatus.accepted)).findFirst();

        if (optional.isPresent()) {
            TimeoffDto timeoffDto = optional.get();

            webTestClient.delete().uri(API_TIMEOFF+"/{id}", timeoffDto.id()).exchange().expectStatus().isNotFound();
        }
    }

    private EmployeeDto getFirstEmployeeFromAll() {
        List<EmployeeDto> employeeDtos = webTestClient.get().uri(API_EMPLOYEE).exchange().expectStatus().isOk()
                .expectBodyList(EmployeeDto.class).returnResult().getResponseBody();

        assert employeeDtos != null;
        if (employeeDtos.size() > 0) {
            return employeeDtos.get(0);
        }

        return null;
    }

    private List<TimeoffDto> getAllTimeoffs() {
        List<TimeoffDto> allTimeoffDtos = webTestClient.get().uri(API_TIMEOFF).exchange().expectStatus().isOk()
                .expectBodyList(TimeoffDto.class).returnResult().getResponseBody();

        assert allTimeoffDtos != null;
        allTimeoffDtos.sort(Comparator.comparing(TimeoffDto::id));

        return allTimeoffDtos;
    }

    private TimeoffDto createTimeoff(TimeoffDto timeoffDto, Long employeeId) {
        return webTestClient.post().uri(API_TIMEOFF+"/{employeeId}", employeeId).bodyValue(timeoffDto).exchange().expectStatus().isOk()
                .expectBody(TimeoffDto.class).returnResult().getResponseBody();
    }

}

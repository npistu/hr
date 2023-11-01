package hu.webuni.hr.npistu;

import hu.webuni.hr.npistu.model.Company;
import hu.webuni.hr.npistu.repository.CompanyRepository;
import hu.webuni.hr.npistu.service.InitDbService;
import hu.webuni.hr.npistu.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

    @Autowired
    private InitDbService initDbService;

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initDbService.clearDB();

        initDbService.insertTestData();
    }
}

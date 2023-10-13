package hu.webuni.hr.npistu;

import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.DoubleToIntFunction;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

    @Autowired
    private SalaryService salaryService;

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

//        Employee employee1 = new Employee(1L, "István", "fejlesztő", 1000, LocalDateTime.parse("2020-03-04 12:00", formatter));
//        Employee employee2 = new Employee(2L, "Csaba", "takarito", 1000, LocalDateTime.parse("2022-03-04 12:00", formatter));
//        Employee employee3 = new Employee(3L, "Endre", "takarito", 1000, LocalDateTime.parse("2012-03-04 12:00", formatter));
//        Employee employee4 = new Employee(4L, "Béla", "takarito", 1000, LocalDateTime.parse("2016-03-04 12:00", formatter));
//        Employee employee5 = new Employee(5L, "Juli", "takarito", 1000, LocalDateTime.parse("2021-01-04 12:00", formatter));
//        Employee employee6 = new Employee(6L, "Jocó", "takarito", 1000, LocalDateTime.parse("2021-08-04 12:00", formatter));

//        System.out.println(employee1.getName()+" before: "+employee1.getSalary());
//        System.out.println(employee2.getName()+" before: "+employee2.getSalary());
//        System.out.println(employee3.getName()+" before: "+employee3.getSalary());
//        System.out.println(employee4.getName()+" before: "+employee4.getSalary());
//        System.out.println(employee5.getName()+" before: "+employee5.getSalary());
//        System.out.println(employee6.getName()+" before: "+employee6.getSalary());

//        salaryService.setNewSalary(employee1);
//        salaryService.setNewSalary(employee2);
//        salaryService.setNewSalary(employee3);
//        salaryService.setNewSalary(employee4);
//        salaryService.setNewSalary(employee5);
//        salaryService.setNewSalary(employee6);
//
//        System.out.println();
//        System.out.println(employee1.getName()+" after: "+employee1.getSalary());
//        System.out.println(employee2.getName()+" after: "+employee2.getSalary());
//        System.out.println(employee3.getName()+" after: "+employee3.getSalary());
//        System.out.println(employee4.getName()+" after: "+employee4.getSalary());
//        System.out.println(employee5.getName()+" after: "+employee5.getSalary());
//        System.out.println(employee6.getName()+" after: "+employee6.getSalary());
    }
}

package hu.webuni.hr.npistu.security;


import hu.webuni.hr.npistu.model.Employee;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
public class EmployeeUserDetails extends User {

    private Employee employee;

    public EmployeeUserDetails(String username, Employee employee) {
        super(username, employee.getPassword(), employee.getRoles().stream().map(SimpleGrantedAuthority::new).toList());

        this.employee = employee;
    }
}

package hu.webuni.hr.npistu.security;

import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return new EmployeeUserDetails(username, employee);
    }
}

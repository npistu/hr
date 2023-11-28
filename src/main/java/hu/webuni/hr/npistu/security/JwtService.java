package hu.webuni.hr.npistu.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import hu.webuni.hr.npistu.config.HrConfigurationProperties;
import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.repository.EmployeeRepository;
import hu.webuni.hr.npistu.utils.EmployeeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private HrConfigurationProperties properties;

    public String createJwt(UserDetails userDetails) {
        Employee employee = ((EmployeeUserDetails) userDetails).getEmployee();

        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withArrayClaim("auth", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .withClaim("name", employee.getName())
                .withClaim("id", employee.getId())
                .withClaim("login-name", employee.getUsername())
                .withClaim("manager", EmployeeUtils.employeeToMap(employee.getManager()))
                .withClaim("managed-employees", EmployeeUtils.employeeListToMapList(employeeRepository.findByManager(employee)))
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(properties.getJwtConfig().getDuration())))
                .withIssuer(properties.getJwtConfig().getIssuer())
                .sign(Algorithm.HMAC256(properties.getJwtConfig().getSecret()));
    }

    public UserDetails parseJwt(String jwtToken) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(properties.getJwtConfig().getSecret()))
                .withIssuer(properties.getJwtConfig().getIssuer())
                .build()
                .verify(jwtToken);

        return new User(decodedJWT.getSubject(), "dummy",
                decodedJWT.getClaim("auth").asList(String.class).stream().map(SimpleGrantedAuthority::new).toList()
        );
    }
}

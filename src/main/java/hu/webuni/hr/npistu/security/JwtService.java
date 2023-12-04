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
                .sign(getAlgorithm());
    }

    public UserDetails parseJwt(String jwtToken) {
        DecodedJWT decodedJWT = JWT.require(getAlgorithm())
                .withIssuer(properties.getJwtConfig().getIssuer())
                .build()
                .verify(jwtToken);

        return new EmployeeUserDetails(decodedJWT.getSubject(), employeeRepository.findById(decodedJWT.getClaim("id").asLong()).get());
    }

    private Algorithm getAlgorithm() {
        return switch (properties.getJwtConfig().getAlgorithm().getType()) {
            case "HS384" -> Algorithm.HMAC384(properties.getJwtConfig().getAlgorithm().getSecret());
            case "HS512" -> Algorithm.HMAC512(properties.getJwtConfig().getAlgorithm().getSecret());
            default -> Algorithm.HMAC256(properties.getJwtConfig().getAlgorithm().getSecret());
        };
    }
}

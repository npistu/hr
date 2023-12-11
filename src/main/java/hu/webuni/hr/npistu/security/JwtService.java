package hu.webuni.hr.npistu.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import hu.webuni.hr.npistu.config.HrConfigurationProperties;
import hu.webuni.hr.npistu.model.Employee;
import hu.webuni.hr.npistu.repository.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Service
public class JwtService {

    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String MANAGER = "manager";
    private static final String MANAGED_EMPLOYEES = "managed-employees";

    private String issuer; //NPE-t okozna, ha itt lenne a configból a beállítás
    private Algorithm algorithm; //= Algorithm.HMAC256("mysecret")

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private HrConfigurationProperties properties;

    @PostConstruct
    public void init() {
        issuer = properties.getJwtConfig().getIssuer();

        try {
            Method algMethod = Algorithm.class.getMethod(properties.getJwtConfig().getAlgorithm().getMethod(), String.class);
            algorithm = (Algorithm) algMethod.invoke(null, properties.getJwtConfig().getAlgorithm().getSecret());
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public String createJwt(UserDetails userDetails) {
        Employee employee = ((EmployeeUserDetails) userDetails).getEmployee();
        Employee manager = employee.getManager();
        List<Employee> managedEmployees = employee.getManagedEmployees();

        JWTCreator.Builder builder = JWT.create()
                .withSubject(userDetails.getUsername())
                .withArrayClaim("auth", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .withClaim(NAME, employee.getName())
                .withClaim(ID, employee.getId())
                .withClaim(USERNAME, employee.getUsername());

        if (manager != null) {
            builder.withClaim(MANAGER, createMapFromEmployee(manager));
        }

        if (managedEmployees != null && !managedEmployees.isEmpty()) {
            builder.withClaim(MANAGED_EMPLOYEES, managedEmployees.stream()
                    .map(this::createMapFromEmployee).toList());
        }

        return builder
//                .withClaim("manager", EmployeeUtils.employeeToMap(manager))
//                .withClaim("managed-employees", EmployeeUtils.employeeListToMapList(employeeRepository.findByManager(employee)))
//                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(properties.getJwtConfig().getDuration())))
                .withExpiresAt(new Date(System.currentTimeMillis() + properties.getJwtConfig().getDuration().toMillis()))
//                .withIssuer(properties.getJwtConfig().getIssuer())
                .withIssuer(issuer)
//                .sign(getAlgorithm());
                .sign(algorithm);
    }

    public UserDetails parseJwt(String jwtToken) {
//        DecodedJWT decodedJWT = JWT.require(getAlgorithm())
        DecodedJWT decodedJWT = JWT.require(algorithm)
//                .withIssuer(properties.getJwtConfig().getIssuer())
                .withIssuer(issuer)
                .build()
                .verify(jwtToken);

        Employee employee = new Employee();
        employee.setId(decodedJWT.getClaim(ID).asLong());
        employee.setName(decodedJWT.getClaim(NAME).asString());
        employee.setUsername(decodedJWT.getSubject());

        Claim managerClaim = decodedJWT.getClaim(MANAGER);
        if (managerClaim != null) {
            Map<String, Object> employeeMap = managerClaim.asMap();
            employee.setManager(parseEmployeeFromMap(employeeMap));
        }

        Claim managedEmployeesClaim = decodedJWT.getClaim(MANAGED_EMPLOYEES);
        if (managedEmployeesClaim != null) {
            employee.setManagedEmployees(new ArrayList<>());

            List<HashMap> managedEmployees = managedEmployeesClaim.asList(HashMap.class);
            if (managedEmployees != null) {
                for (var employeeMap : managedEmployees) {
                    Employee managedEmployee = parseEmployeeFromMap(employeeMap);
                    if (managedEmployee != null) {
                        employee.getManagedEmployees().add(employee);
                    }
                }
            }
        }

        return new EmployeeUserDetails(decodedJWT.getSubject(), employeeRepository.findById(decodedJWT.getClaim("id").asLong()).get());
    }

    private Map<String, Object> createMapFromEmployee(Employee employee) {
        return Map.of(
                ID, employee.getId(),
                USERNAME, employee.getUsername()
        );
    }
    
    private Employee parseEmployeeFromMap(Map<String, Object> employeeMap) {
        if (employeeMap != null) {
            Employee employee = new Employee();
            employee.setId(((Integer) employeeMap.get(ID)).longValue());
            employee.setUsername((String) employeeMap.get(USERNAME));

            return employee;
        }

        return null;
    }

    private Algorithm getAlgorithm() {
        return switch (properties.getJwtConfig().getAlgorithm().getMethod()) {
            case "HS384" -> Algorithm.HMAC384(properties.getJwtConfig().getAlgorithm().getSecret());
            case "HS512" -> Algorithm.HMAC512(properties.getJwtConfig().getAlgorithm().getSecret());
            default -> Algorithm.HMAC256(properties.getJwtConfig().getAlgorithm().getSecret());
        };
    }
}

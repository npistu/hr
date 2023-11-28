package hu.webuni.hr.npistu.utils;

import hu.webuni.hr.npistu.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeUtils {
    public static Map<String, Object> employeeToMap(Employee employee) {
        Map<String, Object> result = new HashMap<>();

        if (employee != null) {
            result.put("id", employee.getId());
            result.put("username", employee.getUsername());
        }

        return result;
    }

    public static List<Map<String, Object>> employeeListToMapList(List<Employee> employees) {
        List<Map<String, Object>> result = new ArrayList<>();

        employees.forEach(employee -> {
            result.add(employeeToMap(employee));
        });

        return result;
    }
}

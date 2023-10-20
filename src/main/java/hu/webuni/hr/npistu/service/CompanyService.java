package hu.webuni.hr.npistu.service;

import hu.webuni.hr.npistu.dto.CompanyDto;
import hu.webuni.hr.npistu.exception.NonUniqueIdException;
import hu.webuni.hr.npistu.model.Company;
import hu.webuni.hr.npistu.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyService {

    private Map<Long, Company> companies = new HashMap<>();

    public Company create(Company company) {
        throwIfNonUniqueId(company);
        return save(company);
    }

    public Company update(Company company) {
        if (findById(company.getId()) == null) {
            return null;
        }

        return save(company);
    }

    public List<Company> findAll() {
        return new ArrayList<>(companies.values());
    }

    public Company findById(long id) {
        return companies.get(id);
    }

    public void delete(long id) {
        companies.remove(id);
    }

    private Company save(Company company) {
        companies.put(company.getId(), company);

        return company;
    }

    private void throwIfNonUniqueId(Company company) {
        if (companies.values().stream().anyMatch(a -> a.getId().equals(company.getId()))) {
            throw new NonUniqueIdException();
        }
    }
}

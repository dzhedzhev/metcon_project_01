package com.containerdepot.metcon.service;

import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.service.dtos.CompanyAddDto;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    List<String> allCompaniesNames();

    boolean add(CompanyAddDto data);
    List<Company> allCompanies();

    Optional<Company> findCompanyById(long id);
}

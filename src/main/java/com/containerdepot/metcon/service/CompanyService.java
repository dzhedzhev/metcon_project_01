package com.containerdepot.metcon.service;

import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.service.dtos.exports.CompanyDto;
import com.containerdepot.metcon.service.dtos.imports.CompanyAddDto;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    List<String> allCompaniesNames();

    boolean add(CompanyAddDto data);
    List<CompanyDto> allCompanies();

    Optional<Company> findCompanyById(long id);

    boolean edit(CompanyAddDto data);

    void delete(Long id);
}

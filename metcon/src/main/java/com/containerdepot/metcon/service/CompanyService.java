package com.containerdepot.metcon.service;

import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.service.dtos.exports.CompanyDto;
import com.containerdepot.metcon.service.dtos.imports.CompanyAddDto;
import com.containerdepot.metcon.service.dtos.imports.CompanyEditDto;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    List<String> allCompaniesNames();

    void add(CompanyAddDto data);
    List<CompanyDto> allCompanies();

    Optional<Company> findCompanyById(long id);

    void edit(CompanyEditDto data);

    void delete(Long id);
}

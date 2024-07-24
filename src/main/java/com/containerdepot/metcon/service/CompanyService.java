package com.containerdepot.metcon.service;

import com.containerdepot.metcon.service.dtos.CompanyAddDto;

import java.util.List;

public interface CompanyService {
    List<String> allCompaniesNames();

    boolean add(CompanyAddDto data);
}

package com.containerdepot.metcon.service.impl;

import com.containerdepot.metcon.data.CompanyRepository;
import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.service.CompanyService;
import com.containerdepot.metcon.service.dtos.CompanyAddDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<String> allCompaniesNames() {
        return this.companyRepository.findAll().stream()
                .map(Company::getNameEn).toList();
    }

    @Override
    public boolean add(CompanyAddDto data) {
        boolean existingCompany = this.companyRepository.existsByNameEnOrVatNumberOrEmailOrPhoneNumber(data.getNameEn(),
                data.getVatNumber(), data.getEmail(), data.getPhoneNumber());
        if (existingCompany) {
            return false;
        }
        Company mappedCompany = this.modelMapper.map(data, Company.class);
        this.companyRepository.save(mappedCompany);
        return true;
    }
}

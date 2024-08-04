package com.containerdepot.metcon.service.impl;

import com.containerdepot.metcon.data.CompanyRepository;
import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.service.CompanyService;
import com.containerdepot.metcon.service.dtos.exports.CompanyDto;
import com.containerdepot.metcon.service.dtos.imports.CompanyAddDto;
import com.containerdepot.metcon.service.dtos.imports.CompanyEditDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            throw new IllegalArgumentException("There is company with matching data registered! Please try again!");
        }
        Company mappedCompany = this.modelMapper.map(data, Company.class);
        this.companyRepository.save(mappedCompany);
        return true;
    }

    @Override
    public List<CompanyDto> allCompanies() {
        return this.companyRepository.findAll().stream().map(company -> {
            CompanyDto mappedCompanyDto = this.modelMapper.map(company, CompanyDto.class);
            mappedCompanyDto.setId(company.getId());
            return mappedCompanyDto;
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<Company> findCompanyById(long id) {
       return this.companyRepository.findById(id);
    }

    @Override
    public boolean edit(CompanyEditDto data) {
        Optional<Company> companyOptional = this.companyRepository.findById(data.getId());
        if (companyOptional.isEmpty()) {
            throw new IllegalArgumentException("There is no company with specified id!");
        }
        Company company = companyOptional.get();
        company.setNameEn(data.getNameEn());
        company.setNameBg(data.getNameBg());
        company.setAddress(data.getAddress());
        company.setCity(data.getCity());
        company.setVatNumber(data.getVatNumber());
        company.setEmail(data.getEmail());
        company.setPhoneNumber(data.getPhoneNumber());
        this.companyRepository.save(company);
        return true;
    }

    @Override
    public void delete(Long id) {
        if (!this.companyRepository.existsById(id)) {
            throw new IllegalArgumentException("There is no company with specified id!");
        }
        this.companyRepository.deleteById(id);
    }
}

package com.containerdepot.metcon.service.impl;

import com.containerdepot.metcon.data.CompanyRepository;
import com.containerdepot.metcon.data.ContainerRepository;
import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.model.entities.Container;
import com.containerdepot.metcon.service.ContainerService;
import com.containerdepot.metcon.service.dtos.ContainerAddDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContainerServiceImpl implements ContainerService {

    private final ModelMapper modelMapper;
    private final ContainerRepository containerRepository;
    private final CompanyRepository companyRepository;


    public ContainerServiceImpl(ModelMapper modelMapper, ContainerRepository containerRepository, CompanyRepository companyRepository) {
        this.modelMapper = modelMapper;
        this.containerRepository = containerRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public boolean add(ContainerAddDto data) {
        Optional<Container> byNumber = this.containerRepository.findByNumber(data.getNumber());
        if (byNumber.isPresent()) {
//            TODO throw error?
            return false;
        }
        String company = data.getOwner();
        Optional<Company> byNameEn = this.companyRepository.findByNameEn(company);
        if (byNameEn.isEmpty()){
            return false;
        }
        Container container = this.modelMapper.map(data, Container.class);
        container.setOwner(byNameEn.get());
        this.containerRepository.save(container);
        return true;
    }

    public List<Container> getAllOrderedByReceivedDesc() {
        return this.containerRepository.findByOrderByReceivedDesc();/*TODO pagination*/
    }

    @Override
    public List<Container> findAllByCompanyId(long id) {
        return this.containerRepository.findAllByOwnerId(id);
    }
}

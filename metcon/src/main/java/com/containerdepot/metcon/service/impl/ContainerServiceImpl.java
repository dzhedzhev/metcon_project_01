package com.containerdepot.metcon.service.impl;

import com.containerdepot.metcon.data.CompanyRepository;
import com.containerdepot.metcon.data.ContainerRepository;
import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.model.entities.Container;
import com.containerdepot.metcon.service.ContainerService;
import com.containerdepot.metcon.service.dtos.imports.ContainerAddDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Optional<Container> byNumber = this.containerRepository.findByNumberAndReleasedNotNull(data.getNumber());
        if (byNumber.isPresent() && byNumber.get().getReleased() == null) {
            throw new IllegalArgumentException("There is no company with specified name!");
        }
        String company = data.getOwner();
        Optional<Company> byNameEn = this.companyRepository.findByNameEn(company);
        if (byNameEn.isEmpty()) {
            throw new IllegalArgumentException("There is no company with specified name!");
        }
        Container container = this.modelMapper.map(data, Container.class);
        container.setOwner(byNameEn.get());
        container.setDamaged(data.getDamaged());/*TODO*/
        this.containerRepository.save(container);
        return true;
    }

    public List<ContainerAddDto> getAllOrderedByReceivedDesc() {
        List<ContainerAddDto> collect = this.containerRepository.findByOrderByReceivedDesc()
                .stream()
                .map(c -> {
                    ContainerAddDto containerAddDto = this.modelMapper.map(c, ContainerAddDto.class);
                    containerAddDto.setOwner(c.getOwner().getNameEn());
                    return containerAddDto;
                }).collect(Collectors.toList());
        return collect;/*TODO pagination*/
    }

    @Override
    public List<ContainerAddDto> findAllByCompanyId(long id) {
        return this.containerRepository.findAllByOwnerId(id)
                .stream().map(c -> {
                    ContainerAddDto containerAddDto = this.modelMapper.map(c, ContainerAddDto.class);
                    containerAddDto.setOwner(c.getOwner().getNameEn());
                    return containerAddDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Container> findContainerById(Long id) {
        return this.containerRepository.findById(id);
    }

    @Override
    public boolean edit(ContainerAddDto data) {
        Optional<Container> optionalContainer = this.containerRepository.findById(data.getId());
        Optional<Company> optionalCompany = this.companyRepository.findByNameEn(data.getOwner());
        if (optionalContainer.isEmpty() || optionalCompany.isEmpty()) {
            return false;
        }
        Container container = optionalContainer.get();
        container.setNumber(data.getNumber());
        container.setType(data.getType());
        container.setDamaged(data.getDamaged());
        container.setOwner(optionalCompany.get());
        container.setReceived(data.getReceived());
        container.setReceivedByTruck(data.getReceivedByTruck());
        container.setReleased(data.getReleased());
        container.setReleasedToTruck(data.getReleasedToTruck());
        this.containerRepository.save(container);
        return true;
    }

    @Override
    public void delete(Long id) {
        if (this.containerRepository.existsById(id)) {
            this.containerRepository.deleteById(id);
        } /*TODO exception handling*/
    }
}

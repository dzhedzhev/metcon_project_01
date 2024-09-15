package com.containerdepot.metcon.service.impl;

import com.containerdepot.metcon.data.CompanyRepository;
import com.containerdepot.metcon.data.ContainerRepository;
import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.model.entities.Container;
import com.containerdepot.metcon.service.ContainerService;
import com.containerdepot.metcon.service.dtos.imports.ContainerAddDto;
import com.containerdepot.metcon.service.dtos.imports.ContainerEditDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
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
    public void add(ContainerAddDto data) {
        Optional<Container> byNumber = this.containerRepository.findByNumberAndReleasedNotNull(data.getNumber());
        if (byNumber.isPresent() && byNumber.get().getReleased() == null) {
            throw new IllegalArgumentException("Container is present at the depot!");
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
    }

    public List<ContainerAddDto> getAllOrderedByReceivedDesc() {
        return this.containerRepository.findByOrderByReceivedDesc()
                .stream()
                .map(c -> {
                    ContainerAddDto containerAddDto = this.modelMapper.map(c, ContainerAddDto.class);
                    containerAddDto.setOwner(c.getOwner().getNameEn());
                    return containerAddDto;
                }).collect(Collectors.toList());/*TODO pagination*/
    }
    public PagedModel<ContainerAddDto> getAllContainers(Pageable pageable, int pageNumber) {
        pageable = PageRequest.of(pageNumber - 1, 10);
        return new PagedModel<>(this.containerRepository.findAll(pageable)
                .map(c -> {
                    ContainerAddDto containerAddDto = this.modelMapper.map(c, ContainerAddDto.class);
                    containerAddDto.setOwner(c.getOwner().getNameEn());
                    return containerAddDto;
                }));
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
    public void edit(ContainerEditDto data) {
        Optional<Container> optionalContainer = this.containerRepository.findById(data.getId());
        Optional<Company> optionalCompany = this.companyRepository.findByNameEn(data.getOwner());
        if (optionalContainer.isEmpty() || optionalCompany.isEmpty()) {
            throw new IllegalArgumentException("There is no such container at the depot!");
        }
        Container container = optionalContainer.get();
        this.modelMapper.map(data, container);
        container.setOwner(optionalCompany.get());

        this.containerRepository.save(container);
    }

    @Override
    public void delete(Long id) {
        if (!this.containerRepository.existsById(id)) {
            throw new IllegalArgumentException("There is no such container at the depot!");
        }
        this.containerRepository.deleteById(id);
    }
}

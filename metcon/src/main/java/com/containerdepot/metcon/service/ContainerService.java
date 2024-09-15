package com.containerdepot.metcon.service;

import com.containerdepot.metcon.model.entities.Container;
import com.containerdepot.metcon.service.dtos.imports.ContainerAddDto;
import com.containerdepot.metcon.service.dtos.imports.ContainerEditDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

import java.util.List;
import java.util.Optional;


public interface ContainerService {
    void add(ContainerAddDto data);

    public List<ContainerAddDto> getAllOrderedByReceivedDesc();

    PagedModel<ContainerAddDto> getAllContainers(Pageable pageable, int pageNumber);

    List<ContainerAddDto> findAllByCompanyId(long id);/*TODO return list of ContainerDto*/

    Optional<Container> findContainerById(Long id);

    void edit(ContainerEditDto data);

    void delete(Long id);
}

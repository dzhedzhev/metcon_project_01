package com.containerdepot.metcon.service;

import com.containerdepot.metcon.model.entities.Container;
import com.containerdepot.metcon.service.dtos.imports.ContainerAddDto;

import java.util.List;
import java.util.Optional;


public interface ContainerService {
    boolean add(ContainerAddDto data);

    public List<ContainerAddDto> getAllOrderedByReceivedDesc();

    List<ContainerAddDto> findAllByCompanyId(long id);/*TODO return list of ContainerDto*/

    Optional<Container> findContainerById(Long id);

    boolean edit(ContainerAddDto data);

    void delete(Long id);
}

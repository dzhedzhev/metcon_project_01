package com.containerdepot.metcon.service;

import com.containerdepot.metcon.model.entities.Container;
import com.containerdepot.metcon.service.dtos.imports.ContainerAddDto;

import java.util.List;


public interface ContainerService {
    boolean add(ContainerAddDto data);

    public List<Container> getAllOrderedByReceivedDesc();

    List<Container> findAllByCompanyId(long id);/*TODO return list of ContainerDto*/
}

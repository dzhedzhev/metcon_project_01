package com.containerdepot.metcon.service;

import com.containerdepot.metcon.model.entities.Container;
import com.containerdepot.metcon.service.dtos.ContainerAddDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ContainerService {
    boolean add(ContainerAddDto data);

    public List<Container> getAllOrderedByReceivedDesc();
}

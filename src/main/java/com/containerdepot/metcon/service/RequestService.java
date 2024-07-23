package com.containerdepot.metcon.service;

import com.containerdepot.metcon.model.entities.Request;
import com.containerdepot.metcon.service.dtos.RequestAddDto;

import java.util.List;

public interface RequestService {
    boolean add(RequestAddDto data);

    List<Request> findAllContainersByIdDesc();
}
